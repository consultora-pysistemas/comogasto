package py.com.pysistemas.commonsecurity.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import py.com.pysistemas.commonsecurity.context.TenantContext;
import py.com.pysistemas.commonsecurity.context.UserContext;
import py.com.pysistemas.commonsecurity.jwt.AuthenticatedUser;
import py.com.pysistemas.commonsecurity.jwt.JwtAuthenticationExtractor;

/*
 * @author josec
 * @project comogastoapp
 */
public class SecurityContextPropagationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            Authentication authentication =
                    org.springframework.security.core.context.SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
                AuthenticatedUser user = JwtAuthenticationExtractor.extract(jwt);

                UserContext.setUserId(user.userId());
                UserContext.setUsername(user.username());
                UserContext.setEmail(user.email());
                UserContext.setRoles(user.roles());
                UserContext.setPermissions(user.permissions());

                TenantContext.setTenantId(user.tenantId());
                TenantContext.setCompanyId(user.companyId());
                TenantContext.setBranchId(user.administrationId());
            }

            filterChain.doFilter(request, response);
        } finally {
            UserContext.clear();
            TenantContext.clear();
        }
    }
}
