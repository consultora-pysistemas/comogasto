# common-outbox

## Objetivo

Implementar el patron Outbox para publicacion confiable de eventos.

## Incluye

- Integracion con capa de persistencia comun.
- Integracion con capa de eventos comun.

## Dependencias internas

- `common-jpa`
- `common-events`

## Uso

```xml
<dependency>
  <groupId>py.com.pysistemas</groupId>
  <artifactId>common-outbox</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Ejemplo de integracion

```java
@Entity
public class OutboxEvent {
    @Id
    private UUID id;
    private String topic;
    private String payload;
    private boolean published;
}

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findTop100ByPublishedFalseOrderByIdAsc();
}
```

## Build

```powershell
mvn -pl backend/commons/common-outbox -am clean install
```

