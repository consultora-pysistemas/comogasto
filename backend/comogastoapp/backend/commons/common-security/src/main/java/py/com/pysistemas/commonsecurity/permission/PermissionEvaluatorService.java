package py.com.pysistemas.commonsecurity.permission;

import py.com.pysistemas.commonsecurity.context.UserContext;

/*
 * @author josec
 * @project comogastoapp
 */
public class PermissionEvaluatorService {
    public boolean hasPermission(String permission) {
        return UserContext.hasPermission(permission);
    }

    public boolean hasRole(String role) {
        return UserContext.hasRole(role);
    }
}
