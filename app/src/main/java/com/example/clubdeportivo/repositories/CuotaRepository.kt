package com.example.clubdeportivo.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.clubdeportivo.entities.Cuota
import com.example.clubdeportivo.helpers.DataBaseHelper
import org.threeten.bp.LocalDate

class CuotaRepository(context: Context) {

    private val dbHelper = DataBaseHelper(context)

    fun saveCuota(cuota: Cuota, idSocio: Int?): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("valor_cuota", cuota.valueCuota)
            put("fecha_pago", cuota.payDay.toString())
            put("fecha_vencimiento", cuota.expirationDate.toString())
            put("fecha_prox_vencimiento", cuota.nextExpirationDate.toString())
            put("forma_pago", cuota.paymentMethod)
            put("cantidad_cuotas", cuota.numberCuotas)
            put("estado", cuota.state)
            put("fk_socio", idSocio)
        }
        val result = db.insert("cuotas", null, contentValues)
        db.close()
        return result != -1L // SqlLite devuelve un -1 si falla
    }

    fun existCuotaSocio(id: Int?): Boolean {
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val query = "select count(*) from cuotas C" +
                " inner join socios AS S on C.fk_socio = S.id_socio" +
                " where S.id_socio = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        var exist = false
        if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            exist = count > 0
        }
        cursor.close()
        db.close()
        return exist
    }

    fun findExpirationDate(id: Int?): LocalDate? {
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val query = "SELECT fecha_prox_vencimiento FROM cuotas WHERE fk_socio = ?" +
                " ORDER BY fecha_prox_vencimiento DESC LIMIT 1"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        var expirationDate: LocalDate? = null
        if (cursor.moveToFirst()) {
            val dateString = cursor.getString(0)
            expirationDate = LocalDate.parse(dateString)
        }
        cursor.close()
        db.close()
        return expirationDate
    }
}