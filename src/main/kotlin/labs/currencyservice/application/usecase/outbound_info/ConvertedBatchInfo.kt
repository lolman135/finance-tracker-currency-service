package labs.currencyservice.application.usecase.outbound_info

import labs.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal
import java.time.Instant

data class ConvertedBatchInfo(
    val targetCurrency: CurrencyCode,
    val totalAmount: BigDecimal,
    val at: Instant,
    val items: List<ConvertedItemInfo>
)