package py.com.pysistemas.common.observability.config;

import org.springframework.context.annotation.Import;

/*
 * @author josec
 * @project comogastoapp
 */
@Import({
        ObservabilityFilterConfiguration.class,
        ObservabilityMetricsConfiguration.class,
        ObservabilityHealthConfiguration.class
})
public class CommonObservabilityConfiguration {
}
