package com.mpm.notificationsaver

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mpm.notificationsaver.databinding.BottomSheetNotificationDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationDetailBottomSheet : BottomSheetDialogFragment(R.layout.bottom_sheet_notification_detail) {
  private var _binding: BottomSheetNotificationDetailBinding? = null
  private val binding get() = _binding!!
  private val dao by lazy {
    NotificationDatabase.getInstance(requireContext()).notificationDao()
  }

  private lateinit var notification: NotificationEntity

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    _binding = BottomSheetNotificationDetailBinding.bind(view)
    arguments?.let {
      notification = it.getParcelable(ARG_NOTIFICATION)!!
    }

    // Populate UI
    binding.textViewAppName.text = notification.title
    binding.textViewDetailTimestamp.text = notification.timestampFormatted()
    binding.textViewFullMessage.text = notification.text
    // TODO: load real app icon from packageName if you like

    binding.buttonDismiss.setOnClickListener {
  CoroutineScope(Dispatchers.IO).launch {
    dao.deleteById(notification.id)
  }
  dismiss()
}

    binding.buttonOpenApp.setOnClickListener {
      val launchIntent: Intent? = requireContext()
        .packageManager
        .getLaunchIntentForPackage(notification.packageName)
      launchIntent?.let {
        startActivity(it)
      }
      dismiss()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {
    private const val ARG_NOTIFICATION = "arg_notification"

    fun newInstance(notification: NotificationEntity): NotificationDetailBottomSheet {
      return NotificationDetailBottomSheet().apply {
        arguments = Bundle().apply {
          putParcelable(ARG_NOTIFICATION, notification)
        }
      }
    }
  }
}
