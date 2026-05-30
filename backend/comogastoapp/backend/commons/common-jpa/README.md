# common-jpa

## Objetivo

Centralizar componentes de persistencia JPA y conectividad PostgreSQL.

## Incluye

- `spring-boot-starter-data-jpa`
- `postgresql`

## Dependencias internas

- `common-web`
- `common-security`

## Uso

```xml
<dependency>
  <groupId>py.com.pysistemas</groupId>
  <artifactId>common-jpa</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Ejemplo de integracion

```java
@Entity
public class Expense {
    @Id
    private UUID id;
    private BigDecimal amount;
}

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
}
```

## Build

```powershell
mvn -pl backend/commons/common-jpa -am clean install
```

