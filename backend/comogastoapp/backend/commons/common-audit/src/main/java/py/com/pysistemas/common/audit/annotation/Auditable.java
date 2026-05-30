package py.com.pysistemas.common.audit.annotation;

import java.lang.annotation.*;

import py.com.pysistemas.common.audit.model.AuditType;

/*
 * @author josec
 * @project comogastoapp
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auditable {

    String action();

    String module();

    String entity() default "";

    AuditType type() default AuditType.FUNCTIONAL;

    boolean includeRequest() default true;

    boolean includeResponse() default false;

    boolean includeException() default true;
}
