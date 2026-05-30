package py.com.pysistemas.commonweb.pagination;

import java.util.List;

/*
 * @author josec
 * @project comogastoapp
 */
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {

    public static <T> PageResponse<T> of(
            List<T> content,
            int page,
            int size,
            long totalElements
    ) {
        int totalPages = size == 0
                ? 0
                : (int) Math.ceil((double) totalElements / (double) size);

        return new PageResponse<>(
                content,
                page,
                size,
                totalElements,
                totalPages,
                page == 0,
                page + 1 >= totalPages
        );
    }
}
