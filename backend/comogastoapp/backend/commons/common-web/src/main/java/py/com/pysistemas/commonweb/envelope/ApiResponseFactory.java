package py.com.pysistemas.commonweb.envelope;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import py.com.pysistemas.commonweb.request.CorrelationIdContext;

/*
 * @author josec
 * @project comogastoapp
 */
public final class ApiResponseFactory {
    private ApiResponseFactory() {
    }

    public static <T> ResponseEntity<ResponseEnvelope<T>> ok(T data) {
        return ResponseEntity.ok(
                ResponseEnvelope.success(data, CorrelationIdContext.get())
        );
    }

    public static <T> ResponseEntity<ResponseEnvelope<T>> ok(String message, T data) {
        return ResponseEntity.ok(
                ResponseEnvelope.success(message, data, CorrelationIdContext.get())
        );
    }

    public static <T> ResponseEntity<ResponseEnvelope<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseEnvelope.success(message, data, CorrelationIdContext.get()));
    }

    public static ResponseEntity<ResponseEnvelope<Void>> noContent(String message) {
        return ResponseEntity.ok(
                ResponseEnvelope.empty(message, CorrelationIdContext.get())
        );
    }
}
