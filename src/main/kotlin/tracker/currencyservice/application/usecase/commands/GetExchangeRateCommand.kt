package tracker.currencyservice.application.usecase.commands

import tracker.currencyservice.domain.model.CurrencyCode

data class GetExchangeRateCommand(
    val from: CurrencyCode,
    val to: CurrencyCode
)