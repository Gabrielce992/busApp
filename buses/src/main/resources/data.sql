-- data.sql idempotente — compatible con Spring JDBC init

-- Marcas: insertar solo si no existe por nombre
INSERT INTO brand (nombre)
SELECT 'Volvo'
    WHERE NOT EXISTS (SELECT 1 FROM brand WHERE nombre = 'Volvo');

INSERT INTO brand (nombre)
SELECT 'Scania'
    WHERE NOT EXISTS (SELECT 1 FROM brand WHERE nombre = 'Scania');

-- Buses: insertar solo si no existe la placa y asociar la marca por nombre
INSERT INTO bus (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, estado)
SELECT 'B-100', 'ABC-101', now(), '50 asientos, A/C',
       b.id, 'ACTIVO'
FROM brand b
WHERE b.nombre = 'Volvo'
  AND NOT EXISTS (SELECT 1 FROM bus WHERE placa = 'ABC-101');

INSERT INTO bus (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, estado)
SELECT 'B-101', 'ABC-102', now(), '40 asientos',
       b.id, 'INACTIVO'
FROM brand b
WHERE b.nombre = 'Scania'
  AND NOT EXISTS (SELECT 1 FROM bus WHERE placa = 'ABC-102');
