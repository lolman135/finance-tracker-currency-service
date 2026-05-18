package tracker.currencyservice.application.usecase

import tracker.currencyservice.application.exception.ExchangeRateNotFoundException
import tracker.currencyservice.application.external.ExchangeRateCache
import tracker.currencyservice.application.usecase.commands.GetExchangeRateCommand
import tracker.currencyservice.domain.ExchangeRateRepository
import tracker.currencyservice.domain.model.CurrencyCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class GetExchangeRateValueUseCase(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val exchangeRateCache: ExchangeRateCache
) : UseCase<GetExchangeRateCommand, BigDecimal> {

    @Transactional
    override fun execute(command: GetExchangeRateCommand): BigDecimal {
        return exchangeRateCache.getRate(command.from, command.to)
            ?: getTargetExchangeRate(command.from, command.to)
    }

    private fun getTargetExchangeRate(from: CurrencyCode, to: CurrencyCode): BigDecimal {
        if (from == to)
            return BigDecimal.ONE

        val usdToBaseRate = if (from == CurrencyCode.USD) {
            BigDecimal.ONE
        } else {
            exchangeRateRepository.findLatestRate(CurrencyCode.USD, from)?.rate
                ?: throw ExchangeRateNotFoundException(CurrencyCode.USD, from)
        }

        val usdToTargetRate = if (to == CurrencyCode.USD) {
            BigDecimal.ONE
        } else {
            exchangeRateRepository.findLatestRate(CurrencyCode.USD, to)?.rate
                ?: throw ExchangeRateNotFoundException(CurrencyCode.USD, to)
        }

        val targetRateValue = usdToTargetRate.divide(usdToBaseRate, 10, RoundingMode.HALF_UP)
        exchangeRateCache.saveRate(from, to, targetRateValue)
        return targetRateValue
    }
}