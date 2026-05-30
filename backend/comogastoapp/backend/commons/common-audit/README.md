# common-audit

## Objetivo

Implementar auditoria transversal para operaciones de negocio y seguridad.

## Incluye

- `spring-aop`
- Integracion con modulos de eventos, seguridad y web.

## Dependencias internas

- `common-events`
- `common-security`
- `common-web`

## Uso

```xml
<dependency>
  <groupId>py.com.pysistemas</groupId>
  <artifactId>common-audit</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Ejemplo de integracion

```java
@Aspect
@Component
public class AuditAspect {

    @AfterReturning("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void auditCreate(JoinPoint joinPoint) {
        // Publicar evento o log de auditoria.
    }
}
```

## Build

```powershell
mvn -pl backend/commons/common-audit -am clean install
```

