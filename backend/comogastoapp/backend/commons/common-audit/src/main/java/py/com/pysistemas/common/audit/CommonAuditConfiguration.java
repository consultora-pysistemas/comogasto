package py.com.pysistemas.common.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import py.com.pysistemas.common.audit.aspect.AuditAspect;
import py.com.pysistemas.common.audit.masking.SensitiveDataMasker;
import py.com.pysistemas.common.audit.publisher.AuditEventPublisher;
import py.com.pysistemas.common.events.envelope.EventEnvelopeFactory;
import py.com.pysistemas.common.events.publisher.EventPublisher;

/*
 * @author josec
 * @project comogastoapp
 */
public class CommonAuditConfiguration {
    @Bean
    public SensitiveDataMasker sensitiveDataMasker() {
        return new SensitiveDataMasker();
    }

    @Bean
    public AuditEventPublisher auditEventPublisher(
            EventPublisher eventPublisher,
            EventEnvelopeFactory eventEnvelopeFactory
    ) {
        return new AuditEventPublisher(eventPublisher, eventEnvelopeFactory);
    }

    @Bean
    public AuditAspect auditAspect(
            AuditEventPublisher auditEventPublisher,
            SensitiveDataMasker sensitiveDataMasker,
            @Value("${spring.application.name:unknown-service}") String sourceService
    ) {
        return new AuditAspect(
                auditEventPublisher,
                sensitiveDataMasker,
                sourceService
        );
    }
}
