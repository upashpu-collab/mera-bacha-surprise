package com.example

import android.content.Context
import android.content.SharedPreferences

/**
 * Handles persistent storage of customized details:
 * girlfriend's name, love letter message, and user-picked photo URIs.
 */
class BirthdayPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("birthday_surprise_prefs", Context.MODE_PRIVATE)

    var girlfriendName: String
        get() = prefs.getString("girlfriend_name", BirthdayConfig.GIRLFRIEND_NAME) ?: BirthdayConfig.GIRLFRIEND_NAME
        set(value) = prefs.edit().putString("girlfriend_name", value).apply()

    var senderName: String
        get() = prefs.getString("sender_name", BirthdayConfig.YOUR_NAME) ?: BirthdayConfig.YOUR_NAME
        set(value) = prefs.edit().putString("sender_name", value).apply()

    var letterBody: String
        get() = prefs.getString("letter_body", BirthdayConfig.LETTER_BODY) ?: BirthdayConfig.LETTER_BODY
        set(value) = prefs.edit().putString("letter_body", value).apply()

    fun getPhotoUri(index: Int): String? {
        return prefs.getString("photo_uri_$index", null)
    }

    fun setPhotoUri(index: Int, uriString: String?) {
        if (uriString == null) {
            prefs.edit().remove("photo_uri_$index").apply()
        } else {
            prefs.edit().putString("photo_uri_$index", uriString).apply()
        }
    }

    fun getPhotoCaption(index: Int, defaultCaption: String): String {
        return prefs.getString("photo_caption_$index", defaultCaption) ?: defaultCaption
    }

    fun setPhotoCaption(index: Int, caption: String) {
        prefs.edit().putString("photo_caption_$index", caption).apply()
    }

    fun resetToDefaults() {
        prefs.edit().clear().apply()
    }
}
