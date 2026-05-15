package labs.currencyservice.application.usecase.outbound_info

import labs.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal

data class ConvertedItemInfo(
    val from: CurrencyCode,
    val amount: BigDecimal,
    val rate: BigDecimal,
    val convertedAmount: BigDecimal
)