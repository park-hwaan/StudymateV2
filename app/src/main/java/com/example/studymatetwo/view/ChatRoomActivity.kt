package com.example.studymatetwo.view

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studymatetwo.ChatMessageAdapter
import com.example.studymatetwo.databinding.ActivityChatRoomBinding
import com.example.studymatetwo.databinding.ActivityHomeBinding
import com.example.studymatetwo.dto.MessageDto
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader

@AndroidEntryPoint
class ChatRoomActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatRoomBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var chatMessageAdapter: ChatMessageAdapter
    private lateinit var userToken : String
    private lateinit var stompClient: ua.naiksoftware.stomp.StompClient
    private val roomId: String by lazy { intent.getStringExtra("roomId").toString() }
    var jsonObject = JSONObject()
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        userToken = sharedPreferences.getString("userToken", "") ?: ""
        name = sharedPreferences.getString("name","") ?: ""

        chatMessageAdapter = ChatMessageAdapter(name)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = chatMessageAdapter

        connectToStomp(userToken, roomId)

        binding.sendBtn.setOnClickListener {
            sendMessage(roomId)
            val sampleMessage = MessageDto(
                sender = name,
                message = binding.editContent.text.toString(),
                chatRoomId = roomId // 채팅방 ID를 추가
            )
            Log.d("ChatRoomActivity","$sampleMessage")
            chatMessageAdapter.addMessage(sampleMessage)
            binding.editContent.text = null
        }

    }

    @SuppressLint("CheckResult")
    private fun connectToStomp(userToken: String, roomId: String) {
        val url = "ws://10.0.2.2:8080/ws/chat"
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

        val headerList = arrayListOf<StompHeader>()
        headerList.add(StompHeader("Authorization", "Bearer $userToken"))
        stompClient.connect(headerList)

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> Log.i("StompClient", "Stomp connection opened")
                LifecycleEvent.Type.CLOSED -> {
                    Log.i("CLOSED", "Stomp connection closed")
                    reconnect(userToken, roomId)
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "Stomp connection error")
                    Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
                    reconnect(userToken, roomId)
                }
                else -> Log.i("ELSE", lifecycleEvent.message)
            }
        }

        stompClient.topic("/sub/chat/room/${roomId}").subscribe { topicMessage ->
            Log.i("ChatRoomActivity", topicMessage.payload)
            try {
                val messageData = JSONObject(topicMessage.payload)
                val sender = messageData.getString("sender")
                val content = messageData.getString("message")
                if (sender != "박환") {
                    val messageModel = MessageDto(sender, content)
                    runOnUiThread {
                        chatMessageAdapter.addMessage(messageModel)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    private fun reconnect(userToken: String, roomId: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            connectToStomp(userToken, roomId)
        }, 5000)
    }

    @SuppressLint("CheckResult")
    private fun sendMessage(roomId: String) {
        if (!stompClient.isConnected) {
            Log.e("SendMessage", "STOMP client is not connected!")
            return
        }

        try {
            jsonObject.put("type", "TALK")
            jsonObject.put("roomId", roomId)
            jsonObject.put("sender", name)
            jsonObject.put("message", binding.editContent.text.toString())
            Log.d("send", jsonObject.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        stompClient.send("/pub/chat/message", jsonObject.toString())
            .subscribe({
                Log.d("SendMessage", "Message sent successfully")
            }, { error ->
                Log.e("SendMessage", "Error sending message", error)
            })
    }

}