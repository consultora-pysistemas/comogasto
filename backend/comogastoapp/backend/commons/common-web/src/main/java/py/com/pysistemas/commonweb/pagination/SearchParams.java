package py.com.pysistemas.commonweb.pagination;

import java.time.LocalDate;

/*
 * @author josec
 * @project comogastoapp
 */
public record SearchParams(
        String search,
        String status,
        LocalDate fromDate,
        LocalDate toDate
) {
}
