package rocks.poopjournal.todont

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import rocks.poopjournal.todont.fragments.FragmentLog
import rocks.poopjournal.todont.fragments.FragmentToday
import rocks.poopjournal.todont.databinding.ActivityMainBinding
import rocks.poopjournal.todont.utils.DatabaseUtils
import rocks.poopjournal.todont.utils.SharedPrefUtils
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import smartdevelop.ir.eram.showcaseviewlib.config.PointerType

class MainActivity : AppCompatActivity() {
    // Database controller instance
    private var dbHelper: DatabaseUtils? = null
    private lateinit var binding: ActivityMainBinding
    private var prefUtils: SharedPrefUtils? = null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Enable immersive mode
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        // Initialize the database controller
        prefUtils = SharedPrefUtils(this)
        dbHelper = DatabaseUtils(this)

        // Set toolbar text to "Today"
        binding.toolbartext.setText(R.string.today)

        // Make the label and settings views visible
        binding.label.visibility = View.VISIBLE
        binding.settings.visibility = View.VISIBLE

        // Retrieve and apply the night mode setting from the database
        //db?.getNightMode()

        // Customize the action bar background
        actionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.mygradient))

        // Define a listener for the bottom navigation view
        binding.navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_today -> {
                    // Switch to the 'FragmentToday' when "Today" is selected
                    replaceFragment(FragmentToday())
                    binding.toolbartext.setText(R.string.today)
                    binding.label.visibility = View.VISIBLE
                    binding.settings.visibility = View.VISIBLE
                    true
                }
                R.id.navigation_log -> {
                    // Show tutorial guide for first-time usage
                    if (prefUtils?.getBool(SharedPrefUtils.KEY_LOG) != true && !intent.getBooleanExtra("openLog", false)) {
                        showLogGuide()
                    } else {
                        // Switch to the 'FragmentLog' when "Log" is selected
                        replaceFragment(FragmentLog())
                        binding.toolbartext.setText(R.string.log)
                        binding.label.visibility = View.INVISIBLE
                        binding.settings.visibility = View.INVISIBLE
                    }
                    true
                }
                else -> false
            }
        }

        // Initialize the default fragment
        if (intent.getBooleanExtra("openLog", false)) {
            binding.navigationView.selectedItemId = R.id.navigation_log
        } else {
            replaceFragment(FragmentToday())
        }

        // Set label click action
        binding.label.setOnClickListener {
            val intent = Intent(this, LabelsActivity::class.java)
            startActivity(intent)
            //finish()
        }
    }

    private fun showLogGuide() {
        GuideView.Builder(this@MainActivity)
            .setContentText(getString(R.string.view_your_stats))
            .setTargetView(binding.navigationView.findViewById(R.id.navigation_log))
            .setDismissType(DismissType.anywhere)
            .setPointerType(PointerType.arrow)
            .setGravity(Gravity.center)
            .setGuideListener {
                prefUtils?.setBool(SharedPrefUtils.KEY_LOG, true)
            }
            .build()
            .show()
    }

    /**
     * Replace the current fragment with the specified one.
     * @param fragment The fragment to display.
     */
    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        val fragmentTag = fragment.javaClass.simpleName
        // Replace the fragment and add it to the back stack
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragmentTag)
            .commit()
    }



    // Handle the "Settings" action when a view is clicked
    fun mySettings(view: View) {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        //finish()
    }

    // Handle the back button press
    override fun onBackPressed() {
        // Customize the back button behavior
        if (supportFragmentManager.backStackEntryCount > 0) {
            // If fragments are in the back stack, pop the latest one
            supportFragmentManager.popBackStack()
        } else {
            // If no fragments are in the back stack, exit the activity
            super.onBackPressed()
            //finish()
        }
    }
}
