# common-tracing

## Objetivo

Proveer trazabilidad distribuida y correlacion de contexto entre servicios.

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
  <artifactId>common-tracing</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Ejemplo de integracion

```java
@Component
public class TraceHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String traceId = request.getHeader("X-Trace-Id");
        if (traceId != null) {
            MDC.put("traceId", traceId);
        }
        chain.doFilter(request, response);
    }
}
```

## Build

```powershell
mvn -pl backend/commons/common-tracing -am clean install
```

