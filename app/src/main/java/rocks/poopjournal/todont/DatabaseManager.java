package rocks.poopjournal.todont;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
}