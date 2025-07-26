package com.mpm.notificationsaver.repository

import com.mpm.notificationsaver.NotificationDao
import com.mpm.notificationsaver.NotificationEntity
import kotlinx.coroutines.flow.Flow

class NotificationRepository(private val notificationDao: NotificationDao) {

    fun getAllNotifications(): Flow<List<NotificationEntity>> = notificationDao.getAllNotifications()

    suspend fun insert(notification: NotificationEntity) {
        notificationDao.insert(notification)
    }

    suspend fun deleteById(id: Long) {
        notificationDao.deleteById(id)
    }
}