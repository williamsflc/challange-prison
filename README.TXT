El siguiente software se realiza como proyecto de prueba
No se proprociona ninguna garantia sobre su funcionamiento

#Preparación del entorno de desarrollo

1. Descargue e instale Java JDK SE 1.8
   https://www.oracle.com/java/technologies/downloads/

   Configurar la variable JAVA_HOME puntando al directorio de instalación del JDK
   Configurar la carpeta .../bin de java en la variable de entorno PATH

2. Descargue e instale maven
   https://maven.apache.org/install.html

   Configurar la carpeta .../bin de maven en la variable de entorno PATH


#Compilar, probar y ejecutar aplicación

1. Desde el cmd, ir a la carpeta donde se clonó el proyecto
   
   Compilar:
   $ mvn compile
   
   Ejecutar el test (JUnit):
   $ mvn test

   Empaquetar el programa
   $ mvn package


   Ejecutar la aplicación en modo UI:
   $ java -cp target\challange-prison-1.0-SNAPSHOT.jar com.challange.prison.PrisonApp

   Ejecutar la aplicación en modo CMD:
   $ java -cp target\challange-prison-1.0-SNAPSHOT.jar com.challange.prison.PrisonApp 20 prison-example-1.txt

       donde 20 es la resistencia del prisionero y 
       prison-example-1.txt es el archivo que contiene la definición de la prisión.

   Se comparte en el repositorio los siguientes archivos con definicion de prisiones:

    1. prison-example-1.txt  (La prision de la definición del challange )
    2. prison-example-2-con-prisionero-al-lado-de-salida.txt  (Prision donde se ubica al prisionero a la par de la salida, esta debe generar error al cargar con mensaje indicandolo)
    3. prison-example-3-con-2-salidas.txt (Prision con 2 salidas)
    4. prison-example-4-grande.txt (Se elabora una prision más grande)
    5. prison-example-5-grande-sin-salida.txt



Información adicional del código:

La información se lee de una archivo de texto y se carga en una matriz de Java
El análisis de rutas de realiza con una estructura de datos de árbol.
El algoritmo se encuentra en el archivo: PrisonEscapeAlg.java
La implementación de la estructura de árbol se encuentra en el archivo: TreeRouteNode.java
Se crea un UI sencillo para facilitar el testeo, sin embargo es posible ejecutar la aplicación desde consola.



