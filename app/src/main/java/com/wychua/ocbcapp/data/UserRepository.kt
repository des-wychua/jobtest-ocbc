package com.wychua.ocbcapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.wychua.ocbcapp.data.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    fun getOrderedUserList(): LiveData<List<User>> {
        return userDao.getOrderedUserList()
    }

    fun getUserByUserId(userId: String): LiveData<List<User>> {
        return userDao.getUserByUserId(userId)
    }

    fun getUserByName(name: String): LiveData<List<User>> {
        return userDao.getUserByName(name)
    }

    @Suppress("RedundantSuspendModified")
    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    @Suppress("RedundantSuspendModified")
    @WorkerThread
    suspend fun update(user : User) {
        userDao.update(user)
    }


}