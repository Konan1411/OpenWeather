package com.example.weather.data

class UserRepository(
    private val dao: UserDao
) {
    suspend fun insertUser(user: User) = dao.insert(user)

    suspend fun deleteUser(user: User) = dao.delete(user)

    fun getAllUsers() = dao.getAllUsers()

    fun getUser(username: String, password: String) = dao.getUser(username, password)

    suspend fun getUserByUsername(username: String) = dao.getUserByUsername(username)

    suspend fun updatePassword(username: String, password: String) = dao.updatePassword(username,password)
}
