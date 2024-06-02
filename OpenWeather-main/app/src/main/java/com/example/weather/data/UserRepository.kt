package com.example.weather.data

class UserRepository(
    private val dao: UserDao
) {
    suspend fun insertUser(user: User) = dao.insert(user)

    suspend fun deleteUser(user: User) = dao.delete(user)

    fun getAllUsers() = dao.getAllUsers()
}

