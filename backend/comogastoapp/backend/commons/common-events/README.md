# common-events

## Objetivo

Proveer capacidades compartidas para publicacion y consumo de eventos.

## Incluye

- `spring-kafka`
- `jackson-databind`

## Dependencias internas

- `common-web`
- `common-security`

## Uso

```xml
<dependency>
  <groupId>py.com.pysistemas</groupId>
  <artifactId>common-events</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Ejemplo de integracion

```java
@Service
public class EventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String topic, String payload) {
        kafkaTemplate.send(topic, payload);
    }
}
```

## Build

```powershell
mvn -pl backend/commons/common-events -am clean install
```

