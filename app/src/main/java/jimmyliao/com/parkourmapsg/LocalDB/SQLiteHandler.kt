package jimmyliao.com.parkourmapsg.LocalDB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import jimmyliao.com.parkourmapsg.Handler.LogHandler as Logger

class SQLiteHandler(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    private val tag = "SQLiteHandler"
    private val tableName = listOf("Central", "East", "North", "NorthEast", "West")

    override fun onCreate(db: SQLiteDatabase?) {
        for (name in tableName) {
            db?.execSQL(SpotDAO.createTable(name))
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // delete existed table
        for (name in tableName) {
            db?.execSQL("DROP TABLE IF EXISTS $name")
        }

        // create new table
        onCreate(db)
        Logger.Info(tag, "delete table version:$oldVersion, create new table version:$newVersion success")
    }

    companion object {
        private const val DATABASE_NAME = "Spot.db"
        private const val VERSION = 1

        fun getDatabase(context: Context): SQLiteDatabase {
            return SQLiteHandler(
                context,
                DATABASE_NAME,
                null,
                VERSION
            ).writableDatabase
        }
    }
}