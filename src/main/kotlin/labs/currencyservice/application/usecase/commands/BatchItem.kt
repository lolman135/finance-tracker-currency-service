package labs.currencyservice.application.usecase.commands

import labs.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal

data class BatchItem(
    val from: CurrencyCode,
    val amount: BigDecimal
)