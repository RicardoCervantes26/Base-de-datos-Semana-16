-- =============================================
-- 1. CREACIÓN Y CONFIGURACIÓN
-- =============================================
USE BibliotecaSistema;
GO

-- =============================================
-- 2. ESTRUCTURA DE TABLAS
-- =============================================
CREATE TABLE [dbo].[Usuarios](
    [ID] [varchar](10) PRIMARY KEY,
    [Nombre] [nvarchar](100) NOT NULL,
    [Apellido] [nvarchar](100) NOT NULL,
    [Dni] [char](8) UNIQUE NOT NULL
);

CREATE TABLE [dbo].[Material](
    [ID] [varchar](10) PRIMARY KEY,
    [Titulo] [nvarchar](200) NOT NULL,
    [Autor] [nvarchar](200) NOT NULL,
    [Anio] [smallint],
    [Disponibilidad] [nvarchar](20) CHECK ([Disponibilidad] IN ('disponible', 'no disponible'))
);

CREATE TABLE [dbo].[Prestamos](
    [ID] [int] IDENTITY(1,1) PRIMARY KEY,
    [UsuarioID] [varchar](10) REFERENCES [dbo].[Usuarios](ID),
    [MaterialID] [varchar](10) REFERENCES [dbo].[Material](ID),
    [FechaPrestamo] [date] DEFAULT GETDATE()
);
GO

-- =============================================
-- 3. CARGA MASIVA DE DATOS
-- =============================================
INSERT INTO [dbo].[Usuarios] VALUES 
('USR001', 'Juan', 'Perez', '12345678'),
('USR002', 'Maria', 'Lopez', '87654321'),
('USR003', 'Carlos', 'Gomez', '11223344'),
('USR004', 'Ana', 'Torres', '55667788'),
('USR005', 'Luis', 'Ramirez', '99001122'),
('USR006', 'Sofia', 'Vega', '22334455'),
('USR007', 'Diego', 'Mendoza', '66778899'),
('USR008', 'Elena', 'Rojas', '11559900');

INSERT INTO [dbo].[Material] VALUES 
('LIB001', 'Cien Años de Soledad', 'Gabriel García Márquez', 1967, 'disponible'),
('LIB002', 'El Principito', 'Antoine de Saint-Exupéry', 1943, 'no disponible'),
('LIB003', 'Don Quijote de la Mancha', 'Miguel de Cervantes', 1605, 'disponible'),
('LIB004', '1984', 'George Orwell', 1949, 'disponible'),
('LIB005', 'Crimen y Castigo', 'Fiodor Dostoyevski', 1866, 'no disponible'),
('LIB006', 'El Hobbit', 'J.R.R. Tolkien', 1937, 'disponible'),
('LIB007', 'Rayuela', 'Julio Cortázar', 1963, 'disponible');

INSERT INTO [dbo].[Prestamos] (UsuarioID, MaterialID, FechaPrestamo) VALUES 
('USR001', 'LIB002', '2026-06-01'),
('USR002', 'LIB001', '2026-05-10'),
('USR003', 'LIB004', '2026-06-01'),
('USR004', 'LIB005', '2026-06-05'),
('USR005', 'LIB003', '2026-06-08'),
('USR006', 'LIB007', '2026-06-01'),
('USR007', 'LIB003', '2026-06-07'),
('USR008', 'LIB004', '2026-06-08');
GO

-- =============================================
-- 4. LÓGICA AVANZADA (Funciones y SPs)
-- =============================================

-- Función para calcular días de préstamo
CREATE FUNCTION [dbo].[fn_DiasPrestamo](@PrestamoID INT) RETURNS INT AS
BEGIN
    RETURN (SELECT ISNULL(DATEDIFF(DAY, FechaPrestamo, GETDATE()), 0) FROM Prestamos WHERE ID = @PrestamoID);
END;
GO

-- Reporte de Promedios
CREATE PROCEDURE [dbo].[SP_ObtenerReportePrestamos] AS
BEGIN
    SELECT U.Nombre + ' ' + U.Apellido AS Cliente, COUNT(P.ID) AS CantidadPrestamos, AVG(CAST(M.Anio AS FLOAT)) AS PromedioAnioLibros
    FROM Usuarios U INNER JOIN Prestamos P ON U.ID = P.UsuarioID INNER JOIN Material M ON P.MaterialID = M.ID
    GROUP BY U.Nombre, U.Apellido;
END;
GO

-- Transacción para Registrar Prestamo
CREATE PROCEDURE [dbo].[SP_RegistrarPrestamoTransaccional]
    @UsuarioID VARCHAR(10), @MaterialID VARCHAR(10), @Resultado VARCHAR(100) OUTPUT
AS
BEGIN
    BEGIN TRANSACTION;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM Material WHERE ID = @MaterialID AND Disponibilidad = 'disponible')
            THROW 50000, 'El material no está disponible', 1;

        INSERT INTO Prestamos (UsuarioID, MaterialID) VALUES (@UsuarioID, @MaterialID);
        UPDATE Material SET Disponibilidad = 'no disponible' WHERE ID = @MaterialID;
        COMMIT TRANSACTION;
        SET @Resultado = 'OK';
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        SET @Resultado = 'ERROR: ' + ERROR_MESSAGE();
    END CATCH
END;
GO