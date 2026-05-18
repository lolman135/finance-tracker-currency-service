package tracker.currencyservice.application.usecase

import org.springframework.stereotype.Service
import tracker.currencyservice.domain.model.CurrencyCode

@Service
class GetAllCurrencyUseCase : UseCase<Unit, List<String>>{
    override fun execute(command: Unit) = CurrencyCode.entries.map { it.name }
}