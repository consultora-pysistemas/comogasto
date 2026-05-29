package py.com.pysistemas.common.events.consumer;

import java.util.Set;

import py.com.pysistemas.common.events.envelope.EventEnvelope;
import py.com.pysistemas.commonsecurity.context.TenantContext;
import py.com.pysistemas.commonsecurity.context.UserContext;
import py.com.pysistemas.commonweb.request.CorrelationIdContext;

/*
 * @author josec
 * @project comogastoapp
 */
public final class EventContextPropagation {
    private EventContextPropagation() {
    }

    public static void apply(EventEnvelope<?> event) {
        TenantContext.setTenantId(event.tenantId());
        TenantContext.setCompanyId(event.companyId());
        TenantContext.setBranchId(event.branchId());

        UserContext.setUserId(event.userId());
        UserContext.setUsername(event.username());
        UserContext.setRoles(Set.of());
        UserContext.setPermissions(Set.of());

        CorrelationIdContext.set(event.correlationId());
    }

    public static void clear() {
        TenantContext.clear();
        UserContext.clear();
        CorrelationIdContext.clear();
    }
}
