package com.example.clubdeportivo.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/***
 * Login
 *
 * Rol Administrador:
 *
 * username: prueba
 * password: 123
 *
 * Rol Empleado
 *
 * username: fabi
 * password: 12345
 */

class DataBaseHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "proyecto_club_deportivo"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {

        // Tabla Rol
        db.execSQL("create table roles(" +
                "id_rol Integer PRIMARY KEY AUTOINCREMENT, " +
                "Nombre varchar(30));")

        // Tabla Usuarios
        db.execSQL("create table usuarios (" +
                "id Integer PRIMARY KEY AUTOINCREMENT," +
                "nombre Varchar (45)," +
                "apellido Varchar (55)," +
                "username Varchar (30)," +
                "password Varchar (64)," +
                "dni Varchar (8)," +
                "email Varchar (45)," +
                "telefono Varchar (15)," +
                "fk_rol int,\n" +
                " constraint fk_usuario foreign key(fk_rol) references roles(id_rol));")
        
        // Datos de prueba
        db.execSQL("insert into roles values" +
                "(1,'Administrador')," +
                "(2, 'Empleado');")

        db.execSQL("insert into usuarios values" +
                "(1, 'Juan', 'Perez', 'prueba', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '31526785', 'juanp@gmail.com', '11526789254', 1)," +
                "(2, 'Fabiana', 'Altamirano', 'fabi', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '37506998', 'fabial@gmail.com', '1152589163', 2);")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS roles")
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Elimina todas las tablas y vuelve a crear
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS roles")
        onCreate(db)
    }
}