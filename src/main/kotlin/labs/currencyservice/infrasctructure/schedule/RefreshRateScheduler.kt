package labs.currencyservice.infrasctructure.schedule

import labs.currencyservice.application.usecase.RefreshRatesUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RefreshRateScheduler(private val refreshRatesUseCase: RefreshRatesUseCase) {

    @Scheduled(cron = "0 5 16 * * *", zone = "CET")
    fun refresh() {
        refreshRatesUseCase.execute(Unit)
    }
}