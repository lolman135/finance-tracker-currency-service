package tracker.currencyservice.infrasctructure.dto.inbound

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import java.math.BigDecimal

data class ConvertBatchItemSubDtoInbound(
    @field:Pattern(regexp = "^[A-Z]{3}$")
    val from: String,
    @field:Min(0)
    val amount: BigDecimal
)