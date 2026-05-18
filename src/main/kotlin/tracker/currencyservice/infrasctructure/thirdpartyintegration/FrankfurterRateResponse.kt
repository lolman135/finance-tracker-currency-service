package tracker.currencyservice.infrasctructure.thirdpartyintegration

import java.math.BigDecimal

data class FrankfurterRateResponse(
    val date: String,
    val base: String,
    val quote: String,
    val rate: BigDecimal
)
