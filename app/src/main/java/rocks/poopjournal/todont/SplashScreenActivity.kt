package rocks.poopjournal.todont

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rocks.poopjournal.todont.databinding.ActivitySplashScreenBinding
import rocks.poopjournal.todont.utils.Constants
import rocks.poopjournal.todont.utils.SharedPrefUtils
import rocks.poopjournal.todont.utils.ThemeMode

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var sharedPrefUtils: SharedPrefUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use viewBinding to inflate the layout
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPrefUtils
        sharedPrefUtils = SharedPrefUtils(this)

        // Retrieve first-time check and apply theme if necessary
        val isFirstTime = sharedPrefUtils.getString(SharedPrefUtils.KEY_FIRST_TIME, Constants.NO)

        setTheme()

        // Check status using coroutines
        checkStatus(isFirstTime)
    }


    private fun checkStatus(isFirstTime: String?) {
        MainScope().launch {
            delay(2000) // 2-second delay
            val nextActivity = if (isFirstTime == Constants.NO) {
                MainActivity::class.java
            } else {
                OnBoardingActivity::class.java
            }

            startActivity(Intent(this@SplashScreenActivity, nextActivity).apply {
                finishAffinity()
            })
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    private fun setTheme() {
        // Usage in when block
        val prefUtils = SharedPrefUtils(this)
        when (prefUtils.getThemeMode()) {
            ThemeMode.FOLLOW_SYS.value -> {
                applyThemeMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                )

            }

            ThemeMode.LIGHT_MODE.value -> {
                applyThemeMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )

            }

            ThemeMode.DARK_MODE.value -> {
                applyThemeMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )

            }

            ThemeMode.DRACULA.value -> {
                applyThemeMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            }
        }
    }

    private fun applyThemeMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
