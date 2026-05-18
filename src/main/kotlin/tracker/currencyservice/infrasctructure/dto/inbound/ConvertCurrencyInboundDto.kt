package tracker.currencyservice.infrasctructure.dto.inbound

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import java.math.BigDecimal
import java.time.Instant

data class ConvertCurrencyInboundDto(
    @field:Min(0)
    val amount: BigDecimal,
    @field:Pattern(regexp = "^[A-Z]{3}$")
    val from: String,
    @field:Pattern(regexp = "^[A-Z]{3}$")
    val to: String,
    val at: Instant? = null
)