# Taller de Rendimiento

Este repositorio contiene dos implementaciones del taller de rendimiento para SW2:

- **TallerRendimiento**: versión síncrona con Spring Boot + JPA.
- **tallerrendimientowebflux**: versión reactiva con Spring Boot WebFlux + R2DBC.

## Estructura del repositorio

- `TallerRendimiento/`: proyecto Java síncrono.
- `tallerrendimientowebflux/`: proyecto Java reactivo.
- `Tablas_base_datos_y_Indices.sql`: script de base de datos e índices.

## Requisitos

- Java 17
- Maven 3.9+

## Ejecución de pruebas

```bash
cd TallerRendimiento
mvn test

cd ../tallerrendimientowebflux
mvn test
```
