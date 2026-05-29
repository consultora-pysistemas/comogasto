package py.com.pysistemas.common.observability.config;

import org.springframework.context.annotation.Bean;

import py.com.pysistemas.common.observability.health.ComoGastoPlatformHealthIndicator;

/*
 * @author josec
 * @project comogastoapp
 */
public class ObservabilityHealthConfiguration {
    @Bean
    public ComoGastoPlatformHealthIndicator comoGastoPlatformHealthIndicator() {
        return new ComoGastoPlatformHealthIndicator();
    }
}
