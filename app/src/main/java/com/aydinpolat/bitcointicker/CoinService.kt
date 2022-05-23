package com.aydinpolat.bitcointicker

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.datastore.preferences.core.intPreferencesKey
import com.aydinpolat.bitcointicker.common.Constants
import com.aydinpolat.bitcointicker.common.Constants.ACTION_CLOSE
import com.aydinpolat.bitcointicker.common.Constants.CHANNEL_ID
import com.aydinpolat.bitcointicker.common.Constants.NOTIFICATION_INFO
import com.aydinpolat.bitcointicker.common.Constants.REFRESHING
import com.aydinpolat.bitcointicker.common.DataStoreManager
import com.aydinpolat.bitcointicker.common.extentions.secondsToMillis
import com.aydinpolat.bitcointicker.domain.repository.CoinRepository
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class CoinService : Service() {

    @Inject
    lateinit var favoritesRepository: FirebaseRepository

    @Inject
    lateinit var coinDetailRepository: CoinRepository

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private val superVisorJob = SupervisorJob()
    private val mainScope = CoroutineScope(Dispatchers.Main + superVisorJob)

    private var refreshSeconds = 10

    private var _handler: Handler? = null
    private val _runnable: Runnable = object : Runnable {
        override fun run() {
            _handler?.postDelayed(this, refreshSeconds.secondsToMillis())
            getCoins()
        }
    }

    private fun getRefreshSeconds() {
        mainScope.launch {
            dataStoreManager.readValue(intPreferencesKey(Constants.INTERVAL_TIME)) {
                refreshSeconds = this
                setTask()
            }
        }
    }

    override
    fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getRefreshSeconds()
        notificationSetup()
        if (intent != null) {
            val action = intent.action
            if (action != null) {
                when (action) {
                    ACTION_CLOSE -> stopSelf()
                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        superVisorJob.cancel()
        super.onDestroy()
    }

    fun getCoins() {
        mainScope.launch {
            favoritesRepository.getFavoriteCoins { coinList ->
                if (coinList.isNotEmpty()) {
                    coinList.forEach {
                        Log.d("Coin", it.name)
                        GlobalScope.launch(Dispatchers.IO) {
                            refreshCoinDetails(it.id)
                        }
                    }
                }
            }

        }
    }

    private suspend fun refreshCoinDetails(id: String) {
        coinDetailRepository.getCoinById(id)
    }

    private fun setTask() {
        _handler?.removeCallbacks(_runnable)
        _handler = Handler(Looper.getMainLooper())
        _handler?.postDelayed(_runnable, refreshSeconds.secondsToMillis())
    }


    private fun notificationSetup() {
        val notificationIntentClose = Intent(this, CoinService::class.java)
        notificationIntentClose.action = ACTION_CLOSE
        val closeIntent = PendingIntent.getService(
            this,
            1,
            notificationIntentClose,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    NOTIFICATION_INFO,
                    NotificationManager.IMPORTANCE_LOW
                )
            channel.lightColor = Color.YELLOW
            val notificationManager =
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            notificationManager.createNotificationChannel(channel)
            val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)

            val notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_crypto)
                .setContentTitle(REFRESHING)
                .setContentIntent(PendingIntent.getActivity(this, 0, Intent(), 0))
                .setPriority(NotificationManager.IMPORTANCE_LOW)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setChannelId(CHANNEL_ID)
                .addAction(
                    NotificationCompat.Action(
                        R.drawable.ic_close_white,
                        "Stop",
                        closeIntent
                    )
                )
                .build()

            startForeground(1, notification)

        }
    }
}