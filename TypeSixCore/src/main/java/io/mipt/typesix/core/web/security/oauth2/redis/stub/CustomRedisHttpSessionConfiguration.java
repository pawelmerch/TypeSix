package io.mipt.typesix.core.web.security.oauth2.redis.stub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.SessionIdGenerator;
import org.springframework.session.UuidSessionIdGenerator;
import org.springframework.session.data.redis.config.annotation.web.http.AbstractRedisHttpSessionConfiguration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;

import java.time.Duration;
import java.util.Map;

@Profile("!test")
@Configuration(proxyBeanMethods = false)
public class CustomRedisHttpSessionConfiguration extends AbstractRedisHttpSessionConfiguration<CustomRedisSessionRepository>
        implements EmbeddedValueResolverAware, ImportAware {

    private StringValueResolver embeddedValueResolver;

    private SessionIdGenerator sessionIdGenerator = UuidSessionIdGenerator.getInstance();

    @Bean
    @Override
    public CustomRedisSessionRepository sessionRepository() {
        RedisTemplate<String, Object> redisTemplate = createRedisTemplate();
        CustomRedisSessionRepository sessionRepository = new CustomRedisSessionRepository(redisTemplate);
        sessionRepository.setDefaultMaxInactiveInterval(getMaxInactiveInterval());
        if (StringUtils.hasText(getRedisNamespace())) {
            sessionRepository.setRedisKeyNamespace(getRedisNamespace());
        }
        sessionRepository.setFlushMode(getFlushMode());
        sessionRepository.setSaveMode(getSaveMode());
        sessionRepository.setSessionIdGenerator(this.sessionIdGenerator);
        getSessionRepositoryCustomizers()
                .forEach((sessionRepositoryCustomizer) -> sessionRepositoryCustomizer.customize(sessionRepository));
        return sessionRepository;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.embeddedValueResolver = resolver;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> attributeMap = importMetadata
                .getAnnotationAttributes(EnableRedisHttpSession.class.getName());
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(attributeMap);
        if (attributes == null) {
            return;
        }
        setMaxInactiveInterval(Duration.ofSeconds(attributes.<Integer>getNumber("maxInactiveIntervalInSeconds")));
        String redisNamespaceValue = attributes.getString("redisNamespace");
        if (StringUtils.hasText(redisNamespaceValue)) {
            setRedisNamespace(this.embeddedValueResolver.resolveStringValue(redisNamespaceValue));
        }
        setFlushMode(attributes.getEnum("flushMode"));
        setSaveMode(attributes.getEnum("saveMode"));
    }

    @Autowired(required = false)
    public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }
}
