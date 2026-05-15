package labs.currencyservice.application.usecase

import labs.currencyservice.application.usecase.commands.ConvertCurrencyCommand
import labs.currencyservice.application.usecase.commands.GetExchangeRateCommand
import labs.currencyservice.application.usecase.commands.GetHistoricalExchangeRateCommand
import labs.currencyservice.application.usecase.outbound_info.ConvertedCurrencyInfo
import org.springframework.stereotype.Service

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

        val finalAmount = command.amount * targetRateValue

        return ConvertedCurrencyInfo(
            from = command.from,
            to = command.to,
            originalAmount = command.amount,
            targetedRateValue = targetRateValue,
            finalAmount = finalAmount
        )
    }
}