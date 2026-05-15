package labs.currencyservice.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.Optional

@Repository
interface ExchangeRateJpaRepository : JpaRepository<ExchangeRateEntity, Long>{

    @Query(
        "SELECT e FROM ExchangeRateEntity e " +
                "WHERE e.from = :from AND e.to = :to " +
                "ORDER BY e.fetchedAt DESC " +
                "LIMIT 1"
    )
    fun findLatestRate(from: String, to: String): Optional<ExchangeRateEntity>

    @Query(
        "SELECT e FROM ExchangeRateEntity e " +
                "WHERE e.from = :from AND e.to = :to " +
                "AND e.fetchedAt <= :fetchedAt " +
                "ORDER BY e.fetchedAt DESC " +
                "LIMIT 1"
    )
    fun findLatestRateBefore(from: String, to: String, fetchedAt: Instant): Optional<ExchangeRateEntity>
}