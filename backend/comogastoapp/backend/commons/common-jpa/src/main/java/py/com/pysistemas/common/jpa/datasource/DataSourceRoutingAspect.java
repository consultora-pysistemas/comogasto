package py.com.pysistemas.common.jpa.datasource;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/*
 * @author josec
 * @project comogastoapp
 */
@Aspect
public class DataSourceRoutingAspect {
    @Before("@annotation(py.com.pysistemas.common.jpa.datasource.UseCommandDataSource)")
    public void useCommandDataSource() {
        DataSourceContextHolder.useCommand();
    }

    @Before("@annotation(py.com.pysistemas.common.jpa.datasource.UseQueryDataSource)")
    public void useQueryDataSource() {
        DataSourceContextHolder.useQuery();
    }

    @After("@annotation(py.com.pysistemas.common.jpa.datasource.UseCommandDataSource) || " +
            "@annotation(py.com.pysistemas.common.jpa.datasource.UseQueryDataSource)")
    public void clear() {
        DataSourceContextHolder.clear();
    }
}
