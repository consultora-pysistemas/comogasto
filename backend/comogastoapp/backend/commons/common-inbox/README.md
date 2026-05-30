# common-inbox

## Objetivo

Implementar el patron Inbox para consumo idempotente y confiable de eventos.

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
  <artifactId>common-inbox</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Ejemplo de integracion

```java
@Entity
public class InboxMessage {
    @Id
    private UUID id;
    private String eventId;
    private Instant processedAt;
}

public interface InboxMessageRepository extends JpaRepository<InboxMessage, UUID> {
    boolean existsByEventId(String eventId);
}
```

## Build

```powershell
mvn -pl backend/commons/common-inbox -am clean install
```

