package py.com.pysistemas.commonsecurity.context;

import java.util.UUID;

/*
 * @author josec
 * @project comogastoapp
 */
public final class TenantContext {
    private static final ThreadLocal<UUID> TENANT_ID = new ThreadLocal<>();
    private static final ThreadLocal<UUID> COMPANY_ID = new ThreadLocal<>();
    private static final ThreadLocal<UUID> BRANCH_ID = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setTenantId(UUID tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static UUID getTenantId() {
        return TENANT_ID.get();
    }

    public static void setCompanyId(UUID companyId) {
        COMPANY_ID.set(companyId);
    }

    public static UUID getCompanyId() {
        return COMPANY_ID.get();
    }

    public static void setBranchId(UUID administrationId) {
        BRANCH_ID.set(administrationId);
    }

    public static UUID getBranchId() {
        return BRANCH_ID.get();
    }

    public static boolean hasTenant() {
        return TENANT_ID.get() != null;
    }

    public static void clear() {
        TENANT_ID.remove();
        COMPANY_ID.remove();
        BRANCH_ID.remove();
    }
}
