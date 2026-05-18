package tracker.currencyservice.application.usecase.commands

import tracker.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal

data class BatchItem(
    val from: CurrencyCode,
    val amount: BigDecimal
)