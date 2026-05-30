package py.com.pysistemas.common.observability.logging;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import py.com.pysistemas.common.tracing.context.TraceContext;
import py.com.pysistemas.commonsecurity.context.TenantContext;
import py.com.pysistemas.commonsecurity.context.UserContext;
import py.com.pysistemas.commonweb.request.CorrelationIdContext;

/*
 * @author josec
 * @project comogastoapp
 */
public class MdcLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            put("correlationId", CorrelationIdContext.get());
            put("traceId", TraceContext.getTraceId());
            put("processId", valueOf(TraceContext.getProcessId()));
            put("tenantId", valueOf(TenantContext.getTenantId()));
            put("companyId", valueOf(TenantContext.getCompanyId()));
            put("administrationId", valueOf(TenantContext.getBranchId()));
            put("userId", valueOf(UserContext.getUserId()));
            put("username", UserContext.getUsername());

            filterChain.doFilter(request, response);

        } finally {
            MDC.clear();
        }
    }

    private void put(String key, String value) {
        if (value != null && !value.isBlank()) {
            MDC.put(key, value);
        }
    }

    private String valueOf(Object value) {
        return value == null ? null : value.toString();
    }
}
