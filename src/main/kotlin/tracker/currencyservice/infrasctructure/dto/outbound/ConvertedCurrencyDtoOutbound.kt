package tracker.currencyservice.infrasctructure.dto.outbound

import java.math.BigDecimal

data class ConvertedCurrencyDtoOutbound(
    val from: String,
    val to: String,
    val originalAmount: BigDecimal,
    val targetedRateValue: BigDecimal,
    val finalAmount: BigDecimal,
)