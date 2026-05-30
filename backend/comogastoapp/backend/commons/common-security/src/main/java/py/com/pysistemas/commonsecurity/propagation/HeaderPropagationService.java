package py.com.pysistemas.commonsecurity.propagation;

import java.util.HashMap;
import java.util.Map;

import py.com.pysistemas.commonsecurity.context.TenantContext;
import py.com.pysistemas.commonsecurity.context.UserContext;
import py.com.pysistemas.commonweb.request.CorrelationIdContext;
import py.com.pysistemas.commonweb.request.RequestHeaders;

/*
 * @author josec
 * @project comogastoapp
 */
public class HeaderPropagationService {
    public Map<String, String> currentHeaders() {
        Map<String, String> headers = new HashMap<>();

        put(headers, RequestHeaders.X_CORRELATION_ID, CorrelationIdContext.get());
        put(headers, RequestHeaders.X_TENANT_ID, valueOf(TenantContext.getTenantId()));
        put(headers, RequestHeaders.X_COMPANY_ID, valueOf(TenantContext.getCompanyId()));
        put(headers, RequestHeaders.X_BRANCH_ID, valueOf(TenantContext.getBranchId()));
        put(headers, RequestHeaders.X_USER_ID, valueOf(UserContext.getUserId()));
        put(headers, RequestHeaders.X_USERNAME, UserContext.getUsername());

        return headers;
    }

    private void put(Map<String, String> headers, String key, String value) {
        if (value != null && !value.isBlank()) {
            headers.put(key, value);
        }
    }

    private String valueOf(Object value) {
        return value == null ? null : value.toString();
    }
}
