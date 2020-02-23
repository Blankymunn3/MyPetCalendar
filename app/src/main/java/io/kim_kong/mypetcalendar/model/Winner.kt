package io.kim_kong.mypetcalendar.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Winner : Serializable {
    @SerializedName("uid")
    private var uId: String? = null
    @SerializedName("parent")
    var parent: String? = null
    @SerializedName("event")
    var event: String? = null
    @SerializedName("user_id")
    var userId: String? = null
    @SerializedName("user_name")
    var userName: String? = null
    @SerializedName("user_mobile")
    var userMobile: String? = null
    @SerializedName("user_email")
    var userEmail: String? = null
    @SerializedName("event_date")
    var eventDate: String? = null
    @SerializedName("event_person")
    var eventPerson: String? = null
    @SerializedName("contents")
    var contents: String? = null
    @SerializedName("winner")
    var winner: String? = null
    @SerializedName("winner_date")
    var winnerDate: String? = null
    @SerializedName("notification")
    var notification: String? = null
    @SerializedName("user_ip")
    var userIp: String? = null
    @SerializedName("user_agent")
    var userAgent: String? = null
    @SerializedName("created_at")
    var createdAt: String? = null
    @SerializedName("image")
    var imageUrl: String? = null
    @SerializedName("ticket")
    fun getuId(): String? {
        return uId
    }

    fun setuId(uId: String?) {
        this.uId = uId
    }

}
