package tracker.currencyservice.application.external

import tracker.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal

interface ExternalRateProvider {
    fun getAllRates(base: CurrencyCode): Map<CurrencyCode, BigDecimal>
}