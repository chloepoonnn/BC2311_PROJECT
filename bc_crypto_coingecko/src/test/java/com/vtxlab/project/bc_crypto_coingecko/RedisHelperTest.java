package com.vtxlab.project.bc_crypto_coingecko;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.project.bc_crypto_coingecko.redis.RedisHelper;

public class RedisHelperTest {

  @InjectMocks
  private RedisHelper<String> redisHelper;

  @Mock
  private RedisTemplate<String, String> redisTemplate;

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private RedisConnectionFactory redisConnectionFactory;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    // redisHelper.setRedisTemplate(redisTemplate);
    // redisHelper.setObjectMapper(objectMapper);
  }

  // Add missing setter methods
  public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Test
  void testExpire() {
    String key = "testKey";
    long time = 60;
    assertTrue(redisHelper.expire(key, time));
  }

  @Test
  void testGetExpire() {
    String key = "testKey";
    long expectedExpireTime = 60;
    // Mock behavior of redisTemplate.getExpire
    when(redisTemplate.getExpire(key, TimeUnit.SECONDS))
        .thenReturn(expectedExpireTime);
    assertEquals(expectedExpireTime, redisHelper.getExpire(key));
  }

  @Test
  void testHasKey() {
    String key = "testKey";
    // Mock behavior of redisTemplate.hasKey
    when(redisTemplate.hasKey(key)).thenReturn(true);
    assertTrue(redisHelper.hasKey(key));
  }

  @Test
  void testDel() {
    String key = "testKey";
    redisHelper.del(key);
    // Verify that redisTemplate.delete is called with the correct key
    verify(redisTemplate).delete(key);
  }

  @Test
  void testGet() {
    String key = "testKey";
    String expectedValue = "testValue";
    // Mock behavior of redisTemplate.opsForValue().get
    when(redisTemplate.opsForValue().get(key)).thenReturn(expectedValue);
    assertEquals(expectedValue, redisHelper.get(key));
  }

  @Test
  void testGetWithClass() throws JsonProcessingException {
    String key = "testKey";
    String expectedValue = "testValue";
    Class<String> clazz = String.class;
    // Mock behavior of redisTemplate.opsForValue().get
    when(redisTemplate.opsForValue().get(key)).thenReturn(expectedValue);
    // Mock behavior of objectMapper.readValue
    when(objectMapper.readValue(expectedValue, clazz))
        .thenReturn(expectedValue);
    assertEquals(expectedValue, redisHelper.get(key, clazz));
  }

  @Test
  void testSet() {
    String key = "testKey";
    String value = "testValue";
    assertTrue(redisHelper.set(key, value));
    // Verify that redisTemplate.opsForValue().set is called with the correct key and value
    verify(redisTemplate).opsForValue().set(key, value);
  }

  @Test
  void testSetWithExpiration() {
    String key = "testKey";
    String value = "testValue";
    long time = 60;
    assertTrue(redisHelper.set(key, value, time));
    // Verify that redisTemplate.opsForValue().set is called with the correct key, value, and time
    verify(redisTemplate).opsForValue().set(key, value, time, TimeUnit.SECONDS);
  }

  @Test
  void testIncr() {
    String key = "testKey";
    long delta = 1;
    long expectedValue = 2;
    // Mock behavior of redisTemplate.opsForValue().increment
    when(redisTemplate.opsForValue().increment(key, delta))
        .thenReturn(expectedValue);
    assertEquals(expectedValue, redisHelper.incr(key, delta));
  }

  @Test
  void testDecr() {
    String key = "testKey";
    long delta = 1;
    long expectedValue = 0;
    // Mock behavior of redisTemplate.opsForValue().increment
    when(redisTemplate.opsForValue().increment(key, -delta))
        .thenReturn(expectedValue);
    assertEquals(expectedValue, redisHelper.decr(key, delta));
  }

  // Other test methods...

  @Test
  void testHGetAll() throws JsonProcessingException {
    String key = "testKey";
    Class<String> clazz = String.class;
    Map<Object, Object> hashEntries = new HashMap<>();
    hashEntries.put("key1", "value1");
    hashEntries.put("key2", "value2");
    // Mock behavior of redisTemplate.opsForHash().entries
    when(redisTemplate.opsForHash().entries(key)).thenReturn(hashEntries);
    // Mock behavior of objectMapper.readValue
    when(objectMapper.readValue("value1", clazz)).thenReturn("value1");
    when(objectMapper.readValue("value2", clazz)).thenReturn("value2");
    Map<String, String> expectedMap = new LinkedHashMap<>();
    expectedMap.put("key1", "value1");
    expectedMap.put("key2", "value2");
    assertEquals(expectedMap, redisHelper.hgetall(key, clazz));
  }

  // Other test methods...

  @Test
  void testSGet() throws JsonProcessingException {
    String key = "testKey";
    Class<String> clazz = String.class;
    Set<Object> members = new HashSet<>();
    members.add("value1");
    members.add("value2");
    Set<String> result =
        members.stream().map(Object::toString).collect(Collectors.toSet());
    // Mock behavior of redisTemplate.opsForSet().members
    when(redisTemplate.opsForSet().members(key)).thenReturn(result);
    // Mock behavior of objectMapper.readValue
    when(objectMapper.readValue("value1", clazz)).thenReturn("value1");
    when(objectMapper.readValue("value2", clazz)).thenReturn("value2");
    Set<String> expectedSet = new HashSet<>();
    expectedSet.add("value1");
    expectedSet.add("value2");
    assertEquals(expectedSet, redisHelper.sGet(key, clazz));
  }

  // Other test methods...

  @Test
  void testSSet() {
    String key = "testKey";
    String[] values = {"value1", "value2"};
    assertTrue(redisHelper.sSet(key, values));
    // Verify that redisTemplate.opsForSet().add is called with the correct key and values
    verify(redisTemplate).opsForSet().add(key, values);
  }

  // Other test methods...

  @Test
  void testLGet() throws JsonProcessingException {
    String key = "testKey";
    long start = 0;
    long end = -1;
    Class<String> clazz = String.class;
    List<Object> range = Arrays.asList("value1", "value2");
    // Mock behavior of redisTemplate.opsForList().range
    List<String> result =
        range.stream().map(Object::toString).collect(Collectors.toList());
    when(redisTemplate.opsForList().range(key, start, end)).thenReturn(result);
    // Mock behavior of objectMapper.readValue
    when(objectMapper.readValue("value1", clazz)).thenReturn("value1");
    when(objectMapper.readValue("value2", clazz)).thenReturn("value2");
    List<String> expectedList = Arrays.asList("value1", "value2");
    assertEquals(expectedList, redisHelper.lGet(key, start, end, clazz));
  }

  // Other test methods...

  @Test
  void testLSet() {
    String key = "testKey";
    String value = "testValue";
    assertTrue(redisHelper.lSet(key, value));
    // Verify that redisTemplate.opsForList().rightPush is called with the correct key and value
    verify(redisTemplate).opsForList().rightPush(key, value);
  }

  @Test
  void testLSet2() {
    String key = "testKey";
    String value = "testValue";
    Long expectedIndex = 0L;
    // Mock behavior of redisTemplate.opsForList().leftPush
    when(redisTemplate.opsForList().leftPush(key, value))
        .thenReturn(expectedIndex);
    assertEquals(expectedIndex, redisHelper.lSet2(key, value));
  }

  // Other test methods...

  @Test
  void testLSetWithList() {
    String key = "testKey";
    List<String> values = Arrays.asList("value1", "value2");
    assertTrue(redisHelper.lSet(key, values));
    // Verify that redisTemplate.opsForList().rightPushAll is called with the correct key and values
    verify(redisTemplate).opsForList().rightPushAll(key, values);
  }

  // Other test methods...

  @Test
  void testLUpdateIndex() {
    String key = "testKey";
    long index = 0;
    String value = "testValue";
    assertTrue(redisHelper.lUpdateIndex(key, index, value));
    // Verify that redisTemplate.opsForList().set is called with the correct key, index, and value
    verify(redisTemplate).opsForList().set(key, index, value);
  }

  // Other test methods...

  @Test
  void testLRemove() {
    String key = "testKey";
    long count = 1;
    String value = "testValue";
    Long expectedRemoveCount = 1L;
    // Mock behavior of redisTemplate.opsForList().remove
    when(redisTemplate.opsForList().remove(key, count, value))
        .thenReturn(expectedRemoveCount);
    assertEquals(expectedRemoveCount, redisHelper.lRemove(key, count, value));
  }
}
