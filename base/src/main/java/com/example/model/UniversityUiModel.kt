package com.example.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class UniversityUiModel(
    val alpha_two_code: String,
    val country: String,
    val domains: List<String>,
    val name: String,
    val state_province: String?,
    val web_pages: List<String>
) : Parcelable{
    fun getWepPage() = web_pages.getOrNull(0) ?: "N/A"
}