package tracker.currencyservice.infrasctructure.dto.outbound

import java.math.BigDecimal

data class ConvertedBatchItemSubDto(
    val from: String,
    val amount: BigDecimal,
    val rate: BigDecimal,
    val convertedAmount: BigDecimal
)