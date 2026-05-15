package labs.currencyservice.application.usecase

import labs.currencyservice.application.exceptions.ExchangeRateNotFoundException
import labs.currencyservice.application.external.ExchangeRateCache
import labs.currencyservice.application.usecase.commands.ConvertBatchCommand
import labs.currencyservice.application.usecase.outbound_info.ConvertedBatchInfo
import labs.currencyservice.domain.ExchangeRateRepository
import labs.currencyservice.domain.model.CurrencyCode
import labs.currencyservice.domain.model.ExchangeRate
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

        /*
            TODO TODO TODO TODO TODO TODO TODO TODO TODO
             ===========finish calculations============
             __________________________________________
             here you need to finish calculations. First of all: create list of convertedItems
             it contains:
              • from - this is currency you can get from command or from finalRates map
              • amount - starter amount from command
              • rate - this is value from finalRates map
              • convertedAmount = amount * rate
             Than you need to count total amount: just iterate by ConvertedItemsInfo list
             and call .convertedAmount by .sum{}
             Finally: at. if fetchedBefore is null -- set it as Instance.now()
             else -- fetchedBefore.
             So, here you go
         */



        TODO("finish")
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
                ?:throw ExchangeRateNotFoundException(CurrencyCode.USD, targetCurrencyCode)
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