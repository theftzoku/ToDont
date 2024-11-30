package rocks.poopjournal.todont.utils;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileUtils {

    // Method for copying the file for Android 10 and above using MediaStore API
    public static void saveFileToDownloadsUsingMediaStore(Context context, File sourceFile) {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues values = new ContentValues();

        // Set up the details for the file in MediaStore
        String fileName = sourceFile.getName();
        String mimeType = "application/octet-stream";
        String relativePath = Environment.DIRECTORY_DOWNLOADS;

        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath);

        // Insert the file details to MediaStore, getting the URI
        Uri fileUri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

        if (fileUri != null) {
            try (OutputStream outputStream = contentResolver.openOutputStream(fileUri);
                 FileInputStream inputStream = new FileInputStream(sourceFile)) {

                // Copy the data from the source file to the destination stream
                byte[] buffer = new byte[1024];
                int bytesCopied; // Number of bytes copied in each iteration

                while ((bytesCopied = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytesCopied);
                }

                Toast.makeText(context, "Database copied to Downloads folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                String errorMessage = "Failed to save file: " + e.getMessage();
                e.printStackTrace();
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    // Method for copying the file in older Android versions
    public static void copyFile(File sourceFile, File destFile) throws Exception {
        FileInputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int bytesCopied; // Number of bytes copied in each iteration

            while ((bytesCopied = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesCopied);
            }
        } finally {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
        }
    }
}
