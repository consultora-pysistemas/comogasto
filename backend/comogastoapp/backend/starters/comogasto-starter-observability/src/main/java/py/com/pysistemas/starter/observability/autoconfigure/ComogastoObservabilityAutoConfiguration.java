package py.com.pysistemas.starter.observability.autoconfigure;

import org.springframework.context.annotation.Import;

import py.com.pysistemas.common.observability.config.CommonObservabilityConfiguration;

/*
 * @author josec
 * @project comogastoapp
 */
@Import(CommonObservabilityConfiguration.class)
public class ComogastoObservabilityAutoConfiguration {
}
