package rocks.poopjournal.todont;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class DatabaseUtils {

    public static void copyDatabaseToDownloads(Context context, String dbName) {
        try {
            // Get the database file from the app's private directory
            File dbFile = context.getDatabasePath(dbName);

            // For Android 10 (API 29) and above, use the MediaStore API to write to Downloads folder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveFileToDownloadsUsingMediaStore(context, dbFile);
            } else {
                // For Android 9 (API 28) and below, use traditional file writing with WRITE_EXTERNAL_STORAGE permission
                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File destFile = new File(downloadsDir, dbFile.getName());
                copyFile(dbFile, destFile);
                Toast.makeText(context, "Database copied to: " + destFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to copy database: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Method for copying the file for Android 10 and above using MediaStore API
    private static void saveFileToDownloadsUsingMediaStore(Context context, File sourceFile) {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues values = new ContentValues();

        // Set up the details for the file in MediaStore
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, sourceFile.getName());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/octet-stream");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        // Insert the file details to MediaStore, getting the URI
        Uri fileUri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

        if (fileUri != null) {
            try (OutputStream outputStream = contentResolver.openOutputStream(fileUri);
                 FileInputStream inputStream = new FileInputStream(sourceFile)) {

                // Copy the data from the source file to the destination stream
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                Toast.makeText(context, "Database copied to Downloads folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to save file: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    // Method for copying the file in older Android versions
    private static void copyFile(File sourceFile, File destFile) throws Exception {
        FileInputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
        }
    }
}
