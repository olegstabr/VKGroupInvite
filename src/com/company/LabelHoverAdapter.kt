package com.company

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.awt.Color
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.StringWriter
import javax.swing.JLabel
import javax.swing.JOptionPane

class LabelHoverAdapter(accessToken: String, userId: String) : MouseAdapter() {
    private var publicId = 80923473
    private var groupInviteQuery = "https://api.vk.com/method/groups.invite?group_id=$publicId&user_id=$userId&access_token=$accessToken"

    private val httpClient = HttpClientBuilder.create().build()
    private val jsonParser = JSONParser()

    override fun mouseEntered(e: MouseEvent) {
        val component = e.component
        if (component is JLabel) {
            component.foreground = Color(255, 127, 80)
        }
    }

    override fun mouseExited(e: MouseEvent) {
        val component = e.component
        if (component is JLabel) {
            component.foreground = Color.BLACK
        }
    }

    override fun mouseClicked(e: MouseEvent) {
        val component = e.component
        if (component is JLabel) {

        }
    }

    override fun mousePressed(e: MouseEvent) {
        val optionPane: JOptionPane
        val content = StringWriter()
        val component = e.component
        if (component is JLabel) {
            val httpPost = HttpPost(groupInviteQuery)
            val httpResponse = httpClient?.execute(httpPost)
            val httpStatus = httpResponse?.statusLine?.statusCode

            if (httpStatus != 200) {
                return
            }

            IOUtils.copy(httpResponse?.entity?.content, content)

            val jsonObject = jsonParser.parse(content.toString()) as JSONObject
            if (jsonObject["error"] != null) {
                optionPane = JOptionPane("Приглашение отправлено", JOptionPane.INFORMATION_MESSAGE)
                optionPane.createDialog("Внимание").isVisible = true
            }
            else {

                optionPane = JOptionPane("Произошла ошибка при отправке", JOptionPane.ERROR_MESSAGE)
                optionPane.createDialog("Внимание").isVisible = true
            }
        }
    }
}