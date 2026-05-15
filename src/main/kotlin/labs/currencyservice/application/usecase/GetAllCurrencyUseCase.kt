package labs.currencyservice.application.usecase

import labs.currencyservice.domain.model.CurrencyCode

class GetAllCurrencyUseCase : UseCase<Unit, List<String>>{
    override fun execute(command: Unit) = CurrencyCode.entries.map { it.name }
}