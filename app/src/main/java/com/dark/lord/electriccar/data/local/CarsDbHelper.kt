package com.dark.lord.electriccar.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dark.lord.electriccar.data.local.CarContract.CAR_TABLE
import com.dark.lord.electriccar.data.local.CarContract.SQL_DELETE_ENTRIES


class CarsDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CAR_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Db_Car"
    }
}