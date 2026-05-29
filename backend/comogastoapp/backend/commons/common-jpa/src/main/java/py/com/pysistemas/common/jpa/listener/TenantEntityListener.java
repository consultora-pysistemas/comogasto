package py.com.pysistemas.common.jpa.listener;

import jakarta.persistence.PrePersist;
import py.com.pysistemas.common.jpa.TenantEntity;
import py.com.pysistemas.commonsecurity.context.TenantContext;

/*
 * @author josec
 * @project comogastoapp
 */
public class TenantEntityListener {
    @PrePersist
    public void beforePersist(TenantEntity entity) {
        if (entity.getTenantId() == null) {
            entity.setTenantId(TenantContext.getTenantId());
        }

        if (entity.getCompanyId() == null) {
            entity.setCompanyId(TenantContext.getCompanyId());
        }

        if (entity.getBranchId() == null) {
            entity.setBranchId(TenantContext.getBranchId());
        }
    }
}
