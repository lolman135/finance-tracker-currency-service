package tracker.currencyservice.infrasctructure.dto.inbound

import jakarta.validation.constraints.Pattern
import java.time.Instant

data class ConvertBatchDtoInbound(
    @field:Pattern(regexp = "^[A-Z]{3}$")
    val targetCurrency: String,
    val items: List<ConvertBatchItemSubDtoInbound> = emptyList(),
    val before: Instant? = null
)