# Daimler Truck — Authorized Workshops

API para registrar talleres autorizados. Implementa únicamente `POST /api/v1/workshops`.

## Ejecutar

Requiere JDK 25 o posterior y acceso a Maven Central para la primera descarga. Configure el JDK del proyecto y de Maven en el IDE, o `JAVA_HOME` en la terminal.

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux/macOS: `./mvnw spring-boot:run`.

La API escucha en `http://localhost:8080/api/v1/workshops`. No hay página de inicio ni operación GET de talleres; un GET a este endpoint devuelve 405.

Por defecto utiliza H2 en disco, en `data/daimler-truck.mv.db`. Los datos sobreviven al reinicio. Hibernate crea la tabla `authorized_workshops` en el esquema predeterminado de la conexión (`PUBLIC` en H2).

Para generar el ejecutable sin ejecutar ni compilar pruebas:

```powershell
.\mvnw.cmd -Dmaven.test.skip=true package
java -jar target/daimler-truck-0.0.1-SNAPSHOT.jar
```

## Solicitud

Abra `requests/workshops.http` con el cliente HTTP del IDE, o use Postman con método POST y `Content-Type: application/json`:

```json
{
  "managerFirstName": "Rodrigo",
  "managerLastName": "Saavedra",
  "contactPhone": "+51987654321",
  "contactEmail": "rodrigo@example.com",
  "workshopLegalName": "Daimler Lima SAC",
  "city": "Lima",
  "provinceOrState": "Lima",
  "countryOrRegion": "Peru",
  "maintenanceAuthCode": "Abc12345"
}
```

`provinceOrState` es opcional; puede omitirse o enviarse como `null`. Los demás atributos son obligatorios. No se aceptan campos desconocidos, incluidos `id`, `workshopId` y `encryptedAuthCode`.

El alta devuelve **201** con `id`, `workshopId`, `managerFirstName`, `managerLastName`, `contactEmail`, `contactPhone`, `workshopLegalName`, `city`, `provinceOrState` y `countryOrRegion`. No devuelve códigos de autorización ni hashes.

## Reglas y errores

- El identificador de persistencia es `Integer` autoincremental; el identificador de negocio es un UUID aleatorio no vacío generado durante el registro.
- No puede repetirse la combinación de nombre y apellido del encargado, razón social y país/región. Se ignoran diferencias de mayúsculas, espacios redundantes y variantes Unicode equivalentes. La ciudad no forma parte de esta combinación.
- Una clave normalizada SHA-256 de esa combinación tiene una restricción única en la base, para proteger también registros simultáneos. Esta clave no corresponde al código de autorización.
- Correo: formato convencional `usuario@dominio.extension`, hasta 254 caracteres.
- Teléfono: formato internacional tipo E.164, `+` seguido de 2 a 15 dígitos, sin cero inicial. Sin espacios ni guiones.
- El código debe tener exactamente 8 caracteres y no estar en blanco. Se guarda únicamente su hash BCrypt con factor 12 y salt aleatorio.
- Nombres y apellido, ciudad, provincia y país admiten hasta 100 caracteres; razón social, hasta 200.
- Se almacenan las fechas de creación y última actualización mediante auditoría JPA.

Los anexos de expresiones regulares y un nombre obligatorio de tabla/esquema no estaban incluidos en el enunciado recibido; se adoptan los formatos y nombres documentados aquí.

| Estado | Significado |
| --- | --- |
| 201 | Taller registrado |
| 400 | Datos inválidos, cuerpo mal formado o campos desconocidos |
| 409 | Registro duplicado |
| 405 | Método distinto de POST |
| 415 | Tipo de contenido no compatible |
| 500 | Fallo interno sin detalles sensibles en la respuesta |

Los errores contienen `timestamp`, `status`, `message`, `path` y `errors` (validaciones por campo). Use `Accept-Language: es` o `Accept-Language: en`; el idioma predeterminado es inglés. Los nombres de propiedades y los datos del taller conservan su valor; los mensajes se traducen.

## MySQL

Active el perfil `mysql`. Requiere un servidor MySQL accesible y un usuario con permisos sobre `daimler_truck`; la creación automática de la base requiere permisos adicionales.

```powershell
$env:SPRING_PROFILES_ACTIVE = 'mysql'
$env:DB_USERNAME = 'su_usuario'
$env:DB_PASSWORD = 'su_clave'
.\mvnw.cmd spring-boot:run
```

Opcionales: `DB_URL` para cambiar la conexión y `PORT` para cambiar el puerto. En MySQL la base predeterminada es `daimler_truck`, y la tabla es `authorized_workshops`. No hay credenciales incrustadas en el código.

## Organización

```text
workshops/
  application/     comandos, contrato e implementación del caso de uso
  domain/          agregado, objetos de valor, excepciones y puertos
  infrastructure/ BCrypt, adaptador JPA, entidad, converters y mapper
  interfaces/      controlador, recursos HTTP y transformador
shared/
  infrastructure/ configuración y base de auditoría
  interfaces/      errores HTTP localizados
```

El dominio no depende de Spring ni de JPA. Las dependencias se inyectan por constructor. El servicio coordina el registro mediante los puertos `AuthorizedWorkshopRepository` y `AuthCodeHasher`. No se configura CORS ni autenticación y no se añade una suite de tests, conforme al alcance.

Consulte `VERIFICATION.md` para los resultados de compilación, solicitudes HTTP reales, concurrencia e inspección de persistencia.
