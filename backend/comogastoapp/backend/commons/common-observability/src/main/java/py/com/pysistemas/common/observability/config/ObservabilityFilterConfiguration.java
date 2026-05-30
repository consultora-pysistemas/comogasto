package py.com.pysistemas.common.observability.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import py.com.pysistemas.common.observability.logging.MdcLoggingFilter;

/*
 * @author josec
 * @project comogastoapp
 */
public class ObservabilityFilterConfiguration {
    @Bean
    public FilterRegistrationBean<MdcLoggingFilter> mdcLoggingFilter() {
        FilterRegistrationBean<MdcLoggingFilter> registration =
                new FilterRegistrationBean<>();

        registration.setFilter(new MdcLoggingFilter());
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 50);
        registration.addUrlPatterns("/*");

        return registration;
    }
}
