package py.com.pysistemas.commonsecurity.jwt;

import java.util.Set;
import java.util.UUID;

/*
 * @author josec
 * @project comogastoapp
 */
public record AuthenticatedUser(
        UUID userId,
        String username,
        String email,
        UUID tenantId,
        UUID companyId,
        UUID administrationId,
        Set<String> roles,
        Set<String> permissions
) {
}