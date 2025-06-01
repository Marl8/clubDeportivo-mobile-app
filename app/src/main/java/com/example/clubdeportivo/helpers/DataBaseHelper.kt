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
 *
 * password: 123
 *
 * Rol Empleado:
 *
 * username: fabi
 *
 * password: 12345
 */

class DataBaseHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val appContext = context

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
                "fk_rol int," +
                " constraint fk_usuario foreign key(fk_rol) references roles(id_rol));")

        // Tabla Socios
        db.execSQL("create table socios (" +
                "id_socio Integer PRIMARY KEY AUTOINCREMENT," +
                "nombre Varchar (45)," +
                "apellido Varchar (55)," +
                "dni Varchar (8)," +
                "email Varchar (45)," +
                "telefono Varchar (15)," +
                "apto_fisico tinyint," +
                "estado tinyint);")

        // Tabla No Socio
        db.execSQL("create table noSocios (" +
                "id_noSocio Integer PRIMARY KEY AUTOINCREMENT," +
                "nombre Varchar (45)," +
                "apellido Varchar (55)," +
                "dni Varchar (8)," +
                "email Varchar (45)," +
                "telefono Varchar (15)," +
                "apto_fisico tinyint);")

        // Tabla Cuotas
        db.execSQL("create table cuotas (" +
                "idCuota Integer PRIMARY KEY AUTOINCREMENT," +
                "valor_cuota double," +
                "fecha_pago date," +
                "fecha_vencimiento date," +
                "fecha_prox_vencimiento date," +
                "forma_pago Varchar (20)," +
                "cantidad_cuotas int," +
                "estado tinyint," +
                "fk_socio int," +
                " constraint fk_socio_cuota foreign key(fk_socio) references socios(id_socio)" +
                ");")

        // Tabla Actividades
        db.execSQL("create table actividades (" +
                "id_actividad Integer PRIMARY KEY AUTOINCREMENT," +
                "nombre Varchar (55)," +
                "valor double," +
                "max_cupo_socio int," +
                "max_cupo_no_socio int," +
                "cupo_socio_disponible int," +
                "cupo_no_socio_disponible int);")

        // Tabla Act_Socios
        db.execSQL("create table act_socios(" +
                "id_act int," +
                "id_socio int," +
                "estado tinyint," +
                "constraint pk_act_Socio primary key (id_act, id_Socio)," +
                "constraint fk_act_socios_act foreign key (id_act) references actividades (id_actividad)," +
                "constraint fk_act_socios foreign key (id_socio) references socios (id_socio)" +
                ");")

        // Tabla Act_NoSocios
        db.execSQL("create table act_nosocios(" +
                "id_act int," +
                "id_noSocio int," +
                "dia_habilitado date," +
                "monto_pagado double," +
                "constraint pk_act_noSocio primary key (id_act, id_noSocio)," +
                "constraint fk_act_noSocios_A foreign key (id_act) references actividades (id_actividad)," +
                "constraint fk_act_noSocios_NS foreign key (id_noSocio) references noSocios (id_noSocio)" +
                ");")

        executeScriptSQL(db, "datos_prueba.sql")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS roles")
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS socios")
        db.execSQL("DROP TABLE IF EXISTS noSocios")
        db.execSQL("DROP TABLE IF EXISTS cuotas")
        db.execSQL("DROP TABLE IF EXISTS actividades")
        db.execSQL("DROP TABLE IF EXISTS act_socios")
        db.execSQL("DROP TABLE IF EXISTS act_nosocios")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Elimina todas las tablas y vuelve a crear
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS roles")
        db.execSQL("DROP TABLE IF EXISTS socios")
        db.execSQL("DROP TABLE IF EXISTS noSocios")
        db.execSQL("DROP TABLE IF EXISTS cuotas")
        db.execSQL("DROP TABLE IF EXISTS actividades")
        db.execSQL("DROP TABLE IF EXISTS act_socios")
        db.execSQL("DROP TABLE IF EXISTS act_nosocios")
        onCreate(db)
    }

    private fun executeScriptSQL(db: SQLiteDatabase, file: String) {
        try {
            appContext.assets.open(file).use { inputStream ->
                inputStream.bufferedReader().use { lector ->
                    val sqlComplete = lector.readText()
                    val comandos = sqlComplete.split(";")

                    for (comando in comandos) {
                        val sql = comando.trim()
                        if (sql.isNotEmpty()) {
                            db.execSQL(sql)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}