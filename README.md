# Sistema Cliente-Servidor Concurrente en Java

Este repositorio contiene la implementación de una arquitectura Cliente-Servidor diseñada para la asignatura de **Programación Concurrente**. El objetivo principal del proyecto es gestionar la comunicación y el acceso a recursos compartidos entre múltiples clientes simultáneos, aplicando diversas estrategias de sincronización y exclusión mutua de forma manual y mediante librerías de Java.

Es un proyecto hecho en parejas, con mi compañero Daniel Casquero Palencia.

## 📋 Descripción del Proyecto

El sistema permite el intercambio de información mediante el paso de mensajes (Message Passing) utilizando **Sockets TCP**. El servidor es capaz de atender múltiples peticiones concurrentes asegurando la integridad de los datos y evitando condiciones de carrera (*race conditions*).

### 🚀 Características Técnicas

* **Lenguaje:** Java.
* **Comunicación:** Sockets (TCP/IP).
* **Modelo:** Cliente-Servidor Multihilo.
* **Protocolo de Comunicación:** Objetos serializados personalizados (ver paquete `mensaje`).

### ⚙️ Gestión de la Concurrencia

Para garantizar la seguridad en el acceso a las secciones críticas, se han implementado y comparado diferentes mecanismos de sincronización:

* **Locks:** Implementación de algoritmos clásicos de exclusión mutua (ej. Algoritmo de la Panadería / Bakery Algorithm).
* **Semáforos:** Implementación de semáforos binarios y contadores para la gestión de recursos limitados.
* **Monitores:** Uso de bloques `synchronized` y mecanismos `wait/notify` de Java.
* **Gestión de Hilos:** Uso de `Thread` y `Runnable` para la atención individualizada de clientes.

## 📂 Estructura del Proyecto

El código fuente se organiza en la carpeta `src` siguiendo una estructura modular:

* `src/servidor`: Contiene la lógica del servidor, el `ServerSocket` y los *handlers* para los hilos de cada cliente.
* `src/cliente`: Interfaz y lógica de conexión para el usuario.
* `src/mensaje`: Definición del protocolo de comunicación. Contiene las clases de los diferentes tipos de mensajes (Login, Petición, Confirmación, etc.).
* `src/baseDatos`: Gestión de la persistencia de datos (lectura/escritura de archivos).
* `src/usuario`: Modelado de la entidad usuario.

## 📄 Licencia

Este proyecto está bajo la Licencia **MIT** - mira el archivo [LICENSE](LICENSE) para más detalles.

---
**Autores:**
* [Luis Orofino Álvarez]
* [Daniel Casquero Palencia]
