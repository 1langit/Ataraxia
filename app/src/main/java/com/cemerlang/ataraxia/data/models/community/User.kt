package com.cemerlang.ataraxia.data.models.community

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val first_name: String,
    val isAnonymous: Boolean,
    val last_name: String?
): Parcelable