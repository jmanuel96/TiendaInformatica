/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  josemanuel
 * Created: 22-abr-2019
 */

CREATE TABLE Productos (
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, -- Id autonumérico
    Nombre VARCHAR (30) NOT NULL,
    Estado BOOLEAN,
    DESCRIPCION
);

CREATE TABLE Categorias (
);