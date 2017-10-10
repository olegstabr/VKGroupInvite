package com.company

import java.awt.Desktop
import java.net.URI


class VK {
    private val APP_ID = 6200317
    private val DISPLAY = "mobile"
    private val REDIRECT_URI = "https://oauth.vk.com/blank.html"
    private val PERMISSIONS = "groups,friends"
    private val API_VERSION = "5.68"

    private var authorizeString = String()

    init {
        authorizeString = "https://oauth.vk.com/authorize?client_id=$APP_ID&" +
                "display=$DISPLAY&redirect_uri=$REDIRECT_URI" +
                "&scope=$PERMISSIONS&response_type=token&v=$API_VERSION"

        Desktop.getDesktop().browse(URI(authorizeString))
    }

}