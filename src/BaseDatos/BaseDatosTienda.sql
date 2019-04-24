/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  josemanuel
 * Created: 22-abr-2019
 */

DROP TABLE Categorias;
DROP TABLE Productos;

CREATE TABLE Productos(
    IdProducto INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, -- Id autonumérico
    Nombre VARCHAR (30) NOT NULL,
    Estado BOOLEAN,
    Descripcion VARCHAR (500),
    Precio DECIMAL (10,2),
    Foto VARCHAR (30),
    FechaSalida DATE,
    CONSTRAINT IdProducto_Productos_PK PRIMARY KEY (IdProducto)
);



CREATE TABLE Categorias(
    IdCategoria INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, -- Id autonumérico
    Nombre VARCHAR (30) NOT NULL,
    Icono VARCHAR (30),
    IdProducto INTEGER NOT NULL,
    CONSTRAINT IdCategoria_Categorias_PK PRIMARY KEY (IdCategoria),
    CONSTRAINT IdProducto_Categorias_FK FOREIGN KEY (IdProducto) REFERENCES Productos (IdProducto)
);