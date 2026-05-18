package tracker.currencyservice.application.usecase.outbound_info

import tracker.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal
import java.time.Instant

data class ConvertedBatchInfo(
    val targetCurrency: CurrencyCode,
    val totalAmount: BigDecimal,
    val at: Instant,
    val items: List<ConvertedItemInfo>
)