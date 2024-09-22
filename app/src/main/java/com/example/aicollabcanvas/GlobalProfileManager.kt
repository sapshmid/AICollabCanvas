package com.example.aicollabcanvas

object GlobalProfileManager {
    private var profile: UserProfile? = null

    fun setProfile(userProfile: UserProfile) {
        profile = userProfile
    }

    fun getProfile(): UserProfile? {
        return profile
    }

    fun clearProfile() {
        profile = null
    }
}

data class UserProfile(
    val id: String,
    val name: String,
    val role: String,
    val profilePic: String
) {
    constructor(): this("", "", "", "")
}
