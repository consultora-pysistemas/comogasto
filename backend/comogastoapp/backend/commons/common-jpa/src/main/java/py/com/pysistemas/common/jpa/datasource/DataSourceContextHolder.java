package py.com.pysistemas.common.jpa.datasource;

/*
 * @author josec
 * @project comogastoapp
 */
public final class DataSourceContextHolder {
    private static final ThreadLocal<DataSourceType> CONTEXT = new ThreadLocal<>();

    private DataSourceContextHolder() {
    }

    public static void useCommand() {
        CONTEXT.set(DataSourceType.COMMAND);
    }

    public static void useQuery() {
        CONTEXT.set(DataSourceType.QUERY);
    }

    public static DataSourceType get() {
        return CONTEXT.get() == null ? DataSourceType.COMMAND : CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
