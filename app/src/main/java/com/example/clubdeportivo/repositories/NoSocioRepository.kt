package com.example.clubdeportivo.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivo.entities.NoSocio
import com.example.clubdeportivo.entities.dto.NoSocioEnabledDto
import com.example.clubdeportivo.helpers.DataBaseHelper
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class NoSocioRepository(context: Context) {

    private val dbHelper = DataBaseHelper(context)

    fun saveNoSocio(noSocio: NoSocio): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("nombre", noSocio.name)
            put("apellido", noSocio.lastName)
            put("dni", noSocio.dni)
            put("email", noSocio.email)
            put("telefono", noSocio.phone)
            put("apto_fisico", noSocio.aptoFisico)
        }
        val result = db.insert("noSocios", null, contentValues)
        db.close()
        return result != -1L
    }

    fun existNoSocio(dni: String): Boolean {
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val query = "SELECT * FROM noSocios WHERE dni = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))

        val exist = cursor.count > 0
        cursor.close()
        db.close()
        return exist
    }

    fun findNoSocioByDni(dni: String): NoSocio? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val query: String = "SELECT * " +
                "FROM noSocios WHERE dni = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))

        var noSocio: NoSocio? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_noSocio"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow("telefono"))
            val dniNoSocio = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
            val aptoFisico = cursor.getInt(cursor.getColumnIndexOrThrow("apto_fisico")) != 0

            noSocio = NoSocio(id, aptoFisico, name, lastName, dniNoSocio, email, phone)
        }
        cursor.close()
        return noSocio
    }

    fun listNoSocioEnabled(date: LocalDate): MutableList<NoSocioEnabledDto> {
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val query: String = "select NS.id_noSocio, NS.nombre As nombre_nosocio, NS.apellido, NS.dni, " +
                "A.nombre As nombre_act, AN.dia_habilitado \n" +
                "from noSocios AS NS \n" +
                "inner join act_nosocios AS AN on NS.id_noSocio = AN.id_noSocio \n" +
                "inner join actividades AS A on AN.id_act = A.id_actividad \n" +
                "where date(AN.dia_habilitado) = ? "

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = date.format(formatter)

        val cursor = db.rawQuery(query, arrayOf(formattedDate))

        val noSocios = mutableListOf<NoSocioEnabledDto>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_noSocio"))
                val nameNoSocio = cursor.getString(cursor.getColumnIndexOrThrow("nombre_nosocio"))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
                val dniNoSocio = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
                val nameAct = cursor.getString(cursor.getColumnIndexOrThrow("nombre_act"))
                val enabledDayStr = cursor.getString(cursor.getColumnIndexOrThrow("dia_habilitado"))
                val enabledDay = LocalDate.parse(enabledDayStr, formatter)

                val noSocio = NoSocioEnabledDto(id, nameNoSocio, lastName, dniNoSocio, nameAct, enabledDay)
                noSocios.add(noSocio)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return noSocios
    }
}