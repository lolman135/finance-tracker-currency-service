package labs.currencyservice.domain.model

import labs.currencyservice.domain.model.CurrencyCode
import java.math.BigDecimal
import java.time.Instant

class ExchangeRate(
    val from: CurrencyCode,
    val to: CurrencyCode,
    val rate: BigDecimal,
    val fetchedAt: Instant
)