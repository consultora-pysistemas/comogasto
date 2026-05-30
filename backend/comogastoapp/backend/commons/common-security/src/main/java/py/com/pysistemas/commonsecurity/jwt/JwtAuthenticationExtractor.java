package py.com.pysistemas.commonsecurity.jwt;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/*
 * @author josec
 * @project comogastoapp
 */
public final class JwtAuthenticationExtractor {
    private JwtAuthenticationExtractor() {
    }

    public static AuthenticatedUser extract(Jwt jwt) {
        return new AuthenticatedUser(
                getUuid(jwt, ComoGastoJwtClaims.USER_ID),
                getString(jwt, ComoGastoJwtClaims.USERNAME),
                getString(jwt, ComoGastoJwtClaims.EMAIL),
                getUuid(jwt, ComoGastoJwtClaims.TENANT_ID),
                getUuid(jwt, ComoGastoJwtClaims.COMPANY_ID),
                getUuid(jwt, ComoGastoJwtClaims.ADMINISTRATION_ID),
                getStringSet(jwt, ComoGastoJwtClaims.ROLES),
                getStringSet(jwt, ComoGastoJwtClaims.PERMISSIONS)
        );
    }

    private static String getString(Jwt jwt, String claim) {
        Object value = jwt.getClaim(claim);
        return value == null ? null : value.toString();
    }

    private static UUID getUuid(Jwt jwt, String claim) {
        String value = getString(jwt, claim);
        return value == null || value.isBlank() ? null : UUID.fromString(value);
    }

    private static Set<String> getStringSet(Jwt jwt, String claim) {
        Object value = jwt.getClaim(claim);

        if (value instanceof List<?> list) {
            Set<String> result = new HashSet<>();

            for (Object item : list) {
                if (item != null) {
                    result.add(item.toString());
                }
            }

            return result;
        }

        return Set.of();
    }
}
