package tracker.currencyservice.application.usecase.commands

import tracker.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal
import java.time.Instant

data class ConvertCurrencyCommand(
    val amount: BigDecimal,
    val from: CurrencyCode,
    val to: CurrencyCode,
    val at: Instant? = null
)
