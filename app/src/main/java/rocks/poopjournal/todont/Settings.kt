package rocks.poopjournal.todont;

import android.app.Dialog;
import android.app.LocaleManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import rocks.poopjournal.todont.utils.SharedPrefUtils;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.config.PointerType;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class Settings extends AppCompatActivity {
    Db_Controller db;
    TextView modetitle;
    private static final int REQUEST_CODE = 100;

    LinearLayout language;
    Spinner lang;
    private SharedPrefUtils prefUtils;
    View view;

    private List<String> localeList = Arrays.asList("cs", "da", "de", "en", "es", "it", "fr");
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 100;
    private static final int REQUEST_CODE_PICK_DB_FILE = 200;
    private Db_Helper dbHelper;  // Database helper to manage DB connection
    private static final String TAG = "DatabaseManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        prefUtils = new SharedPrefUtils(this);
        db = new Db_Controller(getApplicationContext(), "", null, 3);
        language = findViewById(R.id.language);
        view = findViewById(R.id.viww);
        lang = findViewById(R.id.spLanguagePicker);
        // Initialize your DatabaseHelper (assuming it's properly configured)
        dbHelper = new Db_Helper(this);


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent i = new Intent(Settings.this, MainActivity.class);
                finishAffinity();
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(Settings.this, MainActivity.class);
                finishAffinity();
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


//        checkPermissionAndRestoreDatabase();


 /*       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            language.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        }
            lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p0, View p1, int p2, long p3) {
                System.out.println("Selected locale = " + localeList.get(p2));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    setAppLocale(Locale.forLanguageTag(localeList.get(p2)));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> p0) {

            }
        });*/
        modetitle = findViewById(R.id.modetitle);
        Log.d("heyyyymode", "" + Helper.isnightmodeon);
        switch (Helper.isnightmodeon) {
            case "followsys":
                modetitle.setText(R.string.followsys);
                break;
            case "light":
                modetitle.setText(R.string.light);
                break;
            case "dark":
                modetitle.setText(R.string.dark);
                break;
        }
//        db.getNightMode();

    }

    public void changeMode(View view) {
        if (prefUtils.getBool(SharedPrefUtils.KEY_APPEAR_VIEW)) {
            GuideView.Builder guideView = new GuideView.Builder(this)
                    .setContentText("Help make \"To Don't\" better.")
                    .setTargetView(view)
                    .setDismissType(DismissType.anywhere)
                    .setPointerType(PointerType.arrow)
                    .setGravity(Gravity.center)
                    .setGuideListener(new GuideListener() {
                        @Override
                        public void onDismiss(View view) {
                            prefUtils.setBool(SharedPrefUtils.KEY_APPEAR_VIEW, true);
                        }
                    });
            guideView.build().show();
        } else {
            final Dialog d = new Dialog(this);
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.dialogbox);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button btndone = d.findViewById(R.id.btndone);
            RadioButton light, dark, fsys, dracula;
            light = d.findViewById(R.id.light);
            dark = d.findViewById(R.id.dark);
            dracula = d.findViewById(R.id.dracula);
            fsys = d.findViewById(R.id.followsys);
            String getmodetitle = modetitle.getText().toString();
            if (getmodetitle.equals("Follow System")) {
                fsys.setChecked(true);
            }
            if (getmodetitle.equals("Light")) {
                light.setChecked(true);
            }
            if (getmodetitle.equals("Dark")) {
                dark.setChecked(true);
            }
            if (getmodetitle.equals("Dracula")) {
                dracula.setChecked(true);
            }
            WindowManager.LayoutParams lp = d.getWindow().getAttributes();
            lp.dimAmount = 0.9f;
            d.getWindow().setAttributes(lp);

            btndone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.getNightMode();
                    Log.d("modeisbuttondone:", "" + Helper.isnightmodeon);
                    if (Helper.isnightmodeon.equals("followsys")) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        Log.d("checkmode", "f .. " + AppCompatDelegate.getDefaultNightMode());
                        Toast.makeText(getApplicationContext(), (R.string.toast_system), Toast.LENGTH_SHORT).show();
                        modetitle.setText(R.string.followsys);
                        d.dismiss();
                    }
                    if (Helper.isnightmodeon.equals("light")) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        Log.d("checkmode", "l .. " + AppCompatDelegate.getDefaultNightMode());
                        modetitle.setText(R.string.dark);
                        Toast.makeText(getApplicationContext(), (R.string.toast_light), Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    }
                    if (Helper.isnightmodeon.equals("dark")) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        Toast.makeText(getApplicationContext(), R.string.toast_dark, Toast.LENGTH_SHORT).show();
                        modetitle.setText(R.string.light);
                        d.dismiss();
                    }
                    if (Helper.isnightmodeon.equals("Dracula")) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    setTheme(R.style.DraculaTheme);

                        Toast.makeText(getApplicationContext(), R.string.toast_dark, Toast.LENGTH_SHORT).show();
                        modetitle.setText(R.string.light);
                        d.dismiss();
                    }
                }
            });
            d.show();
        }

//                db.getNightMode();
//        Log.d("qqq","infunc : "+Helper.isnightmodeon );
//        if (Helper.isnightmodeon.equals("no")) {
//            Log.d("qqq","inif");
//
//            db.update_nightmode("yes");
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            Intent intennt = new Intent(Settings.this,Settings.class);
//            startActivity(intennt);
//            overridePendingTransition(0, 0);
//            finish();
//        } else if (Helper.isnightmodeon.equals("yes")) {
//            Log.d("qqq","inelse");
//
//            db.update_nightmode("no");
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            Intent intennt = new Intent(Settings.this,Settings.class);
//            startActivity(intennt);
//            overridePendingTransition(0, 0);
//            finish();
//
//        }
////        if(Helper.isnightmodeon.equals("no")){
////            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
////        }
////        else if(Helper.isnightmodeon.equals("yes")){
////            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
////        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.followsys:
                if (checked) {
                    modetitle.setText("Follow System");
                    db.update_nightmode("followsys");
                    Log.d("modeisf:", "" + Helper.isnightmodeon);
                    break;
                }
            case R.id.light:
                if (checked) {
                    modetitle.setText("Light");
                    db.update_nightmode("light");
                    Log.d("modeisl:", "" + Helper.isnightmodeon);
                    break;
                }
            case R.id.dark:
                if (checked) {
                    modetitle.setText("Dark");
                    db.update_nightmode("dark");
                    Log.d("modeisd:", "" + Helper.isnightmodeon);
                    break;
                }
            case R.id.dracula:
                if (checked) {
                    modetitle.setText("Dracula");
                    db.update_nightmode("Dracula");
                    Log.d("modei    sd:", "" + Helper.isnightmodeon);
                    break;
                }
        }
    }

    public void backbtn(View view) {
 /*       Intent i = new Intent(Settings.this, MainActivity.class);
        finishAffinity();
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/

/*
        // Check if the write permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
        } else {
            // Permission already granted, proceed with copying the database
            copyDatabase();
        }*/

        copyDatabase();

    }

    public void aboutus(View view) {
        Intent i = new Intent(Settings.this, About.class);
        finishAffinity();
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public void restore(View view) {

        openFilePicker();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void setAppLocale(Locale locale) {
        LocaleManager localeManager = (LocaleManager) getSystemService(String.valueOf(LocaleManager.class));
        localeManager.setApplicationLocales(new LocaleList(locale));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, copy the database
                copyDatabase();
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Permission denied to write to external storage", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, restore the database
                Log.e(TAG, "granted");

            } else {
                // Permission denied, handle accordingly
                Log.e(TAG, "Permission denied to read external storage");
                // Check if the user denied the permission permanently
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    // Permission denied permanently, inform the user and direct them to settings
                    showPermissionDeniedDialog();
                } else {
                    // Permission denied but not permanently
                    Toast.makeText(this, "Permission is required to restore the database.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_DB_FILE && resultCode == RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            if (fileUri != null) {
                // Close the database before restoring
                dbHelper.closeDatabase();

                // Restore the selected file to the app's database directory
                restoreDatabase(fileUri);
                dbHelper = new Db_Helper(this);
                // Reopen the database after restoring
                dbHelper.getWritableDatabase();  // This reopens the DB connection
            }
        }
    }


    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Required")
                .setMessage("This app requires access to your external storage to restore the database. Please enable this permission in the app settings.")
                .setPositiveButton("Open Settings", (dialog, which) -> {
                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }


    // Method to copy the selected file back to the app's database directory
    private void restoreDatabase(Uri fileUri) {
        ContentResolver contentResolver = getContentResolver();
        dbHelper.close();
        dbHelper = null;
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.deleteCurrentDatabase();
        try (InputStream inputStream = contentResolver.openInputStream(fileUri)) {

            // Get the app's internal database directory
            File dbFile = getDatabasePath("todont.sqlite");  // Replace with actual DB name

            // If the file doesn't exist, create it
            if (!dbFile.exists()) {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
            }

            // Copy the selected file to the app's database directory
            try (OutputStream outputStream = new FileOutputStream(dbFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            /*    File backupFile = new File(this.getFilesDir(), "todont.sqlite"); // Ensure this file exists before using
                dbHelper.initializeDatabase(backupFile);*/

                Toast.makeText(this, "Database restored successfully", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to restore database: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void checkPermissionAndRestoreDatabase() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, REQUEST_CODE);
        }
    }

    // Method to copy the database
    private void copyDatabase() {
        DatabaseUtils.copyDatabaseToDownloads(this, "todont.sqlite");  // Replace with actual DB name
    }


    // Method to open the file picker and allow the user to select the database file
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/octet-stream");  // MIME type for .db files

        // Optional: Specify a specific folder (App's folder in Downloads)
        Uri downloadsFolderUri = Uri.parse(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, downloadsFolderUri);

        startActivityForResult(intent, REQUEST_CODE_PICK_DB_FILE);
    }
}
