package py.com.pysistemas.common.tracing.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/*
 * @author josec
 * @project comogastoapp
 */
public final class TraceNameResolver {
    private TraceNameResolver() {
    }

    public static String resolve(
            ProceedingJoinPoint joinPoint,
            String configuredName
    ) {
        if (configuredName != null && !configuredName.isBlank()) {
            return configuredName;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        return signature.getDeclaringType().getSimpleName()
                + "."
                + signature.getMethod().getName();
    }
}
