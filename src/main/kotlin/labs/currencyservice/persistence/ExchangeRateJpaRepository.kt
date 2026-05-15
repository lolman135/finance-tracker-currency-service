package labs.currencyservice.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.Optional

@Repository
interface ExchangeRateJpaRepository : JpaRepository<ExchangeRateEntity, Long>{

    @Query("""
        SELECT e FROM ExchangeRateEntity e 
        WHERE e.from = :from AND e.to = :to
        ORDER BY e.fetchedAt DESC
        LIMIT 1
        """)
    fun findLatestRate(from: String, to: String): Optional<ExchangeRateEntity>

    @Query("""
        SELECT e FROM ExchangeRateEntity e 
        WHERE e.from = :from 
            AND e.to = :to
            AND e.fetchedAt <= :fetchedBefore
        ORDER BY e.fetchedAt DESC
        LIMIT 1
    """)
    fun findLatestRateBefore(from: String, to: String, fetchedBefore: Instant): Optional<ExchangeRateEntity>

    @Query("""
        SELECT e FROM ExchangeRateEntity e
        WHERE e.from = :from AND e.to in :toCurrencies
        AND e.fetchedAt = (
            SELECT MAX(e2.fetchedAt) 
            FROM ExchangeRateEntity e2
            WHERE e2.from = e.from AND e2.to = e.to
        )
    """)
    fun findLatestRates(from: String, toCurrencies: Collection<String>): List<ExchangeRateEntity>

    @Query("""
        SELECT e FROM ExchangeRateEntity e
        WHERE e.from = :from AND e.to in :toCurrencies
        AND e.fetchedAt = (
            SELECT MAX(e2.fetchedAt) 
            FROM ExchangeRateEntity e2
            WHERE e2.from = e.from 
                AND e2.to = e.to
                AND e.fetchedAt <= :fetchedBefore
        )
    """)
    fun findLatestRatesBefore(
        from: String,
        toCurrencies: Collection<String>,
        fetchedBefore: Instant
    ): List<ExchangeRateEntity>
}