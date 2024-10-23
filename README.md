Price Management API

Descripción
Este proyecto es una API REST desarrollada en Spring Boot que permite consultar el
precio final de un producto específico para una cadena de tiendas en un rango de fechas
determinado. El sistema se basa en una base de datos en memoria (H2) que contiene los
precios disponibles y aplica la lógica de selección de precios basándose en las fechas de
vigencia de cada tarifa y la prioridad de aplicación.

Estructura del Proyecto
El proyecto sigue una estructura de capas bien definida, siguiendo los principios de SOLID y
Clean Code. Las capas principales son:
● Controller: Expone los endpoints REST.
● Service: Contiene la lógica de negocio, como la aplicación de filtros basados en
fechas y la prioridad de los precios.
● Repository: Interactúa con la base de datos H2, proporcionando acceso a los datos.
● Model: Define las entidades de la base de datos.
● DTO: Define los objetos de transferencia de datos (Data Transfer Objects) que son
retornados al cliente.

Tecnologías Utilizadas
● Java 17: Lenguaje de programación.
● Spring Boot 3.0: Framework para el desarrollo de aplicaciones web y API REST.
● H2 Database: Base de datos en memoria para el almacenamiento temporal de
precios.
● Maven: Herramienta de construcción y gestión de dependencias.
● JUnit 5: Marco de pruebas para test unitarios.
● MockMvc: Utilizado para simular y probar el comportamiento de la API.

Funcionalidades
● Consulta de precios: La API permite consultar el precio aplicable de un producto,
en base a la marca y la fecha de aplicación solicitada.
● Filtrado por fecha: Solo se aplican los precios dentro del rango de fechas de inicio y
fin definidos para cada producto.
● Aplicación de prioridades: En caso de que existan varias tarifas aplicables para un
mismo producto y cadena, se selecciona el precio con la mayor prioridad.

Estructura de Capas

1. Controlador (PriceController)
Exposición del endpoint para la consulta de precios:
● Endpoint: /api/prices
● Método: GET
● Parámetros:
○ date (String): Fecha de aplicación en formato yyyy-MM-ddTHH:mm:ss.
○ productId (Long): Identificador del producto.
○ brandId (Long): Identificador de la cadena de tiendas.
● El controlador llama al servicio PriceService para procesar la solicitud y devolver
el precio aplicable.

2. Servicio (PriceService)
El servicio contiene la lógica para filtrar y seleccionar el precio correcto. Los precios se
obtienen del repositorio y luego son filtrados según la applicationDate proporcionada:
● Se eliminan los precios que no estén vigentes en el rango de fechas.
● Se devuelve el precio con la mayor prioridad si existen múltiples precios aplicables.

3. Repositorio (PriceRepository)
Este repositorio proporciona acceso a los datos de precios almacenados en la base de
datos H2. Realiza consultas en función del productId y brandId y devuelve una lista de
precios.

4. Base de Datos
El proyecto utiliza una base de datos H2 en memoria, que se inicializa automáticamente al
arrancar la aplicación con los datos proporcionados en el archivo data.sql.

Ejecución del Proyecto

Requisitos Previos
● Java 17 o superior.
● Maven 3.6+ instalado.
Clonar el Repositorio
git clone https://github.com/TheJacin99/apirest.git
cd apirest

Construcción y Ejecución
1.Para construir y ejecutar el proyecto localmente:
mvn clean install
mvn spring-boot:run
1. La API estará disponible en http://localhost:8080/api/prices.
   
Probar la API
Ejemplo de petición GET al endpoint /api/prices:
curl -X GET
"http://localhost:8080/api/prices?date=2020-06-14T10:00:00&productId
=35455&brandId=1"
Respuesta Esperada:
{
"productId": 35455,
"brandId": 1,
"priceList": 1,
"startDate": "2020-06-14T00:00:00",
"endDate": "2020-12-31T23:59:59",
"price": 35.50
}

Pruebas
Se han implementado pruebas unitarias utilizando JUnit 5 y MockMvc para validar el
comportamiento de la API y asegurarse de que los precios devueltos son correctos según
los criterios establecidos.

Ejecutar las pruebas:
mvn test

Cobertura de Pruebas:
● Test 1: Consulta de precio a las 10:00 del 14 de junio de 2020 para productId
35455 y brandId 1.
● Test 2: Consulta de precio a las 16:00 del 14 de junio de 2020.
● Test 3: Consulta de precio a las 21:00 del 14 de junio de 2020.
● Test 4: Consulta de precio a las 10:00 del 15 de junio de 2020.
● Test 5: Consulta de precio a las 21:00 del 16 de junio de 2020.

Mejoras Posibles
● Documentación de API: Incluir una documentación generada automáticamente con
Swagger para facilitar el uso de la API.
● Autenticación y Autorización: Implementar seguridad básica para proteger el
acceso al endpoint.
● Cache: Implementar cache para mejorar la eficiencia del acceso repetido a los
mismos datos de precios.
Finalmente tambien se deberia hacer el filtrado de los productos en un fichero de querys, en la propia query poniendo que la fecha de aplicacion este between la start y end pero como es la primera vez que uso h2 como base de datos he decidido filtrarlo en java.

Enlaces de Referencia
● H2 Database
● Spring Boot REST API
● Clean Code
