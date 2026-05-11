package labs.currencyservice.application.usecase

import labs.currencyservice.application.exceptions.ExchangeRateNotFoundException
import labs.currencyservice.application.external.ExchangeRateCache
import labs.currencyservice.application.usecase.commands.GetExchangeRateCommand
import labs.currencyservice.domain.ExchangeRateRepository
import labs.currencyservice.domain.model.CurrencyCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

//@Service
// TODO: uncomment later
class GetExchangeRateUseCase(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val exchangeRateCache: ExchangeRateCache
) : UseCase<GetExchangeRateCommand, BigDecimal> {

//    @Transactional
// TODO: uncomment later
    override fun execute(command: GetExchangeRateCommand): BigDecimal {
        return exchangeRateCache.getRate(command.from, command.to)
            ?: getTargetExchangeRate(command.from, command.to)
    }

    private fun getTargetExchangeRate(from: CurrencyCode, to: CurrencyCode): BigDecimal {
        val usdToBaseRate = exchangeRateRepository
            .findLatestRate(CurrencyCode.USD, from)?.rate
            ?: throw ExchangeRateNotFoundException(CurrencyCode.USD, from)

        val  usdToTargetRate = exchangeRateRepository
            .findLatestRate(CurrencyCode.USD, to)?.rate
            ?: throw ExchangeRateNotFoundException(CurrencyCode.USD, to)

        return usdToTargetRate / usdToBaseRate
    }
}