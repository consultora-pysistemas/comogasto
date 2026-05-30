package py.com.pysistemas.starter.web.autoconfigure;

import org.springframework.context.annotation.Import;

import py.com.pysistemas.commonweb.error.GlobalExceptionHandler;
import py.com.pysistemas.commonweb.filter.WebFilterConfiguration;

/*
 * @author josec
 * @project comogastoapp
 */
@Import({
        WebFilterConfiguration.class,
        GlobalExceptionHandler.class
})
public class ComogastoWebAutoConfiguration {
}
