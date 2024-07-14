package io.mipt.typesix.core.web.security.oauth2.redis.stub;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.*;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Log
@Profile("!test")
public class CustomRedisSessionRepository implements SessionRepository<CustomRedisSessionRepository.RedisSession> {
    /**
     * The default namespace for each key and channel in Redis used by Spring Session.
     */
    public static final String DEFAULT_KEY_NAMESPACE = "spring:session";

    private final RedisOperations<String, Object> sessionRedisOperations;

    private Duration defaultMaxInactiveInterval = Duration.ofSeconds(MapSession.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS);

    private String keyNamespace = DEFAULT_KEY_NAMESPACE + ":";

    private FlushMode flushMode = FlushMode.IMMEDIATE;

    private SaveMode saveMode = SaveMode.ON_SET_ATTRIBUTE;

    private SessionIdGenerator sessionIdGenerator = UuidSessionIdGenerator.getInstance();

    private BiFunction<String, Map<String, Object>, MapSession> redisSessionMapper = new CustomRedisSessionMapper();

    /**
     * Create a new {@link RedisSessionRepository} instance.
     * @param sessionRedisOperations the {@link RedisOperations} to use for managing
     * sessions
     */
    public CustomRedisSessionRepository(RedisOperations<String, Object> sessionRedisOperations) {
        Assert.notNull(sessionRedisOperations, "sessionRedisOperations mut not be null");
        this.sessionRedisOperations = sessionRedisOperations;
    }

    /**
     * Set the maximum inactive interval in seconds between requests before newly created
     * sessions will be invalidated. A negative time indicates that the session will never
     * time out. The default is 30 minutes.
     * @param defaultMaxInactiveInterval the default maxInactiveInterval
     */
    public void setDefaultMaxInactiveInterval(Duration defaultMaxInactiveInterval) {
        Assert.notNull(defaultMaxInactiveInterval, "defaultMaxInactiveInterval must not be null");
        this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
    }

    /**
     * Set the Redis key namespace.
     * @param namespace the Redis key namespace
     */
    public void setRedisKeyNamespace(String namespace) {
        Assert.hasText(namespace, "namespace must not be empty");
        this.keyNamespace = namespace.trim() + ":";
    }

    /**
     * Set the flush mode.
     * @param flushMode the flush mode
     */
    public void setFlushMode(FlushMode flushMode) {
        Assert.notNull(flushMode, "flushMode must not be null");
        this.flushMode = flushMode;
    }

    /**
     * Set the save mode.
     * @param saveMode the save mode
     */
    public void setSaveMode(SaveMode saveMode) {
        Assert.notNull(saveMode, "saveMode must not be null");
        this.saveMode = saveMode;
    }

    @Override
    public RedisSession createSession() {
        MapSession cached = new MapSession(this.sessionIdGenerator);
        cached.setMaxInactiveInterval(this.defaultMaxInactiveInterval);
        RedisSession session = new RedisSession(cached, true);
        session.flushIfRequired();
        return session;
    }

    @Override
    public void save(RedisSession session) {
        if (!session.isNew) {
            String key = getSessionKey(session.hasChangedSessionId() ? session.originalSessionId : session.getId());
            Boolean sessionExists = this.sessionRedisOperations.hasKey(key);
            if (sessionExists == null || !sessionExists) {
                log.warning("Session was invalidated");
                for (var name : session.getAttributeNames()) {
                    log.warning("Attribute " + name + " is " + session.getAttribute(name));
                }
            }
        }
        session.save();
    }

    @Override
    public RedisSession findById(String sessionId) {
        String key = getSessionKey(sessionId);
        Map<String, Object> entries = this.sessionRedisOperations.<String, Object>opsForHash().entries(key);
        if (entries.isEmpty()) {
            return null;
        }
        MapSession session = this.redisSessionMapper.apply(sessionId, entries);
        if (session == null || session.isExpired()) {
            deleteById(sessionId);
            return null;
        }
        return new RedisSession(session, false);
    }

    @Override
    public void deleteById(String sessionId) {
        String key = getSessionKey(sessionId);
        this.sessionRedisOperations.delete(key);
    }

    /**
     * Returns the {@link RedisOperations} used for sessions.
     * @return the {@link RedisOperations} used for sessions
     */
    public RedisOperations<String, Object> getSessionRedisOperations() {
        return this.sessionRedisOperations;
    }

    private String getSessionKey(String sessionId) {
        return this.keyNamespace + "sessions:" + sessionId;
    }

    private static String getAttributeKey(String attributeName) {
        return CustomRedisSessionMapper.ATTRIBUTE_PREFIX + attributeName;
    }

    /**
     * Set the {@link SessionIdGenerator} to use to generate session ids.
     * @param sessionIdGenerator the {@link SessionIdGenerator} to use
     * @since 3.2
     */
    public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
        Assert.notNull(sessionIdGenerator, "sessionIdGenerator cannot be null");
        this.sessionIdGenerator = sessionIdGenerator;
    }

    /**
     * Set the {@link BiFunction} used to map {@link MapSession} to
     * @param redisSessionMapper the mapper to use, cannot be null
     * @since 3.2
     */
    public void setRedisSessionMapper(BiFunction<String, Map<String, Object>, MapSession> redisSessionMapper) {
        Assert.notNull(redisSessionMapper, "redisSessionMapper cannot be null");
        this.redisSessionMapper = redisSessionMapper;
    }

    /**
     * An internal {@link Session} implementation used by this {@link SessionRepository}.
     */
    final class RedisSession implements Session {

        private final MapSession cached;

        private final Map<String, Object> delta = new HashMap<>();

        private boolean isNew;

        private String originalSessionId;

        RedisSession(MapSession cached, boolean isNew) {
            this.cached = cached;
            this.isNew = isNew;
            this.originalSessionId = cached.getId();
            if (this.isNew) {
                this.delta.put(CustomRedisSessionMapper.CREATION_TIME_KEY, cached.getCreationTime().toEpochMilli());
                this.delta.put(CustomRedisSessionMapper.MAX_INACTIVE_INTERVAL_KEY,
                        (int) cached.getMaxInactiveInterval().getSeconds());
                this.delta.put(CustomRedisSessionMapper.LAST_ACCESSED_TIME_KEY, cached.getLastAccessedTime().toEpochMilli());
            }
            if (this.isNew || (CustomRedisSessionRepository.this.saveMode == SaveMode.ALWAYS)) {
                getAttributeNames().forEach((attributeName) -> this.delta.put(getAttributeKey(attributeName),
                        cached.getAttribute(attributeName)));
            }
        }

        @Override
        public String getId() {
            return this.cached.getId();
        }

        @Override
        public String changeSessionId() {
            String newSessionId = CustomRedisSessionRepository.this.sessionIdGenerator.generate();
            this.cached.setId(newSessionId);
            return newSessionId;
        }

        @Override
        public <T> T getAttribute(String attributeName) {
            T attributeValue = this.cached.getAttribute(attributeName);
            if (attributeValue != null && CustomRedisSessionRepository.this.saveMode.equals(SaveMode.ON_GET_ATTRIBUTE)) {
                this.delta.put(getAttributeKey(attributeName), attributeValue);
            }
            return attributeValue;
        }

        @Override
        public Set<String> getAttributeNames() {
            return this.cached.getAttributeNames();
        }

        @Override
        public void setAttribute(String attributeName, Object attributeValue) {
            this.cached.setAttribute(attributeName, attributeValue);
            this.delta.put(getAttributeKey(attributeName), attributeValue);
            flushIfRequired();
        }

        @Override
        public void removeAttribute(String attributeName) {
            setAttribute(attributeName, null);
        }

        @Override
        public Instant getCreationTime() {
            return this.cached.getCreationTime();
        }

        @Override
        public void setLastAccessedTime(Instant lastAccessedTime) {
            this.cached.setLastAccessedTime(lastAccessedTime);
            this.delta.put(CustomRedisSessionMapper.LAST_ACCESSED_TIME_KEY, getLastAccessedTime().toEpochMilli());
            flushIfRequired();
        }

        @Override
        public Instant getLastAccessedTime() {
            return this.cached.getLastAccessedTime();
        }

        @Override
        public void setMaxInactiveInterval(Duration interval) {
            this.cached.setMaxInactiveInterval(interval);
            this.delta.put(CustomRedisSessionMapper.MAX_INACTIVE_INTERVAL_KEY, (int) getMaxInactiveInterval().getSeconds());
            flushIfRequired();
        }

        @Override
        public Duration getMaxInactiveInterval() {
            return this.cached.getMaxInactiveInterval();
        }

        @Override
        public boolean isExpired() {
            return this.cached.isExpired();
        }

        private void flushIfRequired() {
            if (CustomRedisSessionRepository.this.flushMode == FlushMode.IMMEDIATE) {
                save();
            }
        }

        private boolean hasChangedSessionId() {
            return !getId().equals(this.originalSessionId);
        }

        private void save() {
            saveChangeSessionId();
            saveDelta();
            if (this.isNew) {
                this.isNew = false;
            }
        }

        private void saveChangeSessionId() {
            if (hasChangedSessionId()) {
                if (!this.isNew) {
                    String originalSessionIdKey = getSessionKey(this.originalSessionId);
                    String sessionIdKey = getSessionKey(getId());
                    CustomRedisSessionRepository.this.sessionRedisOperations.rename(originalSessionIdKey, sessionIdKey);
                }
                this.originalSessionId = getId();
            }
        }

        private void saveDelta() {
            if (this.delta.isEmpty()) {
                return;
            }
            String key = getSessionKey(getId());
            CustomRedisSessionRepository.this.sessionRedisOperations.opsForHash().putAll(key, new HashMap<>(this.delta));
            CustomRedisSessionRepository.this.sessionRedisOperations.expireAt(key,
                    Instant.ofEpochMilli(getLastAccessedTime().toEpochMilli())
                            .plusSeconds(getMaxInactiveInterval().getSeconds()));
            this.delta.clear();
        }

    }

}
