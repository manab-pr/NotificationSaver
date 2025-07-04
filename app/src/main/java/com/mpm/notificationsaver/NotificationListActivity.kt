package com.mpm.notificationsaver

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpm.notificationsaver.databinding.ActivityNotificationListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotificationListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationListBinding
    private lateinit var adapter: NotificationAdapter
    private val dao by lazy {
        NotificationDatabase.getInstance(applicationContext).notificationDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        adapter = NotificationAdapter().apply {
            onItemClick = { notification ->
                NotificationDetailBottomSheet
                    .newInstance(notification)
                    .show(supportFragmentManager, "detail_sheet")
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@NotificationListActivity)
            adapter = this@NotificationListActivity.adapter
        }

        // Collect from your Flow<List<NotificationEntity>> 
        lifecycleScope.launch(Dispatchers.IO) {
            dao.getAllNotifications().collect { list ->
                launch(Dispatchers.Main) {
                    adapter.submitList(list)
                    if (list.isEmpty()) {
                        binding.emptyMessageTextView.visibility = View.VISIBLE
                    } else {
                        binding.emptyMessageTextView.visibility = View.GONE
                    }
                }
            }
        }
    }
}
