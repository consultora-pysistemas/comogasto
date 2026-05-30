# common-security

## Objetivo

Centralizar autenticacion y autorizacion para servicios backend.

## Incluye

- `spring-boot-starter-security`
- `spring-security-oauth2-resource-server`
- `spring-security-oauth2-jose`
- `spring-boot-starter-aop`

## Dependencias internas

- `common-web`

## Uso

```xml
<dependency>
  <groupId>py.com.pysistemas</groupId>
  <artifactId>common-security</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Ejemplo de integracion

```java
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/actuator/health").permitAll()
                    .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt())
            .build();
}
```

## Build

```powershell
mvn -pl backend/commons/common-security -am clean install
```

