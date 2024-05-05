package com.example.feature.detail

import android.os.Parcelable
import java.io.Serializable

interface RefreshCallback: Serializable {
    fun onRefreshRequested()
}