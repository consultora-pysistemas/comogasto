package py.com.pysistemas.starter.audit.autoconfigure;

import org.springframework.context.annotation.Import;

import py.com.pysistemas.common.audit.CommonAuditConfiguration;

/*
 * @author josec
 * @project comogastoapp
 */
@Import(CommonAuditConfiguration.class)
public class ComogastoAuditAutoConfiguration {
}
