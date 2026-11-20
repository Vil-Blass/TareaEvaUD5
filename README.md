# AD05 - Cuadernos y notas

Aplicación web para administrar cuadernos temáticos y sus notas. Cada nota pertenece a un único cuaderno. Al eliminar un cuaderno, sus notas se eliminan mediante la cascada JPA; eliminar una nota no afecta al cuaderno ni a otras notas.

## Versiones y tecnologías

- Java 17
- Spring Boot 3.4.4
- Spring MVC, Spring Data JPA, Thymeleaf y Jakarta Bean Validation
- Oracle JDBC `ojdbc11` 23.6.0.24.10
- Oracle Database XE 21c, servicio `XEPDB1`
- Bootstrap 5.3.3 (hoja de estilos CDN)

## Preparación

1. Instala JDK 17 y Maven 3.9.15 (el POM usa Java 17), y arranca Oracle XE 21c.
2. Si aún no tienes un esquema de prácticas, utiliza el script `preparar_usuario_oracle.sql` proporcionado con la tarea, conectado como administrador al servicio `XEPDB1`. Sustituye la clave de ejemplo por una propia; no guardes la clave real en este repositorio. La aplicación no debe conectarse como `SYSTEM`.
3. Configura estas variables de entorno para el usuario/esquema de prácticas:

   - `DB_USERNAME`: nombre del usuario de prácticas.
   - `DB_PASSWORD`: su contraseña.
   - `DB_URL` (opcional): URL JDBC. Por defecto es `jdbc:oracle:thin:@localhost:1521/XEPDB1`.

   Ejemplo en PowerShell (solo para la sesión actual; reemplaza el valor de ejemplo):

   ```powershell
   $env:DB_USERNAME = "ad05_practicas"
   $env:DB_PASSWORD = "tu-clave-local"
   ```

4. Desde la carpeta del proyecto, ejecuta:

   ```text
   mvn spring-boot:run
   ```

   Abre `http://localhost:8080`. Hibernate usa `spring.jpa.hibernate.ddl-auto=update` para crear o actualizar las tablas del esquema configurado. Los datos se guardan en Oracle y permanecen tras reiniciar.

## Estructura funcional

- `/cuadernos`: alta, listado, edición y confirmación de borrado de cuadernos.
- `/notas`: alta, listado, edición y confirmación de borrado de notas, con selector de cuaderno.
- La edición conserva el identificador de la fila. El servidor valida campos y comprueba que existe el cuaderno elegido.
- La página de confirmación se consulta con GET; el borrado se ejecuta únicamente al confirmar mediante POST.

## Verificación y entrega

Ejecuta `mvn test` para verificar la compilación y las pruebas disponibles. Antes de entregar, comprueba los flujos con la base de datos elegida, añade evidencias propias (capturas o vídeo) y registra la versión entregada mediante un commit o una etiqueta Git. No subas credenciales, datos reales ni la carpeta `target`.

## Uso de IA

Se utilizó IA como apoyo. Interacción representativa: se solicitó crear el proyecto a partir del enunciado; se generaron el modelo cuaderno-nota, los CRUD y la configuración Oracle. Se ajustaron las consultas para cargar las relaciones mostradas, se mantuvieron sincronizados ambos lados de la relación y se evitó `orphanRemoval` para que cambiar una nota de cuaderno no la borre. La inspección de diagnósticos del IDE terminó sin errores; la compilación Maven y la prueba con Oracle quedan pendientes de ejecutarse en un entorno con Maven y Oracle XE configurados. Añade a la autoevaluación las evidencias propias y las decisiones que puedas explicar.
