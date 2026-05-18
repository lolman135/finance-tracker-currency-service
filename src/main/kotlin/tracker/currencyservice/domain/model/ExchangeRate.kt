package tracker.currencyservice.domain.model

import java.math.BigDecimal
import java.time.Instant

class ExchangeRate(
    val from: CurrencyCode,
    val to: CurrencyCode,
    val rate: BigDecimal,
    val fetchedAt: Instant
)