package tracker.currencyservice.infrasctructure.dto.outbound

import java.math.BigDecimal
import java.time.Instant

data class ConvertedBatchDtoOutbound(
    val targetCurrency: String,
    val totalAmount: BigDecimal,
    val at: Instant,
    val items: List<ConvertedBatchItemSubDto>
)