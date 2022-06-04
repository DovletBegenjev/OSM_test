package com.example.osmtest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.lifecycle.LiveData
import java.io.FileOutputStream
import java.io.IOException
import java.sql.SQLException

class DataBaseHelper(
    context: Context?,
    name: String? = "DataBase",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 1
) : SQLiteOpenHelper(context, name, factory, version) {
    private val DB_PATH = context?.getDatabasePath(name).toString()
    private lateinit var myDataBase: SQLiteDatabase
    private lateinit var sqLiteOpenHelper: SQLiteOpenHelper
    private val contxt = context
    private val dbName = name

    /** Creates an empty database
     on the system and rewrites it
     with your own database. */
    fun createDataBase() {
        val dbExist = checkDataBase()
        /** By calling this method and
         the empty database will be
         created into the default system
         path of your application
         so we are gonna be able
         to overwrite that database
         with our database.*/
        if (!dbExist) {
            this.writableDatabase
            try {
                copyDataBase()
            } catch (e: IOException) {
                Log.e("SearchApiExample", "Error copying database: $e")
            }
        }
    }

    /** Check if the database already exist
     to avoid re-copying the file each
     time you open the application
     return true if it exists
     false if it doesn't. */
    private fun checkDataBase(): Boolean {
        var checkDB = null as SQLiteDatabase?
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLException) {
            Log.e("SearchApiExample", "Error checking if database exists: $e")
        }
        checkDB?.close()
        return checkDB != null
    }

    /**
     * Copies your database from your
     * local assets-folder to the just
     * created empty database in the
     * system folder, from where it
     * can be accessed and handled.
     * This is done by transferring bytestream.
     **/
    private fun copyDataBase() {
        // Open your local db as the input stream
        val myInput = dbName?.let { contxt?.assets?.open(it) }

        // Path to the just created empty db
        val outFileName = DB_PATH

        // Open the empty db as the output stream
        val myOutput = FileOutputStream(outFileName)

        // transfer bytes from the inputfile to the outputfile
        val buffer = ByteArray(1024)
        var length: Int
        while (myInput!!.read(buffer).also { length = it } > 0) {
            myOutput.write(buffer, 0, length)
        }

        // Close the streams
        myOutput.flush()
        myOutput.close()
        myInput.close()
    }

    fun openDataBase() {
        // Open the database
        myDataBase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY)
    }

    fun getAllBusStops(context: Context): List<String> {
        val sqLiteOpenHelper = DataBaseHelper(context)
        val db = sqLiteOpenHelper.writableDatabase
        val list: ArrayList<String> = ArrayList()

        // Query
        val query = "SELECT * FROM bus_stop"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1))
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun close() {
        super.close()
        // close the database.
        myDataBase.close()
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        // Do nothing
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Do nothing
    }
}