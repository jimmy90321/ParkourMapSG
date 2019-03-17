package jimmyliao.com.parkourmapsg.LocalDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import jimmyliao.com.parkourmapsg.Module.Spot

class SpotDAO(context: Context) {

    companion object {

        const val KEY_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"

        // create new table
        fun createTable(TABLE_NAME: String): String {

            return "CREATE TABLE $TABLE_NAME " +
                    "($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME TEXT NOT NULL, " +
                    "$COLUMN_DESCRIPTION TEXT NOT NULL, " +
                    "$COLUMN_LATITUDE REAL NOT NULL, " +
                    "$COLUMN_LONGITUDE REAL NOT NULL)"
        }
    }

    private val db: SQLiteDatabase = SQLiteHandler.getDatabase(context)

    fun getAll(tableName: String): MutableList<Spot> {

        val result = mutableListOf<Spot>()
        val cursor = db.query(tableName, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor))
        }

        cursor.close()
        return result
    }

    fun count(tableName: String): Int {
        var result = 0
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $tableName", null)

        if (cursor.moveToNext()) {
            result = cursor.getInt(0)
        }

        cursor.close()
        return result
    }

    fun close() {
        db.close()
    }

    fun insert(tableName: String, spot: Spot): Spot {
        val cv = ContentValues()

        itemToContentValues(spot, cv)

        val id = db.insert(tableName, null, cv)

        spot.id = id
        return spot
    }

    fun update(tableName: String, spot: Spot): Boolean {
        val cv = ContentValues()

        itemToContentValues(spot, cv)

        val where = "$KEY_ID=${spot.id}"

        return db.update(tableName, cv, where, null) > 0
    }

    private fun itemToContentValues(spot: Spot, cv: ContentValues) {
        cv.put(COLUMN_NAME, spot.name)
        cv.put(COLUMN_DESCRIPTION, spot.description)
        cv.put(COLUMN_LATITUDE, spot.latitude)
        cv.put(COLUMN_LONGITUDE, spot.longitude)
    }

    fun delete(tableName: String, id: Long): Boolean {
        val where = "$KEY_ID=$id"

        return db.delete(tableName, where, null) > 0
    }

    operator fun get(tableName: String, id: Long): Spot? {
        var spot: Spot? = null

        val where = "$KEY_ID=$id"

        val result = db.query(tableName, null, where, null, null, null, null)

        if (result.moveToFirst()) {
            spot = getRecord(result)
        }

        result.close()
        return spot
    }

    private fun getRecord(cursor: Cursor): Spot =
        Spot(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getDouble(4))
}