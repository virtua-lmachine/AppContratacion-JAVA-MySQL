# 📋 Aplicación de Contratación — Registro de Aspirantes

Aplicación de escritorio en Java MVC patrón de diseño: model, view, controller (monolito modular) con DAO data access object que permite registrar aspirantes de empleo desde un formulario Swing y persistir la información en una base de datos MySQL local, demostrando el uso correcto de los tipos de datos **ENUM** y **SET**.

---

## 🛠️ Tecnologías

| Herramienta | Versión / Detalle |
|---|---|
| Java SE | JDK 11 o superior |
| IntelliJ IDEA | Community o Ultimate |
| MySQL | 8.x |
| HeidiSQL | Última versión estable |
| MySQL Connector/J | mysql-connector-j-x.x.x.jar |

---

## 📁 Estructura del Proyecto

```
contratacion/
├── src/
│   └── main/
│       └── java/
│           └── com/contratacion/
│               ├── model/
│               │   └── Aspirante.java          # POJO / entidad
│               ├── db/
│               │   └── ConexionDB.java          # Gestión de conexión JDBC
│               ├── dao/
│               │   ├── AspiranteDAO.java        # Interfaz del contrato
│               │   └── AspiranteDaoImpl.java    # Implementación con PreparedStatement
│               └── view/
│                   └── FormularioAspirante.java # JFrame principal (Swing)
└── lib/
    └── mysql-connector-j-x.x.x.jar
```

---

## ⚙️ Configuración de la Base de Datos

**Credenciales por defecto**

```
Host     : localhost
Puerto   : 3306
Usuario  : root
Contraseña: mysql
Base de datos: contratacion_db
```

**Script de creación — ejecutar en HeidiSQL**

```sql
CREATE TABLE IF NOT EXISTS `aspirantes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_completo` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cedula` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nivel_estudios` enum('bachiller','tecnico','tecnologo','profesional') COLLATE utf8mb4_unicode_ci NOT NULL,
  `turno_preferencia` enum('manana','tarde','noche') COLLATE utf8mb4_unicode_ci NOT NULL,
  `idiomas` set('español','ingles','frances','aleman','portugues') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `habilidades_tecnicas` set('java','sql','git','html','css','python','excel') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cedula` (`cedula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=UTF8MB4_UNICODE_CI;
```

> ⚠️ Todos los valores de ENUM y SET están en **minúsculas sin tildes**. Deben coincidir exactamente con los valores que Java envía desde el formulario.

---

## 🔌 Agregar el Driver JDBC en IntelliJ

1. Descargar `mysql-connector-j-x.x.x.jar` desde [dev.mysql.com/downloads/connector/j](https://dev.mysql.com/downloads/connector/j/)
2. Copiar el `.jar` a la carpeta `lib/` del proyecto
3. En IntelliJ: `File → Project Structure → Modules → Dependencies → [+] → JARs or directories`
4. Seleccionar el archivo `.jar` y confirmar con **OK**
5. Verificar que aparezca con scope **Compile**

---

## 🖥️ Componentes del Formulario

| Campo BD | Tipo MySQL | Componente Swing | Comportamiento |
|---|---|---|---|
| nombre_completo | VARCHAR | JTextField | `getText().trim()` |
| cedula | VARCHAR | JTextField | `getText().trim()` |
| nivel_estudios | ENUM | JComboBox | `getSelectedItem().toString()` → 1 valor |
| turno_preferencia | ENUM | JComboBox | `getSelectedItem().toString()` → 1 valor |
| idiomas | SET | JCheckBox[] | `isSelected()` → `join(",")` → String |
| habilidades_tecnicas | SET | JCheckBox[] | `isSelected()` → `join(",")` → String |

---

## 🔄 Flujo de Ejecución

```
Usuario completa el formulario
        ↓
Validación en Java (nombre, cédula, SET ≥ 1 opción)
        ↓
Construcción del objeto Aspirante (POJO)
        ↓
ConexionDB.obtenerConexion() → JDBC abre canal con MySQL
        ↓
PreparedStatement → INSERT INTO aspirantes (...)
        ↓
MySQL valida ENUM y SET contra los valores definidos
        ↓
   ✓ Éxito              ✗ SQLException capturada
        ↓
connection.close() → Liberar recursos
```

---

## ⚠️ Errores Comunes

| Error | Causa | Solución |
|---|---|---|
| `Data truncated for column (ENUM)` | El String enviado no existe en el ENUM | Verificar que el JComboBox use los mismos valores del script SQL |
| `Data truncated for column (SET)` | Un valor del SET no está en la lista definida | Verificar que los JCheckBox coincidan exactamente con el SET |
| `Communications link failure` | MySQL no está corriendo o la URL es incorrecta | Iniciar el servicio MySQL y revisar la URL de conexión |
| `Class not found: com.mysql.cj.jdbc.Driver` | El .jar no está en el classpath | Agregar el Connector/J a Project Structure → Dependencies |
| `Duplicate entry for key 'cedula'` | Cédula ya registrada | Informar al usuario con JOptionPane |

---

## ✅ Verificación Final

Después de registrar un aspirante, confirmar en HeidiSQL:

```sql
SELECT * FROM contratacion_db.aspirantes;

-- ENUM debe traer un solo valor  → 'bachiller'
-- SET  debe traer varios valores → 'java,sql,git'
```

---
