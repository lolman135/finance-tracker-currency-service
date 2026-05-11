package labs.currencyservice.application.usecase.commands

import labs.currencyservice.domain.model.CurrencyCode
import java.time.Instant

data class GetHistoricalExchangeRateCommand(
    val from: CurrencyCode,
    val to: CurrencyCode,
    val instant: Instant
)