## Spring Boot

Proyecto Spring Boot con las dependencias / starters:

Starters para persistencia (la parte de base de datos)
* H2
* Spring Data JPA

Starters para web (osea para la creacíon de controladores)
* Spring Web
* Spring Boot Dev Tools

Aplicación API REST con acceso a base de datos H2 para persistir la informacíon.

El acceso se puede realizar desde Postman o Navegador.

## Entidad book

1. Book
2. BookRepository
3. BookController
    1. Buscar todos los libros
    2. Buscar un solo libro
    3. Crear un nuevo libro
    4. Actualizar un libro esistente
    5. Borrar un libro
    6. Borrar todos los libros