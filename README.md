# Ejemplo WEB para subir objeto a S3

Proyecto de ejemplo para subir un objeto a S3, se esta utilizando un simple servlet directo que sube un archivo con contenido dummy al bucket de MDM de SEEKOP.
Para ejecutar el ejemplo desplegar usando un servidor de aplicaciones como WildFly o Tomcat e invocar http://localhost:8080/example/upload

- El proyecto usa el SDK de AWS por medio de Maven, declarando la dependencia en el pom.xml
- Para este ejemplo solo de importa el SDK para S3
- Se usa autenticacion basica para AWS usando ACCESS KEY y SECRET KEY, otros metodos de autenticacion revisarlos en la documentacion de AWS
- El bucket defualt para cargar archivos para MDM de SEEKOP es: **sicop-mdm**
- La región default a utilizar debe **US-WEST-1** (California)
- Cada usuario de MDM tiene un id definido, este debe usarse como prefijo para formar el KEY (ruta) del objeto a subir, por ejemplo: 165/mi_archivo.txt donde 165 es el id del cliente
