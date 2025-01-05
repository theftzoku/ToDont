package rocks.poopjournal.todont;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.app.LocaleManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources.Theme
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.LocaleList;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.core.view.WindowCompat
import rocks.poopjournal.todont.databinding.ActivitySettingsBinding
import rocks.poopjournal.todont.utils.DatabaseUtils

import java.io.FileOutputStream;
import java.util.Locale;

import rocks.poopjournal.todont.utils.SharedPrefUtils;
import rocks.poopjournal.todont.utils.ThemeMode

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.config.PointerType;

class Settings : AppCompatActivity() {

    private val REQUEST_CODE = 100

    private lateinit var prefUtils: SharedPrefUtils

    private val localeList = listOf("cs", "da", "de", "en", "es", "it", "fr")
    private val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 100
    private val REQUEST_CODE_PICK_DB_FILE = 200
    private lateinit var dbHelper: DatabaseUtils  // Database helper to manage DB connection
    private val TAG = "DatabaseManager"

    private lateinit var binding: ActivitySettingsBinding;

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefUtils = SharedPrefUtils(this)
        dbHelper = DatabaseUtils(this)
        // Enable immersive mode
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // Handle back press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
//                 val intent = Intent(this@Settings, MainActivity::class.java)
//                 finishAffinity()
//                 startActivity(intent)
                finish()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        })

        binding.btnBack.setOnClickListener {
//             val intent = Intent(this@Settings, MainActivity::class.java)
//             finishAffinity()
//             startActivity(intent)
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        binding.modetitle.text = getThemeMode(prefUtils.getThemeMode())
        binding.backUpButton.setOnClickListener {
            copyDatabase()
        }
    }


    fun changeMode(view: View) {
        if (prefUtils.getBool(SharedPrefUtils.KEY_APPEAR_VIEW)) {
            val guideView = GuideView.Builder(this)
                .setContentText(getString(R.string.help_make_to_don_t_better))
                .setTargetView(view)
                .setDismissType(DismissType.anywhere)
                .setPointerType(PointerType.arrow)
                .setGravity(Gravity.center)
                .setGuideListener { prefUtils.setBool(SharedPrefUtils.KEY_APPEAR_VIEW, true) }
                .build()
            guideView.show()
        } else {
            val dialog = Dialog(this).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialogbox)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.9f
            val window = dialog.window
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.attributes = lp

            val btndone = dialog.findViewById<Button>(R.id.btndone)
            val light = dialog.findViewById<RadioButton>(R.id.light)
            val dark = dialog.findViewById<RadioButton>(R.id.dark)
            val dracula = dialog.findViewById<RadioButton>(R.id.dracula)
            val fsys = dialog.findViewById<RadioButton>(R.id.followsys)


            when (binding.modetitle.text.toString()) {
                resources.getString(R.string.followsys) -> fsys.isChecked = true
                resources.getString(R.string.light) -> light.isChecked = true
                resources.getString(R.string.dark) -> dark.isChecked = true
                resources.getString(R.string.dracula) -> dracula.isChecked = true
            }

            dialog.window?.attributes = dialog.window?.attributes?.apply {
                dimAmount = 0.9f
            }

            btndone.setOnClickListener {
                setNewThemeMode()
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun setNewThemeMode() {
        // Usage in when block
        when (prefUtils.getThemeMode()) {
            ThemeMode.FOLLOW_SYS.value -> {
                applyThemeMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                    R.string.toast_system,
                    ThemeMode.FOLLOW_SYS.value
                )

            }

            ThemeMode.LIGHT_MODE.value-> {
                applyThemeMode(
                    AppCompatDelegate.MODE_NIGHT_NO,
                    R.string.toast_light,
                    ThemeMode.LIGHT_MODE.value
                )

            }

            ThemeMode.DARK_MODE.value -> {
                applyThemeMode(
                    AppCompatDelegate.MODE_NIGHT_YES,
                    R.string.toast_dark,
                    ThemeMode.DARK_MODE.value
                )

            }

            ThemeMode.DRACULA.value -> {
                applyThemeMode(
                    AppCompatDelegate.MODE_NIGHT_YES,
                    R.string.toast_dark, // Optionally change this to a custom Dracula toast message
                    ThemeMode.DRACULA.value
                )
            }
        }
    }

    private fun applyThemeMode(mode: Int, toastMessageRes: Int, modeTitle: String) {
        AppCompatDelegate.setDefaultNightMode(mode)
        Log.d("checkmode", "Mode set to: $mode")
        Toast.makeText(applicationContext, toastMessageRes, Toast.LENGTH_SHORT).show()
        binding.modetitle.text = getThemeMode(modeTitle)
    }

    private fun getThemeMode(modeTitle: String): String {
        return when (modeTitle) {
            ThemeMode.FOLLOW_SYS.value -> {
                resources.getString(R.string.followsys)
            }

            ThemeMode.LIGHT_MODE.value -> {
                resources.getString(R.string.light)
            }

           ThemeMode.DARK_MODE.value -> {
                resources.getString(R.string.dark)
            }

            ThemeMode.DRACULA.value -> {
                resources.getString(R.string.dracula)
            }
            else -> {
                modeTitle
            }
        }
    }


    fun onRadioButtonClicked(view: View) {
        // Check if the button is now checked
        val checked = (view as RadioButton).isChecked

        // Determine which radio button was clicked
        when (view.id) {
            R.id.followsys -> {
                if (checked) {
                    updateThemeMode(ThemeMode.FOLLOW_SYS.value)
                }
            }

            R.id.light -> {
                if (checked) {
                    updateThemeMode(ThemeMode.LIGHT_MODE.value)
                }
            }

            R.id.dark -> {
                if (checked) {
                    updateThemeMode(ThemeMode.DARK_MODE.value)
                }
            }

            R.id.dracula -> {
                if (checked) {
                    updateThemeMode(ThemeMode.DRACULA.value)
                }
            }
        }
    }

    private fun updateThemeMode(value: String) {
        binding.modetitle.text =getThemeMode(value)
        prefUtils.setThemeMode(value)
    }

    private fun backBtn(view: View) {
        onBackPressed()
    }

    fun aboutus(view: View) {
        Intent(this, About::class.java).also { intent ->
            //finishAffinity()
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    fun restore(view: View) {
        openFilePicker()
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setAppLocale(locale: Locale) {
        val localeManager = getSystemService(LocaleManager::class.java)
        localeManager?.applicationLocales = LocaleList(locale)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, copy the database
                    copyDatabase()
                } else {
                    // Permission denied, show a message
                    Toast.makeText(
                        this,
                        "Permission denied to write to external storage",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, restore the database
                    Log.e(TAG, "granted")
                } else {
                    // Permission denied, handle accordingly
                    Log.e(TAG, "Permission denied to read external storage")
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            "android.permission.WRITE_EXTERNAL_STORAGE"
                        )
                    ) {
                        // Permission denied permanently, inform the user and direct them to settings
                        showPermissionDeniedDialog()
                    } else {
                        // Permission denied but not permanently
                        Toast.makeText(
                            this,
                            "Permission is required to restore the database.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_DB_FILE && resultCode == RESULT_OK && data != null) {
            val fileUri = data.data
            if (fileUri != null) {
                // Close the database before restoring
                dbHelper.closeDatabase()

                // Restore the selected file to the app's database directory
                restoreDatabase(fileUri)
                dbHelper = DatabaseUtils(this)

                // Reopen the database after restoring
                dbHelper.writableDatabase // This reopens the DB connection
            }
        }
    }


    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_required))
            .setMessage(getString(R.string.this_app_requires_access_to_your_external_storage_to_restore_the_database_please_enable_this_permission_in_the_app_settings))
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                val intent =
                    Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    }
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    // Method to copy the selected file back to the app's database directory
    private fun restoreDatabase(fileUri: Uri) {
        val contentResolver = contentResolver
        dbHelper.close()
        val databaseManager = DatabaseUtils(this)
        databaseManager.deleteCurrentDatabase()

        try {
            contentResolver.openInputStream(fileUri)?.use { inputStream ->
                // Get the app's internal database directory
                val dbFile =
                    getDatabasePath(DatabaseUtils.DATABASE_NAME) // Replace with actual DB name


                // If the file doesn't exist, create it
                if (!dbFile.exists()) {
                    dbFile.parentFile?.mkdirs()
                    dbFile.createNewFile()
                }

                // Copy the selected file to the app's database directory
                FileOutputStream(dbFile).use { outputStream ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (inputStream.read(buffer).also { length = it } > 0) {
                        outputStream.write(buffer, 0, length)
                    }
                }

                Toast.makeText(this, "Database restored successfully", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to restore database: ${e.message}", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun checkPermissionAndRestoreDatabase() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE
            )
        }
    }

    // Method to copy the database
    private fun copyDatabase() {
        DatabaseUtils.copyDatabaseToDownloads(this, DatabaseUtils.DATABASE_NAME)
    }

    // Method to open the file picker and allow the user to select the database file
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/octet-stream" // MIME type for .db files

            // Optional: Specify a specific folder (App's folder in Downloads)
            val downloadsFolderUri =
                Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, downloadsFolderUri)
        }
        startActivityForResult(intent, REQUEST_CODE_PICK_DB_FILE)
    }


}
