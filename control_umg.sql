-----crear la base de datos ---
CREATE DATABASE control_umg;
USE control_umg;

----crear las tablas con su relacionamiento---
CREATE TABLE Estudiantes (
  ID_estudiante INT PRIMARY KEY,
  Nombre VARCHAR(100),
  Apellido VARCHAR(100),
  telefono VARCHAR(15),
  correo VARCHAR(40)
);


CREATE TABLE Profesores (
  ID_profesor INT PRIMARY KEY,
  Nombre VARCHAR(100),
  Apellido VARCHAR(100),
  telefono VARCHAR(15),
   correo VARCHAR(40)
);


CREATE TABLE Cursos (
  ID_curso INT PRIMARY KEY,
  Nombre VARCHAR(255),
  Descripcion TEXT
);


CREATE TABLE Asignaciones_cursos (
  ID_asignacion INT PRIMARY KEY,
  ID_estudiante INT,
  ID_curso INT,
  FOREIGN KEY (ID_estudiante) REFERENCES Estudiantes (ID_estudiante),
  FOREIGN KEY (ID_curso) REFERENCES Cursos (ID_curso)
);


CREATE TABLE Asignaciones_profesores (
  ID_asignacion INT PRIMARY KEY,
  ID_profesor INT,
  ID_curso INT,
  FOREIGN KEY (ID_profesor) REFERENCES Profesores (ID_profesor),
  FOREIGN KEY (ID_curso) REFERENCES Cursos (ID_curso)
);


------script para mostrar los datos de las tablas relacionadas----
    
SELECT Asignaciones_cursos.ID_asignacion, Estudiantes.ID_estudiante, Estudiantes.Nombre, Estudiantes.Apellido, Cursos.ID_curso, Cursos.Nombre
FROM Estudiantes
JOIN Asignaciones_cursos ON Estudiantes.ID_estudiante = Asignaciones_cursos.ID_estudiante
JOIN Cursos ON Asignaciones_cursos.ID_curso = Cursos.ID_curso;


SELECT Asignaciones_profesores.ID_asignacion, Profesores.ID_profesor, Profesores.nombre, Profesores.apellido, Cursos.ID_curso, Cursos.nombre
FROM Asignaciones_profesores
JOIN Profesores ON Asignaciones_profesores.ID_profesor = Profesores.ID_profesor
JOIN Cursos ON Asignaciones_profesores.ID_curso = Cursos.ID_curso;
