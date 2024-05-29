package com.vtxlab.project.bc_stock_finnhub.redis;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * A helper class for interacting with Redis using a RedisTemplate. This class provides various methods for performing common operations on Redis, such as setting and getting values, expiring keys,
 * checking key existence, and more.
 *
 * @param <T> the type of values stored in Redis
 */
@Slf4j
public class RedisHelper<T> {

  @Autowired
  private RedisTemplate<String, T> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  public RedisHelper(RedisTemplate<String, T> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public RedisHelper(RedisConnectionFactory factory) {
    RedisTemplate<String, T> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);

    Jackson2JsonRedisSerializer<T> jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer<>((Class<T>) Object.class);
    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    template.setKeySerializer(stringRedisSerializer);
    template.setValueSerializer(jackson2JsonRedisSerializer);
    template.setHashKeySerializer(stringRedisSerializer);
    template.setHashValueSerializer(jackson2JsonRedisSerializer);
    template.afterPropertiesSet();

    this.redisTemplate = template;
  }

  public RedisHelper(RedisConnectionFactory factory,
      ObjectMapper redisObjectMapper) {
    this.redisTemplate = template(factory, redisObjectMapper);
  }

  public static <T> RedisTemplate<String, T> template(
      RedisConnectionFactory factory, ObjectMapper redisObjectMapper) {
    RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setValueSerializer(RedisSerializer.json());
    redisTemplate.afterPropertiesSet();

    Jackson2JsonRedisSerializer<T> serializer =
        new Jackson2JsonRedisSerializer<>(redisObjectMapper,
            (Class<T>) Object.class);
    redisTemplate.setValueSerializer(serializer);

    return redisTemplate;
  }

  /**
   * Sets a timeout on the specified key.
   * 
   * @param key The key to set the timeout for.
   * @param time The time to expire, in seconds.
   * @return True if the timeout was set successfully, false otherwise.
   */
  public boolean expire(String key, long time) {
    try {
      if (time > 0) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
      }
      return true;
    } catch (Exception e) {
      log.error("Error setting expire for key: {}", key, e);
      return false;
    }
  }


  /**
   * Gets the expiration time for the specified key.
   * 
   * @param key The key to check for expiration.
   * @return The expiration time of the key in seconds.
   */
  public long getExpire(String key) {
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
  }

  /**
   * Checks if the specified key exists.
   * 
   * @param key The key to check for existence.
   * @return True if the key exists, false otherwise.
   */
  public boolean hasKey(String key) {
    try {
      return redisTemplate.hasKey(key);
    } catch (Exception e) {
      log.error("Error checking key existence for key: {}", key, e);
      return false;
    }
  }

  /**
   * Deletes one or more keys.
   * 
   * @param key The key(s) to delete.
   */
  public void del(String... key) {
    if (key != null && key.length > 0) {
      if (key.length == 1) {
        redisTemplate.delete(key[0]);
      } else {
        redisTemplate.delete(Arrays.asList(key));
      }
    }
  }

  /**
   * Gets the value associated with the specified key.
   * 
   * @param key The key to get the value for.
   * @return The value associated with the key.
   */
  public T get(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(key);
  }

  /**
   * Gets the value associated with the specified key and deserializes it into the given class.
   * 
   * @param key The key to get the value for.
   * @param clazz The class to deserialize the value into.
   * @return The deserialized value associated with the key.
   * @throws JsonProcessingException If an error occurs during deserialization.
   */
  public T get(String key, Class<T> clazz) throws JsonProcessingException {
    String value = (String) this.redisTemplate.opsForValue().get(key);
    return objectMapper.readValue(value, clazz);
  }

  /**
   * Sets the value associated with the specified key.
   * 
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return True if the value was set successfully, false otherwise.
   */
  public boolean set(String key, T value) {
    try {
      String json = objectMapper.writeValueAsString(value);
      redisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception e) {
      log.error("Error setting value for key: {}", key, e);
      return false;
    }
  }


  /**
   * Sets the value associated with the specified key.
   * 
   * @param key The key to set the value for.
   * @param value The value to set.
   * @param time The time to expire, in seconds.
   * @return True if the value was set successfully, false otherwise.
   */
  public boolean set(String key, T value, long time) {
    try {
      if (time > 0) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
      } else {
        set(key, value);
      }
      return true;
    } catch (Exception e) {
      log.error("Error setting value with expiration for key: {}", key, e);
      return false;
    }
  }

  /**
   * Increments the value associated with the specified key by the given delta.
   * 
   * @param key The key to increment the value for.
   * @param delta The amount to increment the value by.
   * @return The new value after incrementing.
   */
  public long incr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("Increment factor must be greater than 0");
    }
    return redisTemplate.opsForValue().increment(key, delta);
  }

  /**
   * Decrements the value associated with the specified key by the given delta.
   * 
   * @param key The key to decrement the value for.
   * @param delta The amount to decrement the value by.
   * @return The new value after decrementing.
   */
  public long decr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("Decrement factor must be greater than 0");
    }
    return redisTemplate.opsForValue().increment(key, -delta);
  }

  /**
   * Sets the value associated with the specified key in a hash structure.
   * 
   * @param key The key to set the value for.
   * @param hashKey The hash key to set the value for.
   * @param value The value to set.
   * @return True if the value was set successfully, false otherwise.
   */
  public <HK> void hSet(String key, HK hashKey, T value) {
    try {
      redisTemplate.opsForHash().put(key, hashKey, value);
    } catch (Exception e) {
      log.error("Error setting hash value for key: {}", key, e);
    }
  }

  /**
   * Sets the value associated with the specified key in a hash structure.
   * 
   * @param key The key to set the value for.
   * @param clazz The value to set.
   * @return True if the value was set successfully, false otherwise.
   */
  public <T> Map<String, T> hgetall(String key, Class<T> clazz) {
    try {
      Map<Object, Object> hashEntries = redisTemplate.opsForHash().entries(key);
      Map<String, T> resultMap = new LinkedHashMap<>();
      for (Map.Entry<Object, Object> entry : hashEntries.entrySet()) {
        String hashKey = (String) entry.getKey();
        String jsonValue = objectMapper.writeValueAsString(entry.getValue());
        T value = objectMapper.readValue(jsonValue, clazz);
        resultMap.put(hashKey, value);
      }
      return resultMap;
    } catch (Exception e) {
      log.error("Error getting all values from Redis for key: {}", key, e);
      return Collections.emptyMap();
    }
  }

  /**
   * Sets the value associated with the specified key in a hash structure.
   * 
   * @param key The key to set the value for.
   * @param clazz The class to deserialize the value into.
   * @return True if the value was set successfully, false otherwise.
   */
  public <T> Set<T> sGet(String key, Class<T> clazz) {
    try {
      Set<T> members = (Set<T>) redisTemplate.opsForSet().members(key);
      Set<T> resultSet = new HashSet<>();
      for (T member : members) {
        String jsonValue = objectMapper.writeValueAsString(member);
        T value = objectMapper.readValue(jsonValue, clazz);
        resultSet.add(value);
      }
      return resultSet;
    } catch (Exception e) {
      log.error("Error getting set values from Redis for key: {}", key, e);
      return Collections.emptySet();
    }
  }

  /**
   * Sets the value associated with the specified key in a hash structure.
   * 
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return True if the value was set successfully, false otherwise.
   */
  public boolean sSet(String key, T... values) {
    try {
      return redisTemplate.opsForSet().add(key, values) > 0;
    } catch (Exception e) {
      log.error("Error setting set values in Redis for key: {}", key, e);
      return false;
    }
  }

  /**
   * Sets the value associated with the specified key in a hash structure.
   * 
   * @param key The key to set the value for.
   * @param hashKey The hash key to set the value for.
   * @param value The value to set.
   * @return True if the value was set successfully, false otherwise.
   */
  public <T> List<T> lGet(String key, long start, long end, Class<T> clazz) {
    try {
      List<T> range =
          (List<T>) redisTemplate.opsForList().range(key, start, end);
      return range.stream().<T>map(obj -> deserializeObject(obj, clazz))
          .collect(Collectors.toList());
    } catch (Exception e) {
      log.error("Error getting list values from Redis for key: {}", key, e);
      return Collections.emptyList();
    }
  }

  /**
   * Deserializes the given object into the specified class.
   * 
   * @param obj The object to deserialize.
   * @param clazz The class to deserialize the object into.
   * @return The deserialized object.
   */
  private <S> S deserializeObject(Object obj, Class<S> clazz) {
    try {
      String jsonValue = objectMapper.writeValueAsString(obj);
      return objectMapper.readValue(jsonValue, clazz);
    } catch (Exception e) {
      log.error("Error deserializing object from Redis: {}", obj, e);
      return null;
    }
  }

  /**
   * Sets the value associated with the specified key in a list structure.
   * 
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return True if the value was set successfully, false otherwise.
   */
  public boolean lSet(String key, T value) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      return true;
    } catch (Exception e) {
      log.error("Error setting list value for key: {}", key, e);
      return false;
    }
  }

  /**
   * Sets the value associated with the specified key in a list structure.
   * 
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return True if the value was set successfully, false otherwise.
   */
  public Long lSet2(String key, T value) {
    return redisTemplate.opsForList().leftPush(key, value);
  }

  /**
   * Sets the value associated with the specified key in a list structure.
   * 
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return True if the value was set successfully, false otherwise.
   */
  public boolean lSet(String key, List<T> value) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      return true;
    } catch (Exception e) {
      log.error("Error setting list values for key: {}", key, e);
      return false;
    }
  }

  /**
   * Sets the value associated with the specified key in a list structure.
   * 
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return True if the value was set successfully, false otherwise.
   */
  public boolean lUpdateIndex(String key, long index, T value) {
    try {
      redisTemplate.opsForList().set(key, index, value);
      return true;
    } catch (Exception e) {
      log.error("Error updating list value for key: {}", key, e);
      return false;
    }
  }

  /**
   * Removes the first occurrence of the specified value from the list associated with the specified key.
   * 
   * @param key The key to remove the value from.
   * @param count The number of occurrences to remove.
   * @param value The value to remove.
   * @return The number of occurrences removed.
   */
  public long lRemove(String key, long count, T value) {
    try {
      Long remove = redisTemplate.opsForList().remove(key, count, value);
      return remove;
    } catch (Exception e) {
      log.error("Error removing list value for key: {}", key, e);
      return 0;
    }
  }

  /**
   * Sets the value associated with the specified key in a sorted set structure.
   * 
   * @param key The key to set the value for.
   * @param value The value to set.
   * @param score The score associated with the value.
   * @return True if the value was set successfully, false otherwise.
   */
  public boolean zSet(String key, T value, double score) {
    try {
      redisTemplate.opsForZSet().add(key, value, score);
      return true;
    } catch (Exception e) {
      log.error("Error setting sorted set value for key: {}", key, e);
      return false;
    }
  }

  /**
   * Retrieves the values associated with the specified key in a sorted set structure within the specified range.
   * 
   * @param key The key to retrieve the values from.
   * @param start The start index of the range.
   * @param end The end index of the range.
   * @return The values within the specified range.
   */
  public Set<T> zGet(String key, long start, long end) {
    try {
      return redisTemplate.opsForZSet().range(key, start, end);
    } catch (Exception e) {
      log.error("Error getting sorted set values for key: {}", key, e);
      return Collections.emptySet();
    }
  }


}
