package labs.currencyservice.application.usecase.commands

import labs.currencyservice.domain.model.CurrencyCode
import java.time.Instant

data class ConvertBatchCommand(
    val targetCurrency: CurrencyCode,
    val items: List<BatchItem>,
    val before: Instant? = null
)