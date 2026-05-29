package py.com.pysistemas.common.audit.masking;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import py.com.pysistemas.common.audit.annotation.SensitiveField;

/*
 * @author josec
 * @project comogastoapp
 */
public class SensitiveDataMasker {
    private static final String MASK = "******";

    public Object mask(Object value) {
        if (value == null) {
            return null;
        }

        if (isSimple(value)) {
            return value;
        }

        Map<String, Object> masked = new HashMap<>();

        for (Field field : value.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                Object fieldValue = field.get(value);

                if (field.isAnnotationPresent(SensitiveField.class)) {
                    masked.put(field.getName(), MASK);
                } else {
                    masked.put(field.getName(), fieldValue);
                }
            } catch (IllegalAccessException ignored) {
                masked.put(field.getName(), null);
            }
        }

        return masked;
    }

    private boolean isSimple(Object value) {
        return value instanceof String
                || value instanceof Number
                || value instanceof Boolean
                || value.getClass().isEnum();
    }
}

