package com.cemerlang.ataraxia.data.models.chat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    var id: String = "",
    var name: String = "",
    var information: String = ""
): Parcelable
