package com.newscorp.sampleapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import androidx.appcompat.app.AppCompatActivity
import com.newscorp.sampleapp.databinding.ActivityConfigBinding
import com.newscorp.sampleapp.network.utils.NetworkConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Config activity for configurations in mock mode
 */
@AndroidEntryPoint
class ConfigActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreference: SharedPreferences

    private lateinit var binding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSwitchListeners()
    }

    private fun setSwitchListeners() {
        binding.switchMock.isChecked =
            sharedPreference.getBoolean(NetworkConstants.PREF_KEY_MOCK_MODE, false)
        binding.switchMock.setOnCheckedChangeListener { _, isChecked ->
            sharedPreference.edit().apply {
                putBoolean(NetworkConstants.PREF_KEY_MOCK_MODE, isChecked).apply()

                // Kill the app and restart
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = packageManager.getLaunchIntentForPackage(packageName)
                    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    Process.killProcess(Process.myPid())
                }, 1000)

            }
        }
    }


}