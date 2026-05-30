package py.com.pysistemas.starter.events.autoconfigure;

import org.springframework.context.annotation.Import;

import py.com.pysistemas.common.events.CommonEventsConfiguration;

/*
 * @author josec
 * @project comogastoapp
 */
@Import(CommonEventsConfiguration.class)
public class ComogastoEventsAutoConfiguration {
}
