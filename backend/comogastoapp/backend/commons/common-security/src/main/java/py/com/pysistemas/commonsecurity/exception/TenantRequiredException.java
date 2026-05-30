package py.com.pysistemas.commonsecurity.exception;

/*
 * @author josec
 * @project comogastoapp
 */
public class TenantRequiredException extends RuntimeException {
    public TenantRequiredException() {
        super("Tenant context is required");
    }
}
