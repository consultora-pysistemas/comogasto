# ADR-003: CQRS con PostgreSQL primary/replica

## Estado

Aceptado.

## Contexto

La plataforma debe separar operaciones de escritura y lectura para escalar consultas.

## Decisión

Se usará PostgreSQL primary para comandos y PostgreSQL replica para queries.

## Consecuencias

- Los servicios tendrán command datasource y query datasource.
- Las escrituras irán al primary.
- Las lecturas pesadas irán a la réplica.
- Se debe considerar eventual consistency.