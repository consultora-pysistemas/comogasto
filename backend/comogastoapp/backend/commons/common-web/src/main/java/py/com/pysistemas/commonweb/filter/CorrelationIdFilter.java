package py.com.pysistemas.commonweb.filter;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import py.com.pysistemas.commonweb.request.CorrelationIdContext;
import py.com.pysistemas.commonweb.request.RequestHeaders;

/*
 * @author josec
 * @project comogastoapp
 */
public class CorrelationIdFilter  extends OncePerRequestFilter {
    private static final String MDC_CORRELATION_ID = "correlationId";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String correlationId = request.getHeader(RequestHeaders.X_CORRELATION_ID);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        try {
            CorrelationIdContext.set(correlationId);
            MDC.put(MDC_CORRELATION_ID, correlationId);

            response.setHeader(RequestHeaders.X_CORRELATION_ID, correlationId);

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_CORRELATION_ID);
            CorrelationIdContext.clear();
        }
    }
}
