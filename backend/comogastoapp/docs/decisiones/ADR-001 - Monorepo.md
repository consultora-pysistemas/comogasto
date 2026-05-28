# ADR-001: Usar monorepo para ComoGastoApp Platform

## Estado

Aceptado.

## Contexto

ComoGastoApp Platform contiene múltiples commons, starters, microservicios, frontend e infraestructura. La plataforma debe evolucionar de forma coordinada.

## Decisión

Se utilizará un monorepo con carpetas separadas para backend, frontend, infraestructura, scripts y documentación.

## Consecuencias

### Positivas

- Mejor trazabilidad entre servicios.
- Versionado unificado.
- Refactorización más simple.
- Reutilización directa de commons.
- Onboarding más rápido.

### Negativas

- Requiere disciplina de módulos.
- Puede crecer mucho si no se aplican reglas.