import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.contactapp.Model

class Database(context: Context) : SQLiteOpenHelper(context, Database.DB_NAME, null, Database.DB_VERSION) {

    //creating the database table with desired columns
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $FIRSTNAME TEXT, $LASTNAME TEXT, $CONTACT TEXT, $EMAIL TEXT, $ADDRESS TEXT ,$PROFILE_IMAGE BLOB);"
        db.execSQL(CREATE_TABLE)
    }

    //checking if the database exits or not
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    //creating all the required variables
    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "ContactDB"
        private const val TABLE_NAME = "Contacts"
        private const val ID = "Id"
        private const val FIRSTNAME = "first_name"
        private const val LASTNAME = "last_name"
        private const val CONTACT = "contact"
        private const val EMAIL = "email"
        private const val ADDRESS = "address"
        private const val PROFILE_IMAGE = "img"
    }
}