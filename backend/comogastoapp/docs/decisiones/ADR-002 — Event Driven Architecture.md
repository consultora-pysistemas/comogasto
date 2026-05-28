# ADR-002: Usar arquitectura orientada a eventos

## Estado

Aceptado.

## Contexto

COMOGASTOAPP Platform debe permitir auditoría, tracing, desacoplamiento y extensibilidad para múltiples proyectos.

## Decisión

Se utilizará Kafka KRaft como event bus principal.

## Consecuencias

- Los servicios publicarán eventos versionados.
- Los consumidores serán idempotentes.
- Se aplicará Outbox Pattern para evitar pérdida de eventos.
- Se aplicará Inbox Pattern para evitar reprocesamiento.