package labs.currencyservice.application.usecase

import labs.currencyservice.application.exceptions.ExchangeRateAtMomentNotFoundException
import labs.currencyservice.application.usecase.commands.GetHistoricalExchangeRateCommand
import labs.currencyservice.domain.ExchangeRateRepository
import labs.currencyservice.domain.model.CurrencyCode
import labs.currencyservice.domain.model.ExchangeRate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.RoundingMode


//idk is it necessary, maybe i'll delete it later
//@Service
// TODO: uncomment later
class GetHistoricalExchangeRateUseCase(
    private val exchangeRateRepository: ExchangeRateRepository
) : UseCase<GetHistoricalExchangeRateCommand, ExchangeRate> {

//    @Transactional
// TODO: uncomment later
    override fun execute(command: GetHistoricalExchangeRateCommand): ExchangeRate {
        val usdToBaseRate = exchangeRateRepository
            .findLatestRateBefore(CurrencyCode.USD, command.from, command.instant)?.rate
            ?: throw ExchangeRateAtMomentNotFoundException(
                CurrencyCode.USD,
                command.from,
                command.instant
            )

        val usdToTargetRate = exchangeRateRepository
            .findLatestRateBefore(CurrencyCode.USD, command.to, command.instant)?.rate
            ?: throw ExchangeRateAtMomentNotFoundException(
                CurrencyCode.USD,
                command.to,
                command.instant
            )

        val targetRate = usdToTargetRate.divide(usdToBaseRate, 10, RoundingMode.HALF_UP)

        return ExchangeRate(
            from = command.from,
            to = command.to,
            rate = targetRate,
            fetchedAt = command.instant
        )
    }

}