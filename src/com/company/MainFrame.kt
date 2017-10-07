package com.company

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.awt.Dimension
import java.io.StringWriter
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

class MainFrame(accessToken: String) : JFrame() {
    val panel = JPanel()
    val label = JLabel()
    var query = "https://api.vk.com/method/friends.get?uid=36645215&access_token=$accessToken"

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

        executeQuery()
    }

    fun executeQuery() {
        val httpClient = HttpClientBuilder.create().build()
        val httpPost = HttpPost(query)
        val httpResponse = httpClient.execute(httpPost)
        val jsonParser = JSONParser()

        val content = StringWriter()
        IOUtils.copy(httpResponse.entity.content, content);

        val jsonObject = jsonParser.parse(content.toString()) as JSONObject
        print(jsonObject)
    }
}