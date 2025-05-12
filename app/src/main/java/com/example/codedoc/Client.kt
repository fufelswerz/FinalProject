import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class Client(private val serverUrl: String = "http://10.0.2.2:5000") {

    private var socket: Socket? = null
    var server = serverUrl
    var responseListener: ((Int, String) -> Unit)? = null

    fun connect() {
        try {
            val options = IO.Options()
            options.reconnection = true
            socket = IO.socket(server, options)

            socket?.on("response") { args ->
                val response = args[0] as JSONObject
                val status = response.getInt("status")
                val message = response.getString("message")
                responseListener?.invoke(status, message)
            }
            socket?.connect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendUserData(email: String, login: String, password: String) {
        val userData = JSONObject().apply {
            put("email", email)
            put("login", login)
            put("password", password)
        }
        socket?.emit("register", userData)
    }

    fun sendAuthorizationData(login: String, password: String) {
        val userData = JSONObject().apply {
            put("login", login)
            put("password", password)
        }
        socket?.emit("Authorization", userData)
    }

    fun disconnect() {
        socket?.disconnect()
        socket = null
    }

}
fun create_client(): Client{
    val client = Client()
    client.server = "https://ProjectAP.onrender.com"
    client.connect()
    return client
}
