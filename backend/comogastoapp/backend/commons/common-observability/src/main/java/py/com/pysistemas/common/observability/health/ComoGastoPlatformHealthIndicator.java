package py.com.pysistemas.common.observability.health;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;

/*
 * @author josec
 * @project comogastoapp
 */
public class ComoGastoPlatformHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.up()
                .withDetail("platform", "comogasto-platform")
                .withDetail("status", "running")
                .build();
    }
}
