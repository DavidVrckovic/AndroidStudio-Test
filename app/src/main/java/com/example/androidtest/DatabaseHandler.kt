package com.example.androidtest

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // DB version (important if any changes are made)
        private const val DATABASE_VERSION = 1

        // DB name
        private const val DATABASE_NAME = "Test DB"

        // DB tables
        private const val TABLE_USERS = "UserTable"
        //private const val TABLE_USERS2 = "SecondUserTable"

        // DB attributes
        private const val KEY_ID = "_id" // PRIMARY KEY
        private const val KEY_NAME = "name"
        //private const val KEY_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create DB table
        val CREATE_TABLE = ("CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")")
        /*val CREATE_TABLE = ("CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")") */
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Insert data into DB
    fun addUsers(emp: ModelClass): Long {
        // Get a writable DB
        val db = this.writableDatabase

        // Put the values (entries) in a container
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, emp.name) // ModelClass Name
        //contentValues.put(KEY_EMAIL, emp.email) // ModelClass Email

        // Inserting User's details using insert query row
        // 2nd argument is String containing nullColumnHack
        val response = db.insert(TABLE_USERS, null, contentValues)

        // Close DB connection and return the statement
        db.close()
        return response
    }

    //Method to read the records from database in form of ArrayList
    @SuppressLint("Range")
    fun viewUsers(): ArrayList<ModelClass> {

        val usersList: ArrayList<ModelClass> = ArrayList<ModelClass>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $TABLE_USERS"

        // Get a readable DB
        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            // Fill the cursor with the rawQuery
            cursor = db.rawQuery(selectQuery, null)
            // If sth goes wrong, return exception
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        //var email: String

        if (cursor.moveToFirst()) {
            do {
                // Store data from DB column
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                //email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))

                val emp = ModelClass(id = id, name = name /*, email = email*/)
                usersList.add(emp)

            } while (cursor.moveToNext())
        }
        return usersList
    }

    // Update DB record
    fun updateUsers(emp: ModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, emp.name) // EmpModelClass Name
        //contentValues.put(KEY_EMAIL, emp.email) // EmpModelClass Email

        // Updating Row by searching the ID
        val response = db.update(TABLE_USERS, contentValues, KEY_ID + "=" + emp.id, null)

        // Closing DB connection
        db.close()
        return response
    }

    // Function to delete record
    fun deleteUsers(emp: ModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id) // EmpModelClass id
        // Deleting Row
        //2nd argument is String containing nullColumnHack
        val response = db.delete(TABLE_USERS, KEY_ID + "=" + emp.id, null)

        // Closing DB connection
        db.close()
        return response
    }
}
