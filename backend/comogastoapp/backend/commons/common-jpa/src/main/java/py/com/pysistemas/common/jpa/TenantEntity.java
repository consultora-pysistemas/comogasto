package py.com.pysistemas.common.jpa;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import py.com.pysistemas.common.jpa.listener.TenantEntityListener;

/*
 * @author josec
 * @project comogastoapp
 */
@MappedSuperclass
@EntityListeners(TenantEntityListener.class)
public abstract class TenantEntity extends BaseEntity {
    @Column(name = "tenant_id", nullable = false)
    protected UUID tenantId;

    @Column(name = "company_id")
    protected UUID companyId;

    @Column(name = "branch_id")
    private UUID branchId;

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }


    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }
}
