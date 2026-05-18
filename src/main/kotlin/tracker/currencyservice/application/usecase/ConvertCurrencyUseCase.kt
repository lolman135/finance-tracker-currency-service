package tracker.currencyservice.application.usecase

import tracker.currencyservice.application.usecase.commands.ConvertCurrencyCommand
import tracker.currencyservice.application.usecase.commands.GetExchangeRateCommand
import tracker.currencyservice.application.usecase.commands.GetHistoricalExchangeRateCommand
import tracker.currencyservice.application.usecase.outbound_info.ConvertedCurrencyInfo
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class ConvertCurrencyUseCase(
    private val getExchangeRateValueUseCase: GetExchangeRateValueUseCase,
    private val getHistoricalExchangeRateUseCase: GetHistoricalExchangeRateUseCase
) : UseCase<ConvertCurrencyCommand, ConvertedCurrencyInfo>{

    override fun execute(command: ConvertCurrencyCommand): ConvertedCurrencyInfo {

        val targetRateValue = if (command.at == null){
            getExchangeRateValueUseCase.execute(GetExchangeRateCommand(command.from, command.to))
        } else {
            getHistoricalExchangeRateUseCase.execute(
                GetHistoricalExchangeRateCommand(
                    command.from,
                    command.to,
                    command.at
                )
            ).rate
        }

        val finalAmount = command.amount.multiply(targetRateValue).setScale(2, RoundingMode.HALF_UP)

        return ConvertedCurrencyInfo(
            from = command.from,
            to = command.to,
            originalAmount = command.amount,
            targetedRateValue = targetRateValue,
            finalAmount = finalAmount
        )
    }
}