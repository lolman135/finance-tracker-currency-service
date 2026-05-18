package tracker.currencyservice.application.usecase.outbound_info

import tracker.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal

data class ConvertedItemInfo(
    val from: CurrencyCode,
    val amount: BigDecimal,
    val rate: BigDecimal,
    val convertedAmount: BigDecimal
)