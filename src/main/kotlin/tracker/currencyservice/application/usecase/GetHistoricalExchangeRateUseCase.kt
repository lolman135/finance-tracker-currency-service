package tracker.currencyservice.application.usecase

import tracker.currencyservice.application.exception.ExchangeRateAtMomentNotFoundException
import tracker.currencyservice.application.usecase.commands.GetHistoricalExchangeRateCommand
import tracker.currencyservice.domain.ExchangeRateRepository
import tracker.currencyservice.domain.model.CurrencyCode
import tracker.currencyservice.domain.model.ExchangeRate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode


@Service
class GetHistoricalExchangeRateUseCase(
    private val exchangeRateRepository: ExchangeRateRepository
) : UseCase<GetHistoricalExchangeRateCommand, ExchangeRate> {

    @Transactional
    override fun execute(command: GetHistoricalExchangeRateCommand): ExchangeRate {

        if(command.from == command.to)
            return ExchangeRate(
                from = command.from,
                to = command.to,
                rate = BigDecimal.ONE,
                fetchedAt = command.instant
            )

        val usdToBaseRate = if (command.from == CurrencyCode.USD) {
            BigDecimal.ONE
        } else {
            exchangeRateRepository.findLatestRateBefore(CurrencyCode.USD, command.from, command.instant)?.rate
                ?: throw ExchangeRateAtMomentNotFoundException(
                    CurrencyCode.USD,
                    command.from,
                    command.instant
                )
        }

        val usdToTargetRate = if (command.to == CurrencyCode.USD) {
            BigDecimal.ONE
        } else {
            exchangeRateRepository.findLatestRateBefore(CurrencyCode.USD, command.to, command.instant)?.rate
                ?: throw ExchangeRateAtMomentNotFoundException(
                    CurrencyCode.USD,
                    command.to,
                    command.instant
                )
        }

        val targetRate = usdToTargetRate.divide(usdToBaseRate, 10, RoundingMode.HALF_UP)

        return ExchangeRate(
            from = command.from,
            to = command.to,
            rate = targetRate,
            fetchedAt = command.instant
        )
    }

}