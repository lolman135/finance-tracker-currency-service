package labs.currencyservice.application.usecase

import labs.currencyservice.application.exceptions.ExchangeRateNotFoundException
import labs.currencyservice.application.external.ExchangeRateCache
import labs.currencyservice.application.usecase.commands.GetExchangeRateCommand
import labs.currencyservice.domain.ExchangeRateRepository
import labs.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal
import java.math.RoundingMode

//@Service
// TODO: uncomment later
class GetExchangeRateValueUseCase(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val exchangeRateCache: ExchangeRateCache
) : UseCase<GetExchangeRateCommand, BigDecimal> {

//    @Transactional
// TODO: uncomment later
    override fun execute(command: GetExchangeRateCommand): BigDecimal {
        return  exchangeRateCache.getRate(command.from, command.to)
            ?: getTargetExchangeRate(command.from, command.to)
    }

    private fun getTargetExchangeRate(from: CurrencyCode, to: CurrencyCode): BigDecimal {
        val usdToBaseRate = exchangeRateRepository
            .findLatestRate(CurrencyCode.USD, from)?.rate
            ?: throw ExchangeRateNotFoundException(CurrencyCode.USD, from)

        val  usdToTargetRate = exchangeRateRepository
            .findLatestRate(CurrencyCode.USD, to)?.rate
            ?: throw ExchangeRateNotFoundException(CurrencyCode.USD, to)

        return usdToTargetRate.divide(usdToBaseRate, 10, RoundingMode.HALF_UP)
    }
}