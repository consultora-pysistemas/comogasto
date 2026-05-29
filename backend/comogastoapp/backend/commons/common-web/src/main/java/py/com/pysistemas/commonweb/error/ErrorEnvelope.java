package py.com.pysistemas.commonweb.error;

import java.time.Instant;
import java.util.List;

/*
 * @author josec
 * @project comogastoapp
 */
public record ErrorEnvelope(
        boolean success,
        String code,
        String message,
        int status,
        String path,
        String correlationId,
        List<FieldErrorItem> errors,
        Instant timestamp
) {

    public static ErrorEnvelope of(
            String code,
            String message,
            int status,
            String path,
            String correlationId
    ) {
        return new ErrorEnvelope(
                false,
                code,
                message,
                status,
                path,
                correlationId,
                List.of(),
                Instant.now()
        );
    }

    public static ErrorEnvelope of(
            String code,
            String message,
            int status,
            String path,
            String correlationId,
            List<FieldErrorItem> errors
    ) {
        return new ErrorEnvelope(
                false,
                code,
                message,
                status,
                path,
                correlationId,
                errors,
                Instant.now()
        );
    }
}