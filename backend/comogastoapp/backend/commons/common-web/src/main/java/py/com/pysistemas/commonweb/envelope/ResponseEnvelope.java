package py.com.pysistemas.commonweb.envelope;

import java.time.Instant;

/*
 * @author josec
 * @project comogastoapp
 */
public record ResponseEnvelope<T>(
        boolean success,
        String message,
        T data,
        String correlationId,
        Instant timestamp
) {

    public static <T> ResponseEnvelope<T> success(
            String message,
            T data,
            String correlationId
    ) {
        return new ResponseEnvelope<>(
                true,
                message,
                data,
                correlationId,
                Instant.now()
        );
    }

    public static <T> ResponseEnvelope<T> success(
            T data,
            String correlationId
    ) {
        return success("Operation completed successfully", data, correlationId);
    }

    public static <T> ResponseEnvelope<T> empty(
            String message,
            String correlationId
    ) {
        return new ResponseEnvelope<>(
                true,
                message,
                null,
                correlationId,
                Instant.now()
        );
    }

    public static <T> ResponseEnvelope<T> failure(
            String message,
            T data,
            String correlationId
    ) {
        return new ResponseEnvelope<>(
                false,
                message,
                data,
                correlationId,
                Instant.now()
        );
    }
}
