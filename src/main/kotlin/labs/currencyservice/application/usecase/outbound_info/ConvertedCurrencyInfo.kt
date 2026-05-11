package labs.currencyservice.application.usecase.outbound_info

import labs.currencyservice.domain.model.CurrencyCode
import labs.currencyservice.domain.model.ExchangeRate
import java.math.BigDecimal

data class ConvertedCurrencyInfo (
    val from: CurrencyCode,
    val to: CurrencyCode,
    val originalAmount: BigDecimal,
    val targetedRateValue: BigDecimal,
    val finalAmount: BigDecimal,
)