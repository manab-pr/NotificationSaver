package com.mpm.notificationsaver

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity(tableName = "notifications")
data class NotificationEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0L,
  val packageName: String,
  val title: String,
  val text: String,
  val timestamp: Long
) : Parcelable {
  fun timestampFormatted(): String {
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
  }
}
