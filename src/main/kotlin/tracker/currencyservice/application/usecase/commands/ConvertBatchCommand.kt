package tracker.currencyservice.application.usecase.commands

import tracker.currencyservice.domain.model.CurrencyCode
import java.time.Instant

data class ConvertBatchCommand(
    val targetCurrency: CurrencyCode,
    val items: List<BatchItem>,
    val before: Instant? = null
)