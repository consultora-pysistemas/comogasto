package py.com.pysistemas.starter.security.autoconfigure;

import org.springframework.context.annotation.Import;

import py.com.pysistemas.commonsecurity.CommonSecurityConfiguration;

/*
 * @author josec
 * @project comogastoapp
 */
@Import(CommonSecurityConfiguration.class)
public class ComoGastoSecurityAutoConfiguration {
}
