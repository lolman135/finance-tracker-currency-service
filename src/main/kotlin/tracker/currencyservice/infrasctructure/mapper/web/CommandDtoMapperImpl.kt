package tracker.currencyservice.infrasctructure.mapper.web

import tracker.currencyservice.application.usecase.commands.BatchItem
import tracker.currencyservice.application.usecase.commands.ConvertBatchCommand
import tracker.currencyservice.application.usecase.commands.ConvertCurrencyCommand
import tracker.currencyservice.application.usecase.outbound_info.ConvertedBatchInfo
import tracker.currencyservice.application.usecase.outbound_info.ConvertedCurrencyInfo
import tracker.currencyservice.application.usecase.outbound_info.ConvertedItemInfo
import tracker.currencyservice.domain.model.CurrencyCode
import tracker.currencyservice.infrasctructure.dto.inbound.ConvertBatchDtoInbound
import tracker.currencyservice.infrasctructure.dto.inbound.ConvertBatchItemSubDtoInbound
import tracker.currencyservice.infrasctructure.dto.inbound.ConvertCurrencyInboundDto
import tracker.currencyservice.infrasctructure.dto.outbound.ConvertedBatchDtoOutbound
import tracker.currencyservice.infrasctructure.dto.outbound.ConvertedBatchItemSubDto
import tracker.currencyservice.infrasctructure.dto.outbound.ConvertedCurrencyDtoOutbound
import org.springframework.stereotype.Component
import tracker.currencyservice.infrasctructure.exception.CurrencyNotFoundException

@Component
class CommandDtoMapperImpl : CommandDtoMapper {

    override fun convertCurrencyDtoToCommand(dto: ConvertCurrencyInboundDto): ConvertCurrencyCommand {
        return ConvertCurrencyCommand(
            from = safelyParseCurrency(dto.from),
            to = safelyParseCurrency(dto.to),
            amount = dto.amount,
            at = dto.at
        )
    }

    override fun convertBatchDtoToCommand(dto: ConvertBatchDtoInbound): ConvertBatchCommand {
        return ConvertBatchCommand(
            targetCurrency = safelyParseCurrency(dto.targetCurrency),
            before = dto.before,
            items = dto.items.map { itemDtoToCommand(it) }
        )
    }

    override fun singleConvertedInfoToDto(info: ConvertedCurrencyInfo): ConvertedCurrencyDtoOutbound {
        return ConvertedCurrencyDtoOutbound(
            from = info.from.name,
            to = info.to.name,
            originalAmount = info.originalAmount,
            targetedRateValue = info.targetedRateValue,
            finalAmount = info.finalAmount
        )
    }

    override fun convertedBatchInfoToDto(info: ConvertedBatchInfo) = ConvertedBatchDtoOutbound(
        targetCurrency = info.targetCurrency.name,
        totalAmount = info.totalAmount,
        at = info.at,
        items = info.items.map { itemInfoToDto(it) }
    )

    private fun itemDtoToCommand(itemSubDto: ConvertBatchItemSubDtoInbound): BatchItem {
        return BatchItem(
            from = safelyParseCurrency(itemSubDto.from),
            amount = itemSubDto.amount
        )
    }

    private fun itemInfoToDto(itemInfo: ConvertedItemInfo): ConvertedBatchItemSubDto {
        return ConvertedBatchItemSubDto(
            from = itemInfo.from.name,
            amount = itemInfo.amount,
            rate = itemInfo.rate,
            convertedAmount = itemInfo.convertedAmount
        )
    }

    private fun safelyParseCurrency(code: String): CurrencyCode {
        return try {
            CurrencyCode.valueOf(code.uppercase().trim())
        } catch (_: IllegalArgumentException) {
            throw CurrencyNotFoundException(code)
        }
    }
}