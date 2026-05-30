# comogastoapp

    Monorepo Maven del backend de Comogasto. El proyecto esta organizado en modulos compartidos `common-*` dentro de `backend/commons`.

## Objetivo

- Centralizar componentes reutilizables para servicios backend.
- Separar capacidades transversales por modulo (`web`, `security`, `events`, `jpa`, etc.).
- Simplificar versionado y build mediante agregadores Maven.

## Estructura del repositorio

- `pom.xml`: agregador raiz del monorepo.
- `backend/pom.xml`: agregador backend y `dependencyManagement` (Spring Boot / Spring Cloud).
- `backend/commons/common-*`: librerias compartidas.
- `docker-compose.yml`: stack local (PostgreSQL, Kafka, Redis, PMM, Portainer).

## Modulos common-*

Guia comparativa: `backend/commons/README.md`

| Modulo | Proposito | Path |
|---|---|---|
| `common-web` | Base web (MVC + validacion) | `backend/commons/common-web` |
| `common-security` | Seguridad y OAuth2 Resource Server | `backend/commons/common-security` |
| `common-events` | Eventos y mensajeria con Kafka | `backend/commons/common-events` |
| `common-jpa` | Persistencia JPA y PostgreSQL | `backend/commons/common-jpa` |
| `common-outbox` | Patron Outbox | `backend/commons/common-outbox` |
| `common-inbox` | Patron Inbox | `backend/commons/common-inbox` |
| `common-audit` | Auditoria transversal | `backend/commons/common-audit` |
| `common-tracing` | Trazabilidad transversal | `backend/commons/common-tracing` |
| `common-observability` | Metricas, trazas y logs estructurados | `backend/commons/common-observability` |

## Mapa de dependencias entre modulos

| Modulo | Depende de `common-*` |
|---|---|
| `common-web` | - |
| `common-security` | `common-web` |
| `common-events` | `common-web`, `common-security` |
| `common-jpa` | `common-web`, `common-security` |
| `common-outbox` | `common-jpa`, `common-events` |
| `common-inbox` | `common-jpa`, `common-events` |
| `common-audit` | `common-events`, `common-security`, `common-web` |
| `common-tracing` | `common-events`, `common-security`, `common-web` |
| `common-observability` | `common-web`, `common-tracing` |

Cada modulo `common-*` incluye una seccion **Ejemplo de integracion** en su `README.md` para acelerar onboarding.

## Requisitos

- Java 21
- Maven 3.9+

## Comandos de build

Build completo desde raiz:

```powershell
mvn clean install
```

Build solo del agregador backend:

```powershell
mvn -pl backend -am clean install
```

Build de un modulo puntual (ejemplo `common-web`):

```powershell
mvn -pl backend/commons/common-web -am clean install
```

## Convenciones recomendadas

- Mantener una plantilla uniforme de README por modulo.
- Unificar versiones internas (`0.0.1-SNAPSHOT` vs `1.0-SNAPSHOT`) para evitar inconsistencias.

