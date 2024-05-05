package com.example.task.ui.contract

import android.os.Parcelable
import java.io.Serializable

interface RefreshCallback: Serializable {
    fun onRefreshRequested()
}