package py.com.pysistemas.common.events.metadata;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import py.com.pysistemas.commonsecurity.context.TenantContext;
import py.com.pysistemas.commonsecurity.context.UserContext;
import py.com.pysistemas.commonweb.request.CorrelationIdContext;

/*
 * @author josec
 * @project comogastoapp
 */
public final class EventMetadataBuilder {
    private EventMetadataBuilder() {
    }

    public static Map<String, Object> baseMetadata(String sourceService) {
        Map<String, Object> metadata = new HashMap<>();

        metadata.put("sourceService", sourceService);
        metadata.put("createdAt", Instant.now().toString());
        metadata.put("tenantId", valueOf(TenantContext.getTenantId()));
        metadata.put("companyId", valueOf(TenantContext.getCompanyId()));
        metadata.put("administrationId", valueOf(TenantContext.getBranchId()));
        metadata.put("userId", valueOf(UserContext.getUserId()));
        metadata.put("username", UserContext.getUsername());
        metadata.put("correlationId", CorrelationIdContext.get());

        return metadata;
    }

    private static String valueOf(UUID value) {
        return value == null ? null : value.toString();
    }
}
