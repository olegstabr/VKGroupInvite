package com.company

import org.apache.commons.io.IOUtils
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.awt.*
import java.io.StringWriter
import java.net.URL
import javax.swing.*


class MainFrame constructor(accessToken: String) : JFrame() {
    private var panel: JPanel? = null
    private var networkController = NetworkController(accessToken)

    init {
        title = "Друзья"
        size = Dimension(800, 600)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        layout = BorderLayout()
        setLocationRelativeTo(null)
        isVisible = true

        val friends = networkController.getFriends()
        val usersInfo = networkController.getUsersInfo(friends!!)
        addUsers(usersInfo!!)
    }

    private fun addUsers(usersJsonArray: JSONArray) {
        panel = JPanel(GridLayout(usersJsonArray.size / 6, 6))
        panel?.size = Dimension(800, 600)

        add(panel)

        for (obj in usersJsonArray) {
            if (obj is HashMap<*, *>) {
                val label = JLabel()
                val name: String = obj["first_name"].toString() + " " + obj["last_name"].toString()
                label.icon = ImageIcon(URL(obj["photo_50"].toString()), obj["first_name"].toString())
                label.text = name
                label.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                label.addMouseListener(LabelHoverAdapter(networkController.accessToken, obj["uid"].toString()))

                panel?.add(label)
            }
        }
    }
}