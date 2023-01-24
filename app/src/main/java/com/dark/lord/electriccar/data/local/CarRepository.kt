package com.dark.lord.electriccar.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.dark.lord.electriccar.data.local.CarContract.CarEntry.COLUMN_NAME_BATERIA
import com.dark.lord.electriccar.data.local.CarContract.CarEntry.COLUMN_NAME_POTENCIA
import com.dark.lord.electriccar.data.local.CarContract.CarEntry.COLUMN_NAME_PRECO
import com.dark.lord.electriccar.data.local.CarContract.CarEntry.COLUMN_NAME_RECARGA
import com.dark.lord.electriccar.data.local.CarContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.dark.lord.electriccar.data.local.CarContract.CarEntry
import com.dark.lord.electriccar.data.local.CarContract.CarEntry.COLUMN_NAME_CAR_ID

import com.dark.lord.electriccar.domain.Car

class CarRepository(private val context: Context) {

    fun save(car: Car): Boolean {

        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, car.id)
                put(COLUMN_NAME_PRECO, car.preco)
                put(COLUMN_NAME_POTENCIA, car.potencia)
                put(COLUMN_NAME_BATERIA, car.bateria)
                put(COLUMN_NAME_RECARGA, car.recarga)
                put(COLUMN_NAME_URL_PHOTO, car.urlPhoto)
            }

            val inserted = db?.insert(CarEntry.TABLE_NAME, null, values)

            if (inserted != null) {
                db.close()
                return true
            }

        } catch (ex: Exception) {
            ex.message?.let { errorMessage ->
                Log.e("DATABASE", errorMessage)
            }
        }
        return false
    }

    fun delete(car: Car): Boolean {

        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase
            val _id = arrayOf(car.id.toString())

            val deleted = db?.delete(CarEntry.TABLE_NAME, "car_id=?", _id)

            if (deleted != null) {
                db.close()
                Log.d("DATABASE", "Car deleted -> ID: ${car.id}")
            }

        } catch (ex: Exception) {
            ex.message?.let { errorMessage ->
                Log.e("DATABASE", errorMessage)
            }
        }
        return false
    }

    fun findCarById(id: Int): Car {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase

        val filterValues = arrayOf(id.toString())

        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO
        )

        val filter = "$COLUMN_NAME_CAR_ID = ?"

        val cursor = db.query(
            CarEntry.TABLE_NAME,
            columns,
            filter,
            filterValues,
            null,
            null,
            null
        )

        var itemCar = getEmptyCarObject()
        with(cursor) {
            while (moveToNext()) {

                itemCar = itemCar.copy(
                    id = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID)).toInt(),
                    preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO)),
                    bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA)),
                    potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA)),
                    recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA)),
                    urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO)),
                    isFavorite = true
                )
            }
        }

        cursor.close()
        return itemCar
    }

    fun getAllCars(): List<Car> {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase

        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO
        )

        val cursor = db.query(
            CarEntry.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        val itemCar = mutableListOf<Car>()

        with(cursor) {
            while (moveToNext()) {

                itemCar.add(
                    Car(
                        id = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID)).toInt(),
                        preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO)),
                        bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA)),
                        potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA)),
                        recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA)),
                        urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO)),
                        isFavorite = true
                    )
                )
            }
        }

        cursor.close()
        return itemCar

    }

    fun saveIfUnavailable(car: Car) {
        val mCar = findCarById(car.id)
        if (mCar.id == ID_UNAVAILABLE) {
            save(car)
        }
    }

    companion object {
        const val ID_UNAVAILABLE = 0
    }

    private fun getEmptyCarObject(): Car {
        return Car(
            id = 0,
            preco = "",
            bateria = "",
            potencia = "",
            recarga = "",
            urlPhoto = "",
            isFavorite = false
        )
    }
}