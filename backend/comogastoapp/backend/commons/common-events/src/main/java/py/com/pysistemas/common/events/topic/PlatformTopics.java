package py.com.pysistemas.common.events.topic;

/*
 * @author josec
 * @project comogastoapp
 */
public final class PlatformTopics {
    private PlatformTopics() {
    }

    public static final String TENANT_CREATED_V1 = "tenant.created.v1";
    public static final String TENANT_UPDATED_V1 = "tenant.updated.v1";
    public static final String TENANT_DELETED_V1 = "tenant.deleted.v1";

    public static final String AUTH_LOGIN_SUCCEEDED_V1 = "auth.login.succeeded.v1";
    public static final String AUTH_LOGIN_FAILED_V1 = "auth.login.failed.v1";
    public static final String AUTH_LOGOUT_V1 = "auth.logout.v1";

    public static final String IAM_USER_CREATED_V1 = "iam.user.created.v1";
    public static final String IAM_USER_UPDATED_V1 = "iam.user.updated.v1";
    public static final String IAM_USER_DISABLED_V1 = "iam.user.disabled.v1";
    public static final String IAM_ROLE_CREATED_V1 = "iam.role.created.v1";

    public static final String AUDIT_EVENT_CREATED_V1 = "audit.event.created.v1";

    public static final String TRACING_PROCESS_STARTED_V1 = "tracing.process.started.v1";
    public static final String TRACING_STEP_STARTED_V1 = "tracing.step.started.v1";
    public static final String TRACING_STEP_COMPLETED_V1 = "tracing.step.completed.v1";
    public static final String TRACING_STEP_FAILED_V1 = "tracing.step.failed.v1";
    public static final String TRACING_PROCESS_COMPLETED_V1 = "tracing.process.completed.v1";
    public static final String TRACING_PROCESS_FAILED_V1 = "tracing.process.failed.v1";

    public static final String NOTIFICATION_REQUESTED_V1 = "notification.requested.v1";
    public static final String NOTIFICATION_SENT_V1 = "notification.sent.v1";
    public static final String NOTIFICATION_FAILED_V1 = "notification.failed.v1";

    public static final String GATEWAY_REQUEST_RECEIVED_V1 = "gateway.request.received.v1";
}
