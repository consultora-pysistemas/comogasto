package py.com.pysistemas.commonweb.error;

/*
 * @author josec
 * @project comogastoapp
 */
public record FieldErrorItem(
        String field,
        String message,
        Object rejectedValue
) {
}
