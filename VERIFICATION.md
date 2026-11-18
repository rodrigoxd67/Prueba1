# Verificación funcional

Verificación realizada el 17 de noviembre de 2026 con JDK 26.0.1, Spring Boot 4.1.2 y H2 en disco. No se añadió una suite de tests.

## Compilación y arranque

- `mvnw.cmd -B -Dmaven.test.skip=true package`: **BUILD SUCCESS**.
- JAR ejecutable iniciado correctamente en el puerto 18081 para la verificación.
- Base aislada: `target/verification-db.mv.db`. La base predeterminada de la aplicación queda sin datos de estas comprobaciones.
- El proceso de verificación se detuvo al finalizar.

## Solicitudes HTTP reales

| Escenario | Resultado observado |
| --- | --- |
| Registro válido | 201; id entero positivo y UUID no vacío |
| Campos de respuesta | Solo los diez atributos requeridos; sin código original ni hash |
| Repetición del mismo registro | 409 |
| Duplicado con mayúsculas y espacios redundantes | 409 |
| Misma identidad con otra ciudad | 409 |
| Mismo encargado y razón social en otro país | 201 |
| Otro encargado | 201 |
| Provincia omitida o nula | 201 |
| Correo inválido | 400 |
| Teléfono sin +, con cero inicial, con espacios o demasiado largo | 400 |
| Código corto, largo o compuesto solo de espacios | 400 |
| Omisión de cada campo obligatorio | 400 |
| Provincia demasiado larga | 400 |
| JSON mal formado | 400 |
| Intento de enviar id | 400 |
| GET al endpoint de talleres | 405 |
| Content-Type text/plain | 415 |
| Accept-Language es y en | Mensajes traducidos y cabecera Content-Language correspondiente |
| Accept-Language es-PE con campos omitidos | Mensajes por campo en español |
| Cinco registros simultáneos de la misma identidad | Un 201 y cuatro 409 |
| Repetición de un registro después de reiniciar el JAR | 409; datos conservados |

## Inspección directa de persistencia

Tras detener la aplicación se abrió la base con JDBC y se verificaron las seis filas aceptadas:

- Identificadores de persistencia positivos e identificadores de negocio UUID no vacíos.
- Hashes de 60 caracteres verificables con `BCryptPasswordEncoder.matches` contra el código enviado.
- Fechas `created_at` y `updated_at` no nulas.
- Conservación de registros después de detener y reiniciar el proceso.

El reporte de solicitudes de esta ejecución queda en `target/http-verification.json`, ignorado por Git junto con los demás artefactos temporales.

## Límites de la verificación

El perfil MySQL está configurado, pero no se verificó contra un servidor MySQL. Los formatos utilizados para correo y teléfono se documentan en README porque los anexos mencionados no fueron adjuntados. No se incorporan CORS ni autenticación.
