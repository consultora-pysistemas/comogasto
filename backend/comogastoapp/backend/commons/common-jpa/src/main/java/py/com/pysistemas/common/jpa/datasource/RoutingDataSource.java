package py.com.pysistemas.common.jpa.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/*
 * @author josec
 * @project comogastoapp
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.get();
    }
}
