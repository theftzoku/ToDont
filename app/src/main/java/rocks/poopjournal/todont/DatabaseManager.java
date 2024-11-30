package rocks.poopjournal.todont;

import static rocks.poopjournal.todont.utils.FileUtils.copyFile;
import static rocks.poopjournal.todont.utils.FileUtils.saveFileToDownloadsUsingMediaStore;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
refactor: Pull-up Variable/Method:
 Both classes have similar methods dealing with database file operations
 (e.g., copying, restoring). We can centralize those methods in the DatabaseManager.

 */
public class DatabaseManager {
    private Context context;
    private static final String DATABASE_NAME = "todont.sqlite";
    private static final String BACKUP_FILE_NAME = "todont.sqlite"; // Name of the backup file
    private static final String TAG = "DatabaseManager";

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public void deleteCurrentDatabase() {
        // Get the path of the current database
        File currentDatabase = context.getDatabasePath(DATABASE_NAME);

        // Check if the current database exists
        if (currentDatabase.exists()) {
            // Delete the existing database
            if (currentDatabase.delete()) {
                Log.d(TAG, "Existing database deleted successfully.");
            } else {
                Log.e(TAG, "Failed to delete existing database.");
                return; // Exit if unable to delete
            }
        }
    }

    public void restoreDatabase() {
        File currentDatabase = context.getDatabasePath(DATABASE_NAME);
        deleteCurrentDatabase();

        // Now proceed to restore the new database from the backup file
        File backupFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), DATABASE_NAME);

        if (!backupFile.exists()) {
            Log.e(TAG, "Backup file does not exist.");
            return; // Exit if backup file is not found
        }

        // Copy the backup file to the database location
        try {
            copyDatabase(backupFile, currentDatabase);
            Log.d(TAG, "Database restored successfully.");
        } catch (IOException e) {
            Log.e(TAG, "Failed to restore database: " + e.getMessage());
        }
    }

    private void copyDatabase(File sourceFile, File destFile) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFile);
        FileOutputStream fos = new FileOutputStream(destFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }

        fos.flush();
        fos.close();
        fis.close();
    }

    public static void copyDatabaseToDownloads(Context context, String dbName) {
        try {
            // Get the database file from the app's private directory
            File dbFile = context.getDatabasePath(dbName);

            // For Android 10 (API 29) and above, use the MediaStore API to write to Downloads folder
            /*
            refactor: Introduce Explaining Variable: For below changes like isAndroidQOrAbove and other name variables added in this file for better understanding.
             */
            boolean isAndroidQOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;

            if (isAndroidQOrAbove) {
                /*
                refactor: Move method Or Extract Class
                 As file operations should be part of the fileUtils class Not the DatabaseUtils
                 Removed 2 methods saveFileToDownloadsUsingMediaStore() and copyFile() to a newly created class named FileUtils.
                 */
                saveFileToDownloadsUsingMediaStore(context, dbFile);
            } else {
                // For Android 9 (API 28) and below, use traditional file writing with WRITE_EXTERNAL_STORAGE permission
                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File destFile = new File(downloadsDir, dbFile.getName());

                copyFile(dbFile, destFile);
                String successMessage = "Database copied to: " + destFile.getAbsolutePath();
                Toast.makeText(context, successMessage, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            String errorMessage = "Failed to copy database: " + e.getMessage();
            e.printStackTrace();
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}