-- 1. CREACIÓN DE LA BASE DE DATOS Y TABLAS
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'Backups')
BEGIN
    CREATE DATABASE Backups;
END
GO

USE Backups;
GO

-- Limpiamos tablas si ya existen para evitar errores al ejecutar varias veces
IF OBJECT_ID('Empleados', 'U') IS NOT NULL DROP TABLE Empleados;
IF OBJECT_ID('Departamentos', 'U') IS NOT NULL DROP TABLE Departamentos;

CREATE TABLE Departamentos (
    ID INT PRIMARY KEY,
    Nombre NVARCHAR(50)
);

CREATE TABLE Empleados (
    ID INT PRIMARY KEY,
    Nombre NVARCHAR(100),
    DepartamentoID INT,
    FOREIGN KEY (DepartamentoID) REFERENCES Departamentos(ID)
);
GO

-- 2. RELLENAR LA BASE DE DATOS
INSERT INTO Departamentos (ID, Nombre) VALUES (1, 'Tecnología'), (2, 'Recursos Humanos'), (3, 'Finanzas');
INSERT INTO Empleados (ID, Nombre, DepartamentoID) VALUES (101, 'Ana Lopez', 1), (102, 'Carlos Ruiz', 2), (103, 'Maria Gomez', 3);
GO

-- 3. VERIFICACIÓN DE DATOS
SELECT * FROM Departamentos;
SELECT * FROM Empleados;
GO

-- 4. RESPALDO (Asegúrate de que la carpeta C:\Backups exista)
BACKUP DATABASE [Backups] TO DISK = 'C:\Backups\GestionEmpresarial.bak';
GO