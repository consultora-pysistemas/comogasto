package py.com.pysistemas.common.tracing.annotation;

import java.lang.annotation.*;

import py.com.pysistemas.common.tracing.model.TraceStepType;

/*
 * @author josec
 * @project comogastoapp
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Traced {

    String name() default "";

    TraceStepType type() default TraceStepType.SERVICE_METHOD;

    boolean startProcess() default false;

    boolean finishProcess() default false;

    boolean includeArguments() default false;

    boolean includeResult() default false;
}
