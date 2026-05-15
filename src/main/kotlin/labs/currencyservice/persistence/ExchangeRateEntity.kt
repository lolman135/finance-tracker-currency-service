package labs.currencyservice.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "exchange_rates")
class ExchangeRateEntity(
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @get:SequenceGenerator(name = "exchange_id_seq", sequenceName = "role_id_seq", allocationSize = 50)
    var id: Long? = null,

    @get:Column(name = "from")
    var from: String,

    @get:Column(name = "to")
    var to: String,

    @get:Column(name = "rate")
    var rate: BigDecimal,

    @get:Column(name = "fetched_at")
    var fetchedAt: Instant
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExchangeRateEntity

        if (from != other.from) return false
        if (to != other.to) return false
        if (rate != other.rate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        result = 31 * result + rate.hashCode()
        return result
    }
}