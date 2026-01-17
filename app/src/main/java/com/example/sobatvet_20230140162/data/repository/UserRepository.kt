package com.example.sobatvet_20230140162.data.repository

import com.example.sobatvet_20230140162.data.local.dao.UserDao
import com.example.sobatvet_20230140162.data.local.entity.UserEntity

class UserRepository(private val userDao: UserDao) {
    suspend fun getUserByEmail(email: String): UserEntity? = userDao.getUserByEmail(email)
    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)
}
