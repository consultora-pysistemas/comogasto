package py.com.pysistemas.common.jpa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import py.com.pysistemas.common.jpa.datasource.DataSourceRoutingAspect;

/*
 * @author josec
 * @project comogastoapp
 */
@EnableJpaAuditing
public class CommonJpaConfiguration {
    @Bean
    public DataSourceRoutingAspect dataSourceRoutingAspect() {
        return new DataSourceRoutingAspect();
    }
}
