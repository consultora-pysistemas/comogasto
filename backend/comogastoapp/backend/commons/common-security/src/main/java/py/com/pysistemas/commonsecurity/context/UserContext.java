package py.com.pysistemas.commonsecurity.context;

import java.util.Set;
import java.util.UUID;

/*
 * @author josec
 * @project comogastoapp
 */
public final class UserContext {
    private static final ThreadLocal<UUID> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> USERNAME = new ThreadLocal<>();
    private static final ThreadLocal<String> EMAIL = new ThreadLocal<>();
    private static final ThreadLocal<Set<String>> ROLES = new ThreadLocal<>();
    private static final ThreadLocal<Set<String>> PERMISSIONS = new ThreadLocal<>();

    private UserContext() {
    }

    public static void setUserId(UUID userId) {
        USER_ID.set(userId);
    }

    public static UUID getUserId() {
        return USER_ID.get();
    }

    public static void setUsername(String username) {
        USERNAME.set(username);
    }

    public static String getUsername() {
        return USERNAME.get();
    }

    public static void setEmail(String email) {
        EMAIL.set(email);
    }

    public static String getEmail() {
        return EMAIL.get();
    }

    public static void setRoles(Set<String> roles) {
        ROLES.set(roles);
    }

    public static Set<String> getRoles() {
        return ROLES.get() == null ? Set.of() : ROLES.get();
    }

    public static void setPermissions(Set<String> permissions) {
        PERMISSIONS.set(permissions);
    }

    public static Set<String> getPermissions() {
        return PERMISSIONS.get() == null ? Set.of() : PERMISSIONS.get();
    }

    public static boolean hasRole(String role) {
        return getRoles().contains(role);
    }

    public static boolean hasPermission(String permission) {
        return getPermissions().contains(permission);
    }

    public static void clear() {
        USER_ID.remove();
        USERNAME.remove();
        EMAIL.remove();
        ROLES.remove();
        PERMISSIONS.remove();
    }
}
