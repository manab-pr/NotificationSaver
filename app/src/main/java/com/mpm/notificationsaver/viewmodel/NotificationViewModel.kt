package com.mpm.notificationsaver.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpm.notificationsaver.NotificationEntity
import com.mpm.notificationsaver.repository.NotificationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NotificationViewModel(private val repository: NotificationRepository) : ViewModel() {

    val notifications: StateFlow<List<NotificationEntity>> = repository.getAllNotifications()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}