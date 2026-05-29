package py.com.pysistemas.commonsecurity.annotation;

import java.lang.annotation.*;

/*
 * @author josec
 * @project comogastoapp
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireTenant {
}