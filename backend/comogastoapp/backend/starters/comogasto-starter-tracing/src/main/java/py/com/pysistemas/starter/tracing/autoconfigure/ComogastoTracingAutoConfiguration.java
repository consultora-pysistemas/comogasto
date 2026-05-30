package py.com.pysistemas.starter.tracing.autoconfigure;

import org.springframework.context.annotation.Import;

import py.com.pysistemas.common.tracing.CommonTracingConfiguration;

/*
 * @author josec
 * @project comogastoapp
 */
@Import(CommonTracingConfiguration.class)
public class ComogastoTracingAutoConfiguration {
}
