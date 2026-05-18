package tracker.currencyservice.infrasctructure.mapper.web

import tracker.currencyservice.application.usecase.commands.ConvertBatchCommand
import tracker.currencyservice.application.usecase.commands.ConvertCurrencyCommand
import tracker.currencyservice.application.usecase.outbound_info.ConvertedBatchInfo
import tracker.currencyservice.application.usecase.outbound_info.ConvertedCurrencyInfo
import tracker.currencyservice.infrasctructure.dto.inbound.ConvertBatchDtoInbound
import tracker.currencyservice.infrasctructure.dto.inbound.ConvertCurrencyInboundDto
import tracker.currencyservice.infrasctructure.dto.outbound.ConvertedBatchDtoOutbound
import tracker.currencyservice.infrasctructure.dto.outbound.ConvertedCurrencyDtoOutbound

interface CommandDtoMapper {
    // to command
    fun convertCurrencyDtoToCommand(dto: ConvertCurrencyInboundDto): ConvertCurrencyCommand
    fun convertBatchDtoToCommand(dto: ConvertBatchDtoInbound): ConvertBatchCommand

    // to dto
    fun singleConvertedInfoToDto(info: ConvertedCurrencyInfo): ConvertedCurrencyDtoOutbound
    fun convertedBatchInfoToDto(info: ConvertedBatchInfo): ConvertedBatchDtoOutbound
}