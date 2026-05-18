package tracker.currencyservice.application.usecase.outbound_info

import tracker.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal

data class ConvertedCurrencyInfo (
    val from: CurrencyCode,
    val to: CurrencyCode,
    val originalAmount: BigDecimal,
    val targetedRateValue: BigDecimal,
    val finalAmount: BigDecimal,
)