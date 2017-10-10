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


class MainFrame(accessToken: String) : JFrame() {
    private var panel: JPanel? = null
    private var getFriendsQuery = "https://api.vk.com/method/friends.get?access_token=$accessToken"
    private var getUserInfoQuery = "https://api.vk.com/method/users.get?user_ids=*&fields=photo_50&access_token=$accessToken"


    private val httpClient = HttpClientBuilder.create().build()
    private var httpPost: HttpPost? = null
    private var httpResponse: HttpResponse? = null
    private var httpStatus: Int? = -1

    private val jsonParser = JSONParser()

    init {
        title = "Друзья"
        size = Dimension(800, 600)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        layout = BorderLayout()
        setLocationRelativeTo(null)
        isVisible = true

        executeQuery()
    }

    private fun executeQuery() {
        val content = StringWriter()

        httpPost = HttpPost(getFriendsQuery)
        httpResponse = httpClient.execute(httpPost)
        httpStatus = httpResponse?.statusLine?.statusCode

        if (httpStatus != 200) {
            return
        }

        IOUtils.copy(httpResponse?.entity?.content, content)

        val jsonObject = jsonParser.parse(content.toString()) as JSONObject
        val jsonArray = jsonObject["response"] as JSONArray

        getUsersInfo(jsonArray)
    }

    private fun getUsersInfo(jsonArray: JSONArray) {
        val content = StringWriter()
        val userIds = jsonArray.toString().replace("[", "").replace("]", "")
        getUserInfoQuery = getUserInfoQuery.replace("*", userIds)

        httpPost = HttpPost(getUserInfoQuery)
        httpResponse = httpClient?.execute(httpPost)
        httpStatus = httpResponse?.statusLine?.statusCode

        if (httpStatus != 200) {
            return
        }

        IOUtils.copy(httpResponse?.entity?.content, content)

        val jsonObject = jsonParser.parse(content.toString()) as JSONObject
        val jsonArray = jsonObject["response"] as JSONArray

        panel = JPanel(GridLayout(jsonArray.size / 6, 6))
        panel?.size = Dimension(800, 600)

        add(panel)

        for (obj in jsonArray) {
            if (obj is HashMap<*, *>) {
                val label = JLabel()
                val name: String = obj["first_name"].toString() + " " + obj["last_name"].toString()
                label.icon = ImageIcon(URL(obj["photo_50"].toString()), obj["first_name"].toString())
                label.text = name
                label.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                label.addMouseListener(LabelHoverAdapter())

                panel?.add(label)
            }
        }
    }
}