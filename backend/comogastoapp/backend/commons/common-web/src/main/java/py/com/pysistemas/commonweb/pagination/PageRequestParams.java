package py.com.pysistemas.commonweb.pagination;

/*
 * @author josec
 * @project comogastoapp
 */
public record PageRequestParams(
        int page,
        int size,
        String sortBy,
        String sortDirection
) {

    public static PageRequestParams defaults() {
        return new PageRequestParams(0, 20, "createdAt", "DESC");
    }

    public int safePage() {
        return Math.max(page, 0);
    }

    public int safeSize() {
        if (size <= 0) {
            return 20;
        }

        return Math.min(size, 200);
    }

    public String safeSortDirection() {
        if ("ASC".equalsIgnoreCase(sortDirection)) {
            return "ASC";
        }

        return "DESC";
    }
}
