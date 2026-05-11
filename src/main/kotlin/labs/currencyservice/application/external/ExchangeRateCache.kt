package labs.currencyservice.application.external

import labs.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal

interface ExchangeRateCache {
    fun getRate(from: CurrencyCode, to: CurrencyCode): BigDecimal?
    fun saveRate(from: CurrencyCode, to: CurrencyCode, rate: BigDecimal)
}