# common-observability

## Objetivo

Centralizar capacidades de observabilidad: metricas, trazas y logs estructurados.

## Incluye

- `spring-boot-starter-actuator`
- `micrometer-registry-prometheus`
- `micrometer-tracing-bridge-otel`
- `opentelemetry-exporter-otlp`
- `logstash-logback-encoder`

## Dependencias internas

- `common-web`
- `common-tracing`

## Uso

```xml
<dependency>
  <groupId>py.com.pysistemas</groupId>
  <artifactId>common-observability</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Ejemplo de integracion

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
  tracing:
    sampling:
      probability: 1.0
```

## Build

```powershell
mvn -pl backend/commons/common-observability -am clean install
```

