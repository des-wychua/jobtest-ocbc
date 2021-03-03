package com.wychua.ocbcapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wychua.ocbcapp.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [User::class], version = 1, exportSchema = false)
public abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    // callback to clear all user in database and
    // populate database with users
    private class UserDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.i(TAG, "userdatabasecallback called")
            INSTANCE?.let { database ->
                scope.launch {
                    var userDao = database.userDao()
                    populateDatabase(userDao)
                }
            }
        }

        suspend fun populateDatabase (userDao: UserDao) {
            // Delete all user from database
            userDao.deleteAllUsers()
            Log.i(TAG, "deleted all users")

            // Add some users
            var user = User(UUID.randomUUID().toString(), "Alice", 150.0)
            userDao.insert(user)
            Log.i(TAG, "created Alice : $150.00")
            user = User(UUID.randomUUID().toString(), "Bob", 150.0)
            userDao.insert(user)
            Log.i(TAG, "created Bob : $150.00")
        }
    }

    companion object {
        private val TAG = "UserRoomDatabase"

        // Singleton tp prevent multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): UserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    "user_database"
                ).addCallback(UserDatabaseCallback(scope)).build()
                Log.i(TAG, "user_database created")
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}

