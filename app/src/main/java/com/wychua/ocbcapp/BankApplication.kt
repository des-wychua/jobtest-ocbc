package com.wychua.ocbcapp

import android.app.Application
import com.wychua.ocbcapp.data.UserRepository
import com.wychua.ocbcapp.data.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BankApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { UserRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { UserRepository(database.userDao()) }
}