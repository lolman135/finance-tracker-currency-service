package labs.currencyservice.infrasctructure.cache

import labs.currencyservice.application.external.ExchangeRateCache
import labs.currencyservice.domain.model.CurrencyCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.Duration

@Component
class ExchangeRateRedisCacheImpl(
    private val redisTemplate: StringRedisTemplate,
    @param:Value("\${cache.lifetime-minutes}") private val ttl: Long
) : ExchangeRateCache {

    private val realTtl = Duration.ofMinutes(ttl)

    override fun getRate(from: CurrencyCode, to: CurrencyCode): BigDecimal? {
        val key = buildKey(from, to)
        return redisTemplate.opsForValue().get(key)?.let { BigDecimal(it) }
    }

    override fun saveRate(from: CurrencyCode, to: CurrencyCode, rate: BigDecimal) {
        val key = buildKey(from, to)
        redisTemplate.opsForValue().set(key, rate.toPlainString(), realTtl)
    }

    private fun buildKey(from: CurrencyCode, to: CurrencyCode) = "rates:$from:$to"
}