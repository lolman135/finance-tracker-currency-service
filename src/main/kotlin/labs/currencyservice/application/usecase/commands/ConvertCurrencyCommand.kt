package labs.currencyservice.application.usecase.commands

import labs.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal

data class ConvertCurrencyCommand(
    val amount: BigDecimal,
    val from: CurrencyCode,
    val to: CurrencyCode
)
