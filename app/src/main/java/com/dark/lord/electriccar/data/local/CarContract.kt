package com.dark.lord.electriccar.data.local

import android.provider.BaseColumns

object CarContract {

    object CarEntry : BaseColumns {
        const val TABLE_NAME = "car"
        const val COLUMN_NAME_PRICE = "preco"
        const val COLUMN_NAME_CAR_ID = "car_id"
        const val COLUMN_NAME_BATTERY = "bateria"
        const val COLUMN_NAME_HORSE_POW = "potencia"
        const val COLUMN_NAME_RECHARGE = "recarga"
        const val COLUMN_NAME_URL_PHOTO = "url_photo"
    }

    const val CAR_TABLE =
        "CREATE TABLE ${CarEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${CarEntry.COLUMN_NAME_CAR_ID} TEXT," +
                "${CarEntry.COLUMN_NAME_PRICE} TEXT," +
                "${CarEntry.COLUMN_NAME_BATTERY} TEXT," +
                "${CarEntry.COLUMN_NAME_HORSE_POW} TEXT," +
                "${CarEntry.COLUMN_NAME_RECHARGE} TEXT," +
                "${CarEntry.COLUMN_NAME_URL_PHOTO} TEXT)"

    const val SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"
}