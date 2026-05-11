package labs.currencyservice.application.external

import labs.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal

interface ExternalRateProvider {
    fun getAllRates(base: CurrencyCode): Map<CurrencyCode, BigDecimal>
}