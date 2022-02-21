package com.example.posapp

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

class PlayMusicInBGBroadCastReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
        if(p1.action == MEDIAPLAYER_PLAY){
            Intent(p0,PlayMusicInBGService::class.java).also { intent->
                p0.startService(intent)
            }


        }else if(p1.action == MEDIAPLAYER_STOP){
            Intent(p0,PlayMusicInBGService::class.java).also { intent->
                p0.stopService(intent)
            }

        }
    }
}