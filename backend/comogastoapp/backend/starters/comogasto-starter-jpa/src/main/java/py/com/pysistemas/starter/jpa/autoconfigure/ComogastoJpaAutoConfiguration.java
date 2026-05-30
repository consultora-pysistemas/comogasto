package py.com.pysistemas.starter.jpa.autoconfigure;

import org.springframework.context.annotation.Import;

import py.com.pysistemas.common.jpa.configuration.CommonJpaConfiguration;

/*
 * @author josec
 * @project comogastoapp
 */
@Import(CommonJpaConfiguration.class)
public class ComogastoJpaAutoConfiguration {
}
