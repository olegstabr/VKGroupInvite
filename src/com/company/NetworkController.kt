package com.company

import org.apache.commons.io.IOUtils
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.StringWriter

class NetworkController constructor(accessToken: String) {
    var accessToken: String = String()

    private var publicId = 80923473
    private var getFriendsQuery = "https://api.vk.com/method/friends.get?access_token=$accessToken"
    private var getUserInfoQuery = "https://api.vk.com/method/users.get?user_ids=*&fields=photo_50&access_token=$accessToken"
    private var groupInviteQuery = "https://api.vk.com/method/groups.invite?group_id=$publicId&user_id=*&access_token=$accessToken"


    private val httpClient = HttpClientBuilder.create().build()
    private var httpPost: HttpPost? = null
    private var httpResponse: HttpResponse? = null
    private var httpStatus: Int? = -1

    private val jsonParser = JSONParser()

    init {
        this.accessToken = accessToken
    }

    fun getFriends(): JSONArray? {
        val content = StringWriter()

        httpPost = HttpPost(getFriendsQuery)
        httpResponse = httpClient.execute(httpPost)
        httpStatus = httpResponse?.statusLine?.statusCode

        if (httpStatus != 200) {
            return null
        }

        IOUtils.copy(httpResponse?.entity?.content, content)

        val jsonObject = jsonParser.parse(content.toString()) as JSONObject
        val jsonArray = jsonObject["response"] as JSONArray

        return jsonArray
    }

    fun getUsersInfo(users: JSONArray): JSONArray? {
        val content = StringWriter()
        val userIds = users.toString().replace("[", "").replace("]", "")
        getUserInfoQuery = getUserInfoQuery.replace("*", userIds)

        httpPost = HttpPost(getUserInfoQuery)
        httpResponse = httpClient?.execute(httpPost)
        httpStatus = httpResponse?.statusLine?.statusCode

        if (httpStatus != 200) {
            return null
        }

        IOUtils.copy(httpResponse?.entity?.content, content)

        val jsonObject = jsonParser.parse(content.toString()) as JSONObject
        val jsonArray = jsonObject["response"] as JSONArray

        return jsonArray
    }
}