package py.com.pysistemas.common.audit.annotation;

import java.lang.annotation.*;

/*
 * @author josec
 * @project comogastoapp
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveField {
}
