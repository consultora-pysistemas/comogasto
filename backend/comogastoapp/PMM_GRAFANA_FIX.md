# 🔧 PMM Grafana No Abre - Solución Alternativa

## Problema
PMM Grafana en `http://localhost:8443` no responde o tarda demasiado en cargar.

## Solución Rápida: Acceder a Grafana Directamente

### 1. Obtener Puerto de Grafana Interno
```bash
docker exec comogastoapp-pmm-server bash -c "grep -i 'listen' /etc/grafana/provisioning/grafana.ini | head -5"
```

Por defecto, Grafana corre en **puerto 3000** dentro del contenedor.

### 2. Mapear Puerto de Grafana en docker-compose.yml

Agrega un nuevo servicio o expón el puerto directamente:

```bash
# Opción A: Verificar si ya está expuesto
docker exec comogastoapp-pmm-server bash -c "ss -tlnp 2>/dev/null | grep grafana" 2>&1
```

### 3. Crear Acceso Directo a PMMGrafana

Edita `docker-compose.yml` y agrega un mapeo de puerto para Grafana:

```yaml
pmm-server:
  ...
  ports:
    - "${PMM_SERVER_PORT}:80"           # Nginx proxy (actual)
    - "3000:3000"                       # Grafana directo (alternativo)
  ...
```

### 4. Acceder a Grafana Directamente

Luego de reiniciar:
```
http://localhost:3000
admin / admin
```

---

## Solución Permanente: Flexibilizar Health Check

La imagen de PMM incluye un healthcheck estricto que marca "unhealthy" mientras Grafana se inicializa.

### Actualizar docker-compose.yml

Aumenta los tiempos de espera:

```yaml
pmm-server:
  ...
  healthcheck:
    test: ["CMD", "curl", "-f", "http://localhost/graph/"]
    interval: 30s           # (era 15s)
    timeout: 10s            # (era 5s)
    retries: 5              # (era 3)
    start_period: 180s      # (era 60s) - 3 minutos de gracia
```

Luego reinicia:
```bash
docker compose restart pmm-server
```

---

## Debugging: Ver Qué Está Corriendo

```bash
# Ver procesos en PMM
docker exec comogastoapp-pmm-server supervisorctl status | grep -E "grafana|nginx|pmm-managed"

# Ver logs de Grafana
docker exec comogastoapp-pmm-server tail -50 /var/log/grafana/grafana.log

# Probar conectividad desde dentro
docker exec comogastoapp-pmm-server bash -c "curl -s http://localhost:3000/ | head -10"
```

---

## Pasos de Resolución Recomendados

### Paso 1: Acceso Rápido Ahora
```bash
# Acceder directamente (mapeo temporal de puerto)
docker exec -it comogastoapp-pmm-server bash
# Dentro: curl http://localhost:3000
```

### Paso 2: Solución Permanente
Modifica `docker-compose.yml`:
1. Adiciona línea `- "3000:3000"` bajo `pmm-server` ports
2. Aumenta `start_period` a 180s en healthcheck
3. Ejecuta: `docker compose restart pmm-server`

### Paso 3: Acceso Final
```
http://localhost:3000
usuario: admin
password: admin
```

---

## Alternativa: Usar Portainer para Acceder

Si nada funciona, usa Portainer (que ya está corriendo):
```
http://localhost:9000
```
- Ve a **Containers** → `comogastoapp-pmm-server`
- Abre **Exec Console**
- Ejecuta: `curl http://localhost:3000`
- O expón el puerto desde ahí

---

## Checklist de Verificación

- [ ] PMM Server está corriendo: `docker ps | grep pmm`
- [ ] Puerto 8443 está escuchando: `netstat -an | find "8443"`
- [ ] Nginx está iniciado: `docker exec comogastoapp-pmm-server supervisorctl status nginx`
- [ ] Grafana está iniciado: `docker exec comogastoapp-pmm-server supervisorctl status grafana`
- [ ] PMM Agent está corriendo: `docker exec comogastoapp-pmm-server supervisorctl status pmm-agent`

---

## Si Sigue Sin Funcionar

Reconstruye PMM desde cero:
```bash
docker compose down pmm-server
docker volume rm comogastoapp_pmm_server_data
docker compose up -d pmm-server
# Espera 3 minutos
```

Luego accede a `http://localhost:8443` o `http://localhost:3000`.

