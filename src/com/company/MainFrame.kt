package com.company

import org.apache.commons.io.IOUtils
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.awt.Dimension
import java.awt.Point
import java.io.StringWriter
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

class MainFrame(accessToken: String) : JFrame() {
    private val panel = JPanel()
    private val label = JLabel()
    private var getFriendsQuery = "https://api.vk.com/method/friends.get?uid=36645215&access_token=$accessToken"
    private var getUserInfoQuery = "https://api.vk.com/method/users.get?user_ids=*&fields=photo_big&access_token=$accessToken"


    private val httpClient = HttpClientBuilder.create().build()
    private var httpPost: HttpPost? = null
    private var httpResponse: HttpResponse? = null
    private var httpStatus: Int? = -1

    private val jsonParser = JSONParser()

    init {
        size = Dimension(800, 600)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        contentPane.add(panel)
        isVisible = true

        panel.layout = null
        panel.size = Dimension(800, 600)
        panel.add(label)

        label.text = "DSDSSDSS"
        label.location = Point(10, 10)

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

        print(jsonObject)
    }
}