# ADR-004 — Percona PMM para Monitoreo de PostgreSQL

**Estado**: Aceptado  
**Fecha**: 2026-05-28  
**Autor**: Sistema de Modernización  

## Contexto

La plataforma comogastoapp necesita monitoreo centralizado y observabilidad en tiempo real para:
- PostgreSQL (primario y réplica)
- Redis
- Kafka
- Otros servicios críticos

Actualmente no existe solución de monitoreo integrada.

## Decisión

Se implementa **Percona Monitoring and Management (PMM)** como solución de monitoreo centralizado.

### Justificación

1. **PostgreSQL nativamente soportado**: PMM está optimizado para monitoreo de PostgreSQL
2. **Open Source**: Código abierto, sin costos de licencia
3. **Docker-ready**: Fácil deployment en nuestro ambiente con Docker Compose
4. **Multitenancy**: Puede monitorear múltiples instancias/ambientes
5. **Observabilidad completa**: Métricas, logs, análisis de queries
6. **Replicación**: Monitoreo específico del lag y estado de sincronización

## Alternativas consideradas

| Solución | Ventajas | Desventajas |
|----------|----------|------------|
| **Percona PMM** (⭐ seleccionado) | Open source, PostgreSQL optimizado, fácil deploy | Requiere máquina de monitoreo dedicada |
| Prometheus + Grafana | Muy flexible, comunidad grande | Requiere más configuración manual |
| Datadog | Muy completo, SaaS | Costo por agente/métrica |
| AWS RDS Monitoring | Integrado si usamos RDS | No aplica, tenemos PostgreSQL on-prem |
| Zabbix | Potente, escalable | Complejidad, requiere aprendizaje |

## Impacto

### Positivo
- ✅ Visibilidad de métricas en tiempo real
- ✅ Análisis histórico de performance
- ✅ Alertas tempranas de problemas
- ✅ Debugging de issues de replicación
- ✅ Query analytics para optimización

### Negativo
- ⚠️ Requerimiento adicional de recursos (memoria, CPU)
- ⚠️ Complejidad añadida a infraestructura
- ⚠️ Curva de aprendizaje para el equipo

### Recursos requeridos

**Mínimo:**
- CPU: 2 cores
- RAM: 2 GB
- Storage: 10 GB (retención de 30 días por defecto)

## Implementación

### Componentes
1. **PMM Server**: Servidor central de recolección y visualización
   - Puerto: 8443 (configurable vía `PMM_SERVER_PORT`)
   - Volumen persistente: `pmm_server_data:/srv`
   - Health check: Endpoint `/graph/`

2. **PMM Agents**: Instalados en cada nodo a monitorear
   - PostgreSQL primario
   - PostgreSQL réplica
   - (Futuro) Redis, Kafka

### Configuración

**Docker Compose:**
```yaml
pmm-server:
  image: percona/pmm-server:latest
  ports:
    - "${PMM_SERVER_PORT}:80"
  volumes:
    - pmm_server_data:/srv
  environment:
    DISABLE_UPDATES: "true"
    METRICS_RETENTION: "2592000"  # 30 días
```

**Acceso inicial:**
- URL: http://localhost:8443
- Usuario: admin
- Contraseña: admin

### Monitoreo de PostgreSQL

1. **Replicación**: 
   - Lag bytes
   - Socat LSN lag
   - WAL age

2. **Performance**:
   - Queries por segundo
   - Conexiones activas
   - Cache hit ratio

3. **Almacenamiento**:
   - Tamaño de tabla
   - Tamaño de índice
   - Dead tuples

## Notas importantes

- [ ] En producción: cambiar credenciales por defecto
- [ ] En producción: configurar reverse proxy (nginx/traefik) con SSL
- [ ] Mantener SLA de uptime del servidor PMM
- [ ] Implementar backups de datos de PMM
- [ ] Crear alertas para eventos críticos

## Referencias

- [Documentación PMM](https://docs.percona.com/percona-monitoring-and-management/)
- [PostgreSQL Monitoring Best Practices](https://www.postgresql.org/docs/current/monitoring.html)
- [Percona Blog - PMM for PostgreSQL](https://www.percona.com/blog/)

