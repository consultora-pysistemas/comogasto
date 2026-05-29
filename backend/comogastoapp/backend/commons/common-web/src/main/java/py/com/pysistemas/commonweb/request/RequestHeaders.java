package py.com.pysistemas.commonweb.request;

/*
 * @author josec
 * @project comogastoapp
 */
public final class RequestHeaders {
    private RequestHeaders() {
    }

    public static final String X_CORRELATION_ID = "X-Correlation-Id";
    public static final String X_REQUEST_ID = "X-Request-Id";
    public static final String X_TRACE_ID = "X-Trace-Id";
    public static final String X_TENANT_ID = "X-Tenant-Id";
    public static final String X_COMPANY_ID = "X-Company-Id";
    public static final String X_BRANCH_ID = "X-Branch-Id";
    public static final String X_USER_ID = "X-User-Id";
    public static final String X_USERNAME = "X-Username";
    public static final String X_SOURCE_SYSTEM = "X-Source-System";
    public static final String X_PROCESS_ID = "X-Process-Id";
    public static final String X_PARENT_STEP_ID = "X-Parent-Step-Id";
}
