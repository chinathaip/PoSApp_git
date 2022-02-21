package com.example.posapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer

import android.os.IBinder
import android.os.PowerManager


class PlayMusicInBGService:Service(){
    private var mediaPlayer:MediaPlayer?=null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer= MediaPlayer.create(this,R.raw.sample)
        mediaPlayer?.apply {
            setWakeMode(applicationContext,PowerManager.PARTIAL_WAKE_LOCK)
            mediaPlayer!!.start()
        }
        PosSettings.statusText="Playingggggg"
        return START_STICKY
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    override fun onDestroy() {
        mediaPlayer?.stop()
        stopSelf()
        PosSettings.statusText="Pauseeeee"

    }


}
