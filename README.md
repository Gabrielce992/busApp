
# 🚌 BusApp — Sistema de Gestión de Buses  

> Proyecto **Full Stack** desarrollado como **reto técnico**.  
Incluye una **API REST segura** (Spring Boot + PostgreSQL), un **frontend web moderno** (React + Vite) y una **colección Postman** para validación de endpoints.  

---

## 🧩 Tecnologías Utilizadas  

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=springboot)
![React](https://img.shields.io/badge/React-18-blue?logo=react)
![Vite](https://img.shields.io/badge/Vite-4-purple?logo=vite)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3-red?logo=apachemaven)
![Node.js](https://img.shields.io/badge/Node.js-18-green?logo=node.js)
![Postman](https://img.shields.io/badge/Postman-Collection-orange?logo=postman)

---

## 📂 Estructura del Repositorio  

```plaintext
BusApp/
├── backend/                # API Spring Boot + PostgreSQL
│   ├── src/main/java/com/busapp/proyect
│   │   ├── BusAppApplication.java
│   │   ├── config/         # Security, CORS
│   │   ├── controller/     # BusController
│   │   ├── dto/            # BusDTO
│   │   ├── model/          # Bus, Brand
│   │   ├── repository/     # JPA Repos
│   │   ├── service/        # BusService
│   │   └── exceptions/     # GlobalExceptionHandler
│   ├── src/main/resources
│   │   ├── application.properties
│   │   └── data.sql
│   └── pom.xml
│
├── frontend/               # React + Vite
│   ├── src/
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   ├── components/tables/BusesTable.jsx
│   │   ├── services/busService.js
│   │   └── styles/
│   ├── package.json
│   └── .env
│
└── postman/
    └── BusApp.postman_collection.json
````

---

## 📊 Diagramas del Proyecto

### 🔹 Diagrama General

![Diagrama General](./diag.png)

### 🔹 Vista de la Aplicación

![Vista de la Aplicación](./vista.JPG)

---

## ⚙️ Requisitos Previos

* ☕ **Java 21+**
* 🧱 **Maven 3+**
* 🐘 **PostgreSQL 15+**
* ⚛️ **Node.js 18+**
* 📬 **Postman** (última versión)
* 💻 IDEs recomendados:

  * Backend → IntelliJ IDEA Ultimate
  * Frontend → VS Code

---

## 🔧 Configuración del Backend

### 1️⃣ Crear Base de Datos PostgreSQL

```sql
CREATE DATABASE busapp;
CREATE USER postgres WITH PASSWORD '???';
GRANT ALL PRIVILEGES ON DATABASE busapp TO postgres;
```

---

### 2️⃣ Configurar `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/busapp
spring.datasource.username=postgres
spring.datasource.password=???

spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS=false
```

---

### 3️⃣ Cargar Datos Iniciales (`data.sql`)

```sql
INSERT INTO brand (nombre)
SELECT 'Volvo' WHERE NOT EXISTS (SELECT 1 FROM brand WHERE nombre='Volvo');

INSERT INTO brand (nombre)
SELECT 'Scania' WHERE NOT EXISTS (SELECT 1 FROM brand WHERE nombre='Scania');

INSERT INTO bus (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, estado)
SELECT 'B-100','ABC-101',now(),'50 asientos, A/C',b.id,'ACTIVO'
FROM brand b WHERE b.nombre='Volvo'
AND NOT EXISTS (SELECT 1 FROM bus WHERE placa='ABC-101');
```

---

### ▶️ Ejecutar Backend

```bash
cd backend
mvn spring-boot:run
```

> 🟢 API disponible en: [http://localhost:8080](http://localhost:8080)

---

## ⚛️ Configuración del Frontend

### 1️⃣ Instalar dependencias

```bash
cd frontend
npm install
```

### 2️⃣ Configurar `.env`

```bash
VITE_BACKEND_URL=http://localhost:8080
```

### 3️⃣ Ejecutar Frontend

```bash
npm run dev
```

> 🟣 UI disponible en: [http://localhost:5173](http://localhost:5173)

---

## 🔒 Seguridad

* Autenticación: **Basic Auth**
* Usuario por defecto:

  ```
  user / password
  ```
* Header:

  ```
  Authorization: Basic dXNlcjpwYXNzd29yZA==
  ```

---

## 🌐 Endpoints Principales

| Método | Endpoint  | Descripción             | Auth |
| ------ | --------- | ----------------------- | ---- |
| GET    | /bus      | Listar buses (paginado) | ✅    |
| GET    | /bus/{id} | Obtener bus por ID      | ✅    |
| POST   | /bus      | Crear nuevo bus         | ✅    |

📌 Ejemplo:

```bash
GET http://localhost:8080/bus?page=0&size=10
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

---

## 🧪 Pruebas con Postman

**Colección:** `postman/BusApp.postman_collection.json`

1. 📥 **Importar colección:**

   * Postman → Import → Selecciona archivo

2. ⚙️ **Configurar variables:**

   | Variable          | Valor                        |
   | ----------------- | ---------------------------- |
   | `baseUrl`         | `http://localhost:8080`      |
   | `basicAuthHeader` | `Basic dXNlcjpwYXNzd29yZA==` |

3. 🚀 **Requests incluidos:**

   * `GET /bus` (paginado)
   * `GET /bus/{id}`
   * `POST /bus` (creación dinámica con script JS)

---

### 4. ⚙️ Script Pre-request

El request **Create Bus** incluye un script que genera datos automáticamente:

```javascript
if (!pm.environment.get('counter')) {
    pm.environment.set('counter', 2);
}

let i = parseInt(pm.environment.get('counter')) + 1;

let marca = (i % 2 === 0) ? 1 : 2;
let estado = (i % 2 === 0) ? 'ACTIVO' : 'INACTIVO';

let placa = 'ABC-' + (100 + i);
let numeroBus = 'B-' + (100 + i);
let caracteristicas = (i % 3 === 0) ? '30 asientos, A/C' : '40 asientos, A/C';

pm.environment.set('numeroBus', numeroBus);
pm.environment.set('placa', placa);
pm.environment.set('caracteristicas', caracteristicas);
pm.environment.set('marcaId', marca);
pm.environment.set('estado', estado);
pm.environment.set('counter', i);
```

👉 Cada vez que presiones **Send**, se crea un bus nuevo con datos diferentes.

---

## 🧠 Validación Rápida

1. Ejecutar **Get Buses** → ver datos iniciales
2. Ejecutar **Create Bus** → respuesta `201 Created`
3. Repetir **Get Buses** → nuevo bus visible
4. Ejecutar **Get Bus by ID** → ver detalles

---

## 🌐 Frontend en Acción

El **frontend React** permite:

* 📋 Listar buses con paginación
* 🔍 Consultar detalles por ID
* ⚡ Renderizado dinámico desde API REST

---

## ✅ Checklist Final

### 🧩 Backend

* [x] Modelo Bus + relación Marca
* [x] Endpoints `/bus` y `/bus/{id}`
* [x] Basic Auth implementado
* [x] PostgreSQL configurado
* [x] Deploy local funcional

### ⚛️ Frontend

* [x] React 18 + Hooks
* [x] Tabla dinámica con paginación
* [x] Consumo directo del backend
* [x] Integración con `.env`

### 🧪 Postman

* [x] Scripts automáticos
* [x] Variables y entornos configurables

---

## 🚀 Flujo de Ejecución

1️⃣ Levantar PostgreSQL
2️⃣ Ejecutar Backend → `mvn spring-boot:run`
3️⃣ Ejecutar Frontend → `npm run dev`
4️⃣ Validar con Postman
5️⃣ Visualizar datos en el navegador

---

## 📜 Licencia

🧾 Proyecto académico — uso educativo.

---

## 👨‍💻 Autor

**Castañeda Huaytalla Gabriel**
Desarrollador Full Stack — *Java | Spring Boot | React*

📧 [gabrielce992@gmail.com](mailto:gabrielce992@gmail.com)

🏢 Reto solicitado por **Civa — 2025**




