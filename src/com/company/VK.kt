package com.company

import java.awt.Desktop
import java.net.URI


class VK {
    val APP_ID = 1111111
    val DISPLAY = "mobile"
    val REDIRECT_URI = "https://oauth.vk.com/blank.html"
    val PERMISSIONS = "groups,friends"
    val API_VERSION = "5.68"

    var authorizeString = String()
    var accessToken = String()

    init {
        authorizeString = "https://oauth.vk.com/authorize?client_id=$APP_ID&" +
                "display=$DISPLAY&redirect_uri=$REDIRECT_URI" +
                "&scope=$PERMISSIONS&response_type=token&v=$API_VERSION"

        Desktop.getDesktop().browse(URI(authorizeString))
    }

}