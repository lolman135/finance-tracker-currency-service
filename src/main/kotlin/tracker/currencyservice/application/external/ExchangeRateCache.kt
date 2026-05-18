package tracker.currencyservice.application.external

import tracker.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal

interface ExchangeRateCache {
    fun getRate(from: CurrencyCode, to: CurrencyCode): BigDecimal?
    fun saveRate(from: CurrencyCode, to: CurrencyCode, rate: BigDecimal)
    fun getRates(pairs: List<Pair<CurrencyCode, CurrencyCode>>): Map<Pair<CurrencyCode, CurrencyCode>, BigDecimal>
}