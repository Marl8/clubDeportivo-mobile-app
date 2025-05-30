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


        db.execSQL("INSERT INTO cuotas (idCuota, valor_cuota, fecha_pago, fecha_vencimiento, " +
                "fecha_prox_vencimiento, forma_pago, cantidad_cuotas , estado , fk_socio) VALUES" +
                "(1,12500,'2025-05-14','2025-05-15','2025-06-15','Efectivo',1,1,1)," +
                "(2,12500,'2025-05-09','2025-05-10','2025-06-10','Efectivo',1,1,2)," +
                "(3,12500,'2025-05-12','2025-05-13','2025-06-13','Efectivo',1,1,3)," +
                "(4,12500,'2025-05-13','2025-05-14','2025-06-14','Efectivo',1,1,4)," +
                "(5,12500,'2025-05-12','2025-05-14','2025-06-14','Tarjeta de Crédito',6,1,5)," +
                "(6,12500,'2025-05-28','2025-05-29','2025-06-29','Efectivo',1,1,6)," +
                "(7,12500,'2025-05-14','2025-05-15','2025-06-15','Efectivo',1,1,7)," +
                "(8,12500,'2025-05-13','2025-05-14','2025-06-14','Tarjeta de Crédito',3,1,8)," +
                "(9,12500,'2025-05-11','2025-05-12','2025-06-12','Efectivo',1,1,9)," +
                "(10,12500,'2025-05-08','2025-05-09','2025-06-09','Efectivo',1,1,10)," +
                "(11,12500,'2025-05-10','2025-05-12','2025-06-12','Efectivo',1,1,11)," +
                "(12,12500, '2025-05-18','2025-05-19','2025-06-19','Efectivo',1,1,12)," +
                "(13,12500,'2025-05-20','2025-05-21','2025-06-21','Efectivo',1,1,13)," +
                "(14,12500,'2025-05-21','2025-05-22','2025-06-22','Efectivo',1,1,14)," +
                "(15,12500,'2025-05-12','2025-05-22','2025-06-22','Efectivo',1,1,14)," +
                "(16,12500,'2025-05-12','2025-05-12','2025-06-12','Efectivo',1,1,15)," +
                "(17,12500,'2025-05-22','2025-05-23','2025-06-23','Efectivo',1,1,16)," +
                "(18,12500,'2025-05-23','2025-05-24','2025-06-24','Efectivo',1,1,17)," +
                "(19,12500,'2025-05-24','2025-05-25','2025-06-25','Efectivo',1,1,18)," +
                "(20,12500,'2025-05-25','2025-05-26','2025-06-26','Tarjeta de Crédito',6,1,1)," +
                "(21,12500,'2025-05-12','2025-05-04','2025-06-04','Tarjeta de Crédito',3,1,19)," +
                "(22,12500,'2025-05-13','2025-05-13','2025-06-13','Efectivo',1,1,20)," +
                "(23,12500,'2025-05-13','2025-05-13','2025-06-13','Efectivo',1,1,21)," +
                "(24,12500,'2025-05-13','2025-05-13','2025-06-13','Efectivo',1,1,22)," +
                "(25,12500,'2025-05-14','2025-05-04','2025-06-04','Tarjeta de Crédito',6,1,23)," +
                "(26,12500,'2025-05-22','2025-05-23','2025-06-23','Efectivo',1,1,24)," +
                "(27,12500,'2025-05-23','2025-05-24','2025-06-24','Efectivo',1,1,25);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS roles")
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS socios")
        db.execSQL("DROP TABLE IF EXISTS cuotas")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Elimina todas las tablas y vuelve a crear
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS roles")
        db.execSQL("DROP TABLE IF EXISTS socios")
        db.execSQL("DROP TABLE IF EXISTS cuotas")
        onCreate(db)
    }
}