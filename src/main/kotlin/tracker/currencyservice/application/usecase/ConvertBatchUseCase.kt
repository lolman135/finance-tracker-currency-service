package tracker.currencyservice.application.usecase

import tracker.currencyservice.application.exception.ExchangeRateNotFoundException
import tracker.currencyservice.application.external.ExchangeRateCache
import tracker.currencyservice.application.usecase.commands.ConvertBatchCommand
import tracker.currencyservice.application.usecase.outbound_info.ConvertedBatchInfo
import tracker.currencyservice.application.usecase.outbound_info.ConvertedItemInfo
import tracker.currencyservice.domain.ExchangeRateRepository
import tracker.currencyservice.domain.model.CurrencyCode
import tracker.currencyservice.domain.model.ExchangeRate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant

@Service
class ConvertBatchUseCase(
    private val repository: ExchangeRateRepository,
    private val cache: ExchangeRateCache
) : UseCase<ConvertBatchCommand, ConvertedBatchInfo> {

    override fun execute(command: ConvertBatchCommand): ConvertedBatchInfo {
        val finalRates = if (command.before == null) {
            val target = command.targetCurrency
            val items = command.items

            val pairs = items.map { it.from to target }
            val ratesFromCache = cache.getRates(pairs)

            val missingRateCodes = items.map { it.from }
                .filter { !ratesFromCache.contains(it to target) }
                .distinct()

            val missingRates = getRateBatchFromDB(missingRateCodes, command.targetCurrency)

            ratesFromCache + missingRates
        } else {
            val fromCodesList = command.items.map { it.from }
            getRateBatchFromDB(fromCodesList, command.targetCurrency, command.before)
        }

        val items: List<ConvertedItemInfo> = command.items.map { item ->
            val tempRate = finalRates[item.from to command.targetCurrency]!!
            ConvertedItemInfo(
                from = item.from,
                amount = item.amount,
                rate = tempRate,
                convertedAmount = item.amount.multiply(tempRate).setScale(2, RoundingMode.HALF_UP)
            )
        }

        val totalAmount = items.sumOf { it.convertedAmount }
        val at = command.before ?: Instant.now()

        return ConvertedBatchInfo(
            targetCurrency = command.targetCurrency,
            totalAmount = totalAmount,
            at = at,
            items = items
        )
    }

    private fun getRateBatchFromDB(
        fromCodesList: List<CurrencyCode>,
        targetCurrencyCode: CurrencyCode,
        fetchedBefore: Instant? = null
    ): Map<Pair<CurrencyCode, CurrencyCode>, BigDecimal> {

        if (fromCodesList.isEmpty()) return emptyMap()

        val usdToTargetRate = if (targetCurrencyCode == CurrencyCode.USD) {
            ExchangeRate(
                from = CurrencyCode.USD,
                to = targetCurrencyCode,
                rate = BigDecimal.ONE,
                fetchedAt = Instant.now()
            )
        } else {
            if (fetchedBefore == null) {
                repository.findLatestRate(CurrencyCode.USD, targetCurrencyCode)
            } else {
                repository.findLatestRateBefore(CurrencyCode.USD, targetCurrencyCode, fetchedBefore)
            }
                ?: throw ExchangeRateNotFoundException(CurrencyCode.USD, targetCurrencyCode)
        }

        val filteredFromCodes = fromCodesList.filter { it != CurrencyCode.USD }

        val usdToMissingRates = if (fetchedBefore == null){
            repository.findLatestRates(CurrencyCode.USD, filteredFromCodes)
        } else {
            repository.findLatestRatesBefore(CurrencyCode.USD, filteredFromCodes, fetchedBefore)
        }

        val result = usdToMissingRates
            .associate {
                Pair(it.to, usdToTargetRate.to) to usdToTargetRate.rate.divide(
                    it.rate,
                    10,
                    RoundingMode.HALF_UP
                )
            }.toMutableMap()

        if (fromCodesList.contains(CurrencyCode.USD)) {
            result[CurrencyCode.USD to targetCurrencyCode] = usdToTargetRate.rate
        }

        if (result.size < fromCodesList.distinct().size) {
            val foundCodes = result.keys.map { it.first }
            val missing = fromCodesList.distinct().filter { it !in foundCodes }
            throw ExchangeRateNotFoundException(CurrencyCode.USD, missing.first())
        }

        return result
    }
}