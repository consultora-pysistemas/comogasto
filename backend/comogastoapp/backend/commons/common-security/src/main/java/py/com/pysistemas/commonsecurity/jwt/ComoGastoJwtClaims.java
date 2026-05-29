package py.com.pysistemas.commonsecurity.jwt;

/*
 * @author josec
 * @project comogastoapp
 */
public final class ComoGastoJwtClaims {
    private ComoGastoJwtClaims() {
    }

    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String TENANT_ID = "tenantId";
    public static final String COMPANY_ID = "companyId";
    public static final String ADMINISTRATION_ID = "administrationId";
    public static final String ROLES = "roles";
    public static final String PERMISSIONS = "permissions";
    public static final String TOKEN_TYPE = "tokenType";
    public static final String JTI = "jti";

}
