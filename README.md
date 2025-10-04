# 🚌 BusApp — Sistema de Gestión de Buses

Proyecto **Full-Stack** desarrollado como reto técnico.  
Incluye **API REST segura** (Spring Boot + PostgreSQL), **frontend web** (React + Vite) y colección **Postman** para validación.

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
```

---

## 📊 Diagrama de Arquitectura

A continuación se muestran los diagramas utilizados en el proyecto:

### 🔹 Diagrama General
![Diagrama General](./diag.png)

### 🔹 Vista de la Aplicación
![Vista de la Aplicación](./vista.JPG)

---

## ⚙️ Requisitos Previos

* Java **21+**
* Maven **3+**
* PostgreSQL **15+**
* Node.js **18+**
* Postman (última versión)
* IDEs recomendados:
  * Backend → IntelliJ IDEA Ultimate
  * Frontend → VS Code

---

## 🔥 Configuración del Backend

### 1. Crear Base de Datos PostgreSQL
```sql
CREATE DATABASE busapp;
CREATE USER postgres WITH PASSWORD '???';
GRANT ALL PRIVILEGES ON DATABASE busapp TO postgres;
```

---

### 2. Configurar `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/busapp
spring.datasource.username=postgres
spring.datasource.password=???

spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS=false
```

---

### 3. Cargar Datos Iniciales (`data.sql`)
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

### 4. Ejecutar Backend
```bash
cd backend
mvn spring-boot:run
```

👉 API disponible en: [http://localhost:8080](http://localhost:8080)

---

## ⚛️ Configuración del Frontend

### 1. Instalar dependencias
```bash
cd frontend
npm install
```

### 2. Configurar `.env`
```bash
VITE_BACKEND_URL=http://localhost:8080
```

### 3. Ejecutar Frontend
```bash
npm run dev
```

👉 UI disponible en: [http://localhost:5173](http://localhost:5173)

---

## 🔑 Seguridad

* Autenticación **Basic Auth**
* Usuario por defecto:
```
user / password
```
* Header de autorización:
```
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

---

## 📂 Endpoints Principales

| Método | Endpoint  | Descripción             | Auth |
| ------ | --------- | ----------------------- | ---- |
| GET    | /bus      | Listar buses (paginado) | ✅    |
| GET    | /bus/{id} | Obtener bus por ID      | ✅    |
| POST   | /bus      | Crear nuevo bus         | ✅    |

Ejemplo:
```bash
GET http://localhost:8080/bus?page=0&size=10
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

---

## 🧪 Pruebas con Postman

La colección Postman está en:
`postman/BusApp.postman_collection.json`

---

### 1. 📥 Importar colección
1. Abrir **Postman → Import**  
2. Seleccionar archivo `BusApp.postman_collection.json`  
3. La colección aparecerá en el panel izquierdo como **BusApp**

---

### 2. ⚙️ Configurar Variables
En la pestaña **Variables** de la colección:

| Variable          | Valor                          |
|-------------------|---------------------------------|
| `baseUrl`         | `http://localhost:8080`        |
| `basicAuthHeader` | `Basic dXNlcjpwYXNzd29yZA==`    |

---

### 3. 🚀 Requests Incluidos

#### ✅ Get Buses (paginado)
- **URL:** `{{baseUrl}}/bus?page=0&size=10`  
- **Método:** `GET`  
- **Header:** `Authorization: {{basicAuthHeader}}`

---

#### ✅ Get Bus by ID
- **URL:** `{{baseUrl}}/bus/1`  
- **Método:** `GET`  
- **Header:** `Authorization: {{basicAuthHeader}}`

---

#### ✅ Create Bus (dinámico)
- **URL:** `{{baseUrl}}/bus`  
- **Método:** `POST`  
- **Headers:**  
  - `Authorization: {{basicAuthHeader}}`  
  - `Content-Type: application/json`

**Body:**
```json
{
  "numeroBus": "{{numeroBus}}",
  "placa": "{{placa}}",
  "caracteristicas": "{{caracteristicas}}",
  "marcaId": {{marcaId}},
  "estado": "{{estado}}"
}
```

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

### 5. 🧩 Validación Rápida
1. Ejecutar **Get Buses** → debes ver los buses iniciales (ej. `B-100`, `B-101`).  
2. Ejecutar **Create Bus** → debe responder `201 Created`.  
3. Ejecutar **Get Buses** nuevamente → aparece el nuevo bus creado.  
4. Ejecutar **Get Bus by ID** → ver detalles de un bus específico.

---

## 🌐 Frontend en Acción

El frontend permite:
* Ver lista de buses paginada
* Consultar detalles por ID
* Navegar entre páginas

---

## ✅ Checklist de Verificación

### ✅ Backend
* [x] Modelo **Bus** con campos requeridos + relación con **Marca**
* [x] Endpoints `/bus` y `/bus/{id}` con paginación
* [x] Seguridad con **Basic Auth**
* [x] Base de datos **PostgreSQL** configurada
* [x] Proyecto subido a repositorio remoto

### ✅ Frontend
* [x] **React 18+**, uso de **useState** y **useEffect**
* [x] Tabla que muestra datos de la API
* [x] Consumo de `/bus/{id}`
* [x] Paginación en la tabla
* [x] Repositorio remoto disponible

### ✅ Postman
* [x] Colección importable con variables y scripts
* [x] Validación dinámica de creación de buses

---

## 🚀 Flujo de Trabajo
1. Levantar PostgreSQL
2. Ejecutar backend → `mvn spring-boot:run`
3. Ejecutar frontend → `npm run dev`
4. Validar API con Postman
5. Visualizar buses en [http://localhost:5173](http://localhost:5173)

---

## 📜 Licencia
Proyecto académico — uso educativo.

---

## 👤 Autor
**Castañeda Huaytalla Gab** — Técnico Practicante  
Desarrollador Full-Stack Java & React  
Reto solicitado por **Civa — 2025**
