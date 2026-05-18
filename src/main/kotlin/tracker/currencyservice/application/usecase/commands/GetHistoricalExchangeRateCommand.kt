package tracker.currencyservice.application.usecase.commands

import tracker.currencyservice.domain.model.CurrencyCode
import java.time.Instant

data class GetHistoricalExchangeRateCommand(
    val from: CurrencyCode,
    val to: CurrencyCode,
    val instant: Instant
)