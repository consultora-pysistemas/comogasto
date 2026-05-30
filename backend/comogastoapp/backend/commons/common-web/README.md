# common-web

## Objetivo

Modulo base para capacidades web compartidas (controladores, validaciones y utilidades HTTP).

## Incluye

- `spring-boot-starter-web`
- `spring-boot-starter-validation`
- `spring-web`

## Dependencias internas

- No depende de otros modulos `common-*`.

## Uso

```xml
<dependency>
  <groupId>py.com.pysistemas</groupId>
  <artifactId>common-web</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Ejemplo de integracion

```java
@RestController
@RequestMapping("/healthz")
public class HealthController {

    @GetMapping
    public Map<String, String> ok() {
        return Map.of("status", "ok");
    }
}
```

## Build

```powershell
mvn -pl backend/commons/common-web -am clean install
```

