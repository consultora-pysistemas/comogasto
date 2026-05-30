package py.com.pysistemas.common.jpa.datasource;

import java.lang.annotation.*;

/*
 * @author josec
 * @project comogastoapp
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseQueryDataSource {
}
