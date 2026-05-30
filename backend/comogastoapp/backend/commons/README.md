# commons

Guia de modulos compartidos `common-*` para el backend.

## Objetivo

- Acelerar onboarding tecnico.
- Estandarizar el uso de librerias comunes.
- Definir orden de adopcion y criterios de decision.

## Mapa rapido

| Modulo                 | Cuando usarlo                                                            | Evitar usarlo si...                                                       |
|------------------------|--------------------------------------------------------------------------|---------------------------------------------------------------------------|
| `common-web`           | Necesitas controladores REST, validaciones y utilidades HTTP compartidas | Tu modulo no expone endpoints ni maneja contrato web                      |
| `common-security`      | Necesitas autenticacion/autorizacion JWT/OAuth2 y politicas comunes      | El flujo no requiere seguridad (p.ej. utilitario offline)                 |
| `common-events`        | Publicas/consumes eventos en Kafka                                       | Tu flujo es estrictamente sincrono y local                                |
| `common-jpa`           | Requieres persistencia relacional con JPA/PostgreSQL                     | Tu modulo no persiste datos o usa almacenamiento no relacional            |
| `common-outbox`        | Debes publicar eventos de forma confiable tras transaccion DB            | No hay eventos salientes o no hay requisito de consistencia transaccional |
| `common-inbox`         | Debes consumir eventos con idempotencia y deduplicacion                  | No hay consumo asincrono de eventos                                       |
| `common-audit`         | Necesitas trazabilidad de acciones de negocio/seguridad                  | No hay requerimiento de auditoria en ese contexto                         |
| `common-tracing`       | Necesitas correlacion distribuida (trace/span)                           | El servicio es aislado y sin observabilidad distribuida                   |
| `common-observability` | Expones metricas, trazas OTEL y logs estructurados                       | El modulo no se despliega como servicio monitoreado                       |

## Orden recomendado de adopcion

1. `common-web`
2. `common-security`
3. `common-jpa`
4. `common-events`
5. `common-outbox` y/o `common-inbox`
6. `common-tracing`
7. `common-observability`
8. `common-audit`

## Reglas practicas

- Inicia con el menor set de modulos posible y agrega por necesidad real.
- Para flujos event-driven con DB, prioriza `common-outbox` para consistencia.
- Para consumidores idempotentes, usa `common-inbox` y clave de deduplicacion.
- En servicios productivos, combina `common-tracing` + `common-observability`.

## Anti-patrones a evitar

- Duplicar configuraciones de seguridad ya resueltas en `common-security`.
- Publicar eventos directamente desde logica de dominio sin outbox cuando hay transaccion DB.
- Consumir eventos sin estrategia de idempotencia.
- Mezclar logging ad-hoc sin trazabilidad/correlacion en servicios distribuidos.
- Usar todos los `common-*` por defecto aunque el modulo no los necesite.

## Referencias

- `backend/commons/common-web/README.md`
- `backend/commons/common-security/README.md`
- `backend/commons/common-events/README.md`
- `backend/commons/common-jpa/README.md`
- `backend/commons/common-outbox/README.md`
- `backend/commons/common-inbox/README.md`
- `backend/commons/common-audit/README.md`
- `backend/commons/common-tracing/README.md`
- `backend/commons/common-observability/README.md`

