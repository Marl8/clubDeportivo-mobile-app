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

        // Datos de prueba
        db.execSQL("insert into roles values" +
                "(1,'Administrador')," +
                "(2, 'Empleado');")

        db.execSQL("insert into usuarios values" +
                "(1, 'Juan', 'Perez', 'prueba', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '31526785', 'juanp@gmail.com', '11526789254', 1)," +
                "(2, 'Fabiana', 'Altamirano', 'fabi', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '37506998', 'fabial@gmail.com', '1152589163', 2);")

        db.execSQL("INSERT INTO socios (id_socio, nombre, apellido, dni, email, telefono, apto_fisico, estado) VALUES " +
                "(1,'Danilo','Yatra','38523664','daniloyatra@gmail.com','114998526',1,1)," +
                "(2,'Sebastian','Gonzalez','38515806','sebagonzalez@gmail.com','114990527',1,1)," +
                "(3,'Geronimo','Rulli','37885602','gero.rulli@gmail.com','114850360',1,1)," +
                "(4,'Bautista','Arce','41050223','bautista.arce@gmail.com','2214987008',1,1)," +
                "(5,'Alba','Danubio','29699822','albadanu@gmail.com','2213569993',1,1)," +
                "(6,'Homero','Jara','18228858','homerojara@gmail.com','2214778906',1,1)," +
                "(7,'Helena','Rosso','20414771','helenarosso@gmail.com','2214778550',1,1)," +
                "(8,'Prisila','Oca','34746667','prisilaoca@gmail.com','2253699858',1,1)," +
                "(9,'Lorenzo','Sastre','36652023','lorenzosas@gmail.com','116989741',1,1)," +
                "(10,'Ricardo','Duran','22599818','ricDuran@gmail.com','114225362',1,1)," +
                "(11,'Santino','Lugo','31447711','santilugo@gmail.com','112441153',1,1)," +
                "(12,'sandro','Idra','20266980','sandroidra@gmail.com','221400503',1,1)," +
                "(13,'Ivan','Sorredo','35606331','ivanso@gmail.com','221447850',1,1)," +
                "(14,'Ivana','Ramos','29332264','ivanaRamos@gmail.com','112036580',1,1)," +
                "(15,'Ramon','Corso','21005587','ramonco@gmail.com','2212400515',1,1)," +
                "(16,'Favio','Flandes','26885470','favioflan@gmail.com','113692315',1,1)," +
                "(17,'Alfredo','Tapia','32335608','alfredTapia@gmail.com','221369875',1,1)," +
                "(18,'Cielo','Flama','34789103','cielofla@gmail.com','2213569006',1,1)," +
                "(19,'Helena','Jordan','19520202','helenajor@gmail.com','110253657',1,1)," +
                "(20,'Victor','Thorton','29889523','victort@gmail.com','2214782360',1,1)," +
                "(21,'Julian','Prodan','36201487','juliprodan@gmail.com','113204726',1,1)," +
                "(22,'Vilma','Retegi','28498705','vilmarete@gmail.com','2216303324',1,1)," +
                "(23,'Sonia','Lemos','36574198','sonialemos@gma.com','2263633188',1,1)," +
                "(24,'Mariana','Esteche','40230156','marianeste@gmail.com','1123005403',1,1)," +
                "(25,'Fabiana','Nandes','38717119','fabinandes@gmail.com','116990807',1,1);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS roles")
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS socios")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Elimina todas las tablas y vuelve a crear
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS roles")
        db.execSQL("DROP TABLE IF EXISTS socios")
        onCreate(db)
    }
}