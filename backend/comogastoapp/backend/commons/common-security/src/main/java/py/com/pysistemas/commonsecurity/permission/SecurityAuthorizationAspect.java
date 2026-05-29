package py.com.pysistemas.commonsecurity.permission;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import py.com.pysistemas.commonsecurity.annotation.*;
import py.com.pysistemas.commonsecurity.context.TenantContext;
import py.com.pysistemas.commonsecurity.exception.AccessDeniedBusinessException;
import py.com.pysistemas.commonsecurity.exception.TenantRequiredException;

/*
 * @author josec
 * @project comogastoapp
 */
@Aspect
public class SecurityAuthorizationAspect {
    private final PermissionEvaluatorService permissionEvaluatorService;

    public SecurityAuthorizationAspect(
            PermissionEvaluatorService permissionEvaluatorService
    ) {
        this.permissionEvaluatorService = permissionEvaluatorService;
    }

    @Before("@annotation(requirePermission)")
    public void validatePermission(
            JoinPoint joinPoint,
            RequirePermission requirePermission
    ) {
        if (!permissionEvaluatorService.hasPermission(requirePermission.value())) {
            throw new AccessDeniedBusinessException(
                    "Permission required: " + requirePermission.value()
            );
        }
    }

    @Before("@annotation(requireRole)")
    public void validateRole(
            JoinPoint joinPoint,
            RequireRole requireRole
    ) {
        if (!permissionEvaluatorService.hasRole(requireRole.value())) {
            throw new AccessDeniedBusinessException(
                    "Role required: " + requireRole.value()
            );
        }
    }

    @Before("@annotation(requireTenant)")
    public void validateTenant(
            JoinPoint joinPoint,
            RequireTenant requireTenant
    ) {
        if (!TenantContext.hasTenant()) {
            throw new TenantRequiredException();
        }
    }

    @Before("@annotation(requireCompany)")
    public void validateCompany(
            JoinPoint joinPoint,
            RequireCompany requireCompany
    ) {
        if (TenantContext.getCompanyId() == null) {
            throw new AccessDeniedBusinessException("Company context is required");
        }
    }

    @Before("@annotation(requireBranch)")
    public void validateAdministration(
            JoinPoint joinPoint,
            RequireBranch requireBranch
    ) {
        if (TenantContext.getBranchId() == null) {
            throw new AccessDeniedBusinessException("Branch context is required");
        }
    }
}
