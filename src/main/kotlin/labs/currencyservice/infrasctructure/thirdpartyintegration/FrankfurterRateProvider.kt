package labs.currencyservice.infrasctructure.thirdpartyintegration

import labs.currencyservice.application.external.ExternalRateProvider
import labs.currencyservice.domain.model.CurrencyCode
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.math.BigDecimal

@Component
class FrankfurterRateProvider(
    private val webClient: WebClient
) : ExternalRateProvider {

    override fun getAllRates(base: CurrencyCode): Map<CurrencyCode, BigDecimal> {
        val rateList = webClient.get()
            .uri { it.queryParam("base", base.name).build() }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<FrankfurterRateResponse>>() {})
            .block() ?: emptyList()

        return rateList
            .filter { response -> CurrencyCode.entries.any { it.name == response.quote } }
            .associate { response -> CurrencyCode.valueOf(response.quote) to response.rate }
    }
}