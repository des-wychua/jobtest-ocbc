package com.wychua.ocbcapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wychua.ocbcapp.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // ORDER user list by name alphabetically
    @Query("SELECT * FROM user_table ORDER BY name ASC")
    fun getOrderedUserList(): LiveData<List<User>>

    // GET user from database by userID
    @Query("SELECT * FROM user_table WHERE userId LIKE :userId")
    fun getUserByUserId(userId: String): LiveData<List<User>>

    // GET user from database by name
    @Query("SELECT * FROM user_table WHERE name LIKE :name")
    fun getUserByName(name: String): LiveData<List<User>>

    // INSERT new user into database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    // UPDATE user balance in database
    @Update
    suspend fun update(user : User)

    // DELETE all user from database
    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

}