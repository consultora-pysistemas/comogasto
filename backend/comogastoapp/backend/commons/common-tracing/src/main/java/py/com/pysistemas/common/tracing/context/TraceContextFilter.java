package py.com.pysistemas.common.tracing.context;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import py.com.pysistemas.commonweb.request.RequestHeaders;

/*
 * @author josec
 * @project comogastoapp
 */
public class TraceContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String traceId = request.getHeader(RequestHeaders.X_TRACE_ID);

        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
        }

        try {
            TraceContext.setTraceId(traceId);
            response.setHeader(RequestHeaders.X_TRACE_ID, traceId);

            filterChain.doFilter(request, response);
        } finally {
            TraceContext.clear();
        }
    }
}
