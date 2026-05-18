package tracker.currencyservice.web.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tracker.currencyservice.application.usecase.ConvertBatchUseCase
import tracker.currencyservice.application.usecase.ConvertCurrencyUseCase
import tracker.currencyservice.application.usecase.GetAllCurrencyUseCase
import tracker.currencyservice.application.usecase.RefreshRatesUseCase
import tracker.currencyservice.infrasctructure.dto.inbound.ConvertBatchDtoInbound
import tracker.currencyservice.infrasctructure.dto.inbound.ConvertCurrencyInboundDto
import tracker.currencyservice.infrasctructure.dto.outbound.AllCurrenciesDtoOutbound
import tracker.currencyservice.infrasctructure.dto.outbound.ConvertedBatchDtoOutbound
import tracker.currencyservice.infrasctructure.dto.outbound.ConvertedCurrencyDtoOutbound
import tracker.currencyservice.infrasctructure.mapper.web.CommandDtoMapper

@RestController
@RequestMapping("/api/v1/currencies")
@Validated
class CurrencyController(
    private val getAllCurrencyUseCase: GetAllCurrencyUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val convertBatchUseCase: ConvertBatchUseCase,
    private val refreshRatesUseCase: RefreshRatesUseCase,
    private val mapper: CommandDtoMapper
) {

    @GetMapping
    fun getAllCurrencies(): ResponseEntity<AllCurrenciesDtoOutbound> {
        return ResponseEntity.ok(AllCurrenciesDtoOutbound(getAllCurrencyUseCase.execute(Unit)))
    }


    @PostMapping("/convert")
    fun convert(@Valid @RequestBody dto: ConvertCurrencyInboundDto): ResponseEntity<ConvertedCurrencyDtoOutbound> {
        val convertCommand = mapper.convertCurrencyDtoToCommand(dto)
        val response = mapper.singleConvertedInfoToDto(convertCurrencyUseCase.execute(convertCommand))
        return ResponseEntity.ok(response)
    }

    @PostMapping("/convert-batch")
    fun convertBatch(@Valid @RequestBody dto: ConvertBatchDtoInbound): ResponseEntity<ConvertedBatchDtoOutbound> {
        val convertBatchCommand = mapper.convertBatchDtoToCommand(dto)
        val response = mapper.convertedBatchInfoToDto(convertBatchUseCase.execute(convertBatchCommand))
        return ResponseEntity.ok(response)
    }

    @PostMapping("/refresh")
    fun refreshCurrencyRates(): ResponseEntity<Void> {
        refreshRatesUseCase.execute(Unit)
        return ResponseEntity.noContent().build()
    }

}