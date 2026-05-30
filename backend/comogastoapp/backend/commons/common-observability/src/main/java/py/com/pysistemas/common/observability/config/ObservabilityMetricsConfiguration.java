package py.com.pysistemas.common.observability.config;

import org.springframework.context.annotation.Bean;

import io.micrometer.core.instrument.MeterRegistry;
import py.com.pysistemas.common.observability.metrics.PlatformMetrics;

/*
 * @author josec
 * @project comogastoapp
 */
public class ObservabilityMetricsConfiguration {
    @Bean
    public PlatformMetrics platformMetrics(MeterRegistry meterRegistry) {
        return new PlatformMetrics(meterRegistry);
    }
}
