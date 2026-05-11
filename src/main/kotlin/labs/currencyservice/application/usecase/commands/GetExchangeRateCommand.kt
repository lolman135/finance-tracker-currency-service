package labs.currencyservice.application.usecase.commands

import labs.currencyservice.domain.model.CurrencyCode

data class GetExchangeRateCommand(
    val from: CurrencyCode,
    val to: CurrencyCode
)