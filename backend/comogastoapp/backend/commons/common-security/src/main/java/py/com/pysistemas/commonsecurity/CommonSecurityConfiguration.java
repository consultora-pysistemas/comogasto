package py.com.pysistemas.commonsecurity;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import py.com.pysistemas.commonsecurity.filter.SecurityContextPropagationFilter;
import py.com.pysistemas.commonsecurity.permission.PermissionEvaluatorService;
import py.com.pysistemas.commonsecurity.permission.SecurityAuthorizationAspect;
import py.com.pysistemas.commonsecurity.propagation.HeaderPropagationService;

/*
 * @author josec
 * @project comogastoapp
 */
public class CommonSecurityConfiguration {
    @Bean
    public PermissionEvaluatorService permissionEvaluatorService() {
        return new PermissionEvaluatorService();
    }

    @Bean
    public SecurityAuthorizationAspect securityAuthorizationAspect(
            PermissionEvaluatorService permissionEvaluatorService
    ) {
        return new SecurityAuthorizationAspect(permissionEvaluatorService);
    }

    @Bean
    public HeaderPropagationService headerPropagationService() {
        return new HeaderPropagationService();
    }

    @Bean
    public FilterRegistrationBean<SecurityContextPropagationFilter> securityContextPropagationFilter() {
        FilterRegistrationBean<SecurityContextPropagationFilter> registration =
                new FilterRegistrationBean<>();

        registration.setFilter(new SecurityContextPropagationFilter());
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 20);
        registration.addUrlPatterns("/*");

        return registration;
    }
}
