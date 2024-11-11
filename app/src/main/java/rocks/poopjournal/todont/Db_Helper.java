package rocks.poopjournal.todont;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Db_Helper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todont.sqlite";
    private static final int DATABASE_VERSION = 2;

    // Table names
    private static final String TABLE_HABITS = "HABITS";
    private static final String TABLE_AVOIDED = "AVOIDED";
    private static final String TABLE_DONE = "DONE";
    private static final String TABLE_LABELS = "LABELS";
    private static final String TABLE_CHECKNIGHTMODE = "CHECKNIGHTMODE";

    // Common column names
    private static final String KEY_ID = "ID INTEGER PRIMARY KEY AUTOINCREMENT"; // Using AUTOINCREMENT
    private static final String KEY_DATE = "DATE TEXT";
    private static final String KEY_HABIT = "HABIT TEXT";
    private static final String KEY_DETAIL = "DETAIL TEXT";
    private static final String KEY_TIMES = "TIMES INTEGER";
    private static final String KEY_CATEGORY = "CATEGORY TEXT";

    // Table specific columns
    private static final String KEY_LABEL = "LABEL TEXT";
    private static final String KEY_NIGHTMODE = "NIGHTMODE TEXT";

    private SQLiteDatabase database;
    private Context context;

    public Db_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HABITS_TABLE = "CREATE TABLE " + TABLE_HABITS + "("
                + KEY_ID + ", " + KEY_DATE + ", " + KEY_HABIT + ", "
                + KEY_DETAIL + ", " + KEY_TIMES + ", " + KEY_CATEGORY + ");";

        String CREATE_AVOIDED_TABLE = "CREATE TABLE " + TABLE_AVOIDED + "("
                + KEY_ID + ", " + KEY_DATE + ", " + KEY_HABIT + ", "
                + KEY_DETAIL + ", " + KEY_TIMES + ", " + KEY_CATEGORY + ");";

        String CREATE_DONE_TABLE = "CREATE TABLE " + TABLE_DONE + "("
                + KEY_ID + ", " + KEY_DATE + ", " + KEY_HABIT + ", "
                + KEY_DETAIL + ", " + KEY_TIMES + ", " + KEY_CATEGORY + ");";

        String CREATE_LABELS_TABLE = "CREATE TABLE " + TABLE_LABELS + "(" + KEY_LABEL + ");";
        String CREATE_NIGHTMODE_TABLE = "CREATE TABLE " + TABLE_CHECKNIGHTMODE + "(" + KEY_NIGHTMODE + ");";

        db.execSQL(CREATE_HABITS_TABLE);
        db.execSQL(CREATE_AVOIDED_TABLE);
        db.execSQL(CREATE_DONE_TABLE);
        db.execSQL(CREATE_LABELS_TABLE);
        db.execSQL(CREATE_NIGHTMODE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AVOIDED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LABELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKNIGHTMODE);
        onCreate(db);
    }

    // Method to restore the database from backup
    public void restoreDatabase(File backupFile) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if (dbFile.exists() && dbFile.length() > 0) {
            Log.d("Db_Helper", "Existing database found, not restoring.");
            return; // Don't restore if the database already exists and is not empty
        }

        try (FileInputStream fis = new FileInputStream(backupFile);
             FileOutputStream fos = new FileOutputStream(dbFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            Log.d("Db_Helper", "Database restored from backup.");
        } catch (IOException e) {
            Log.e("Db_Helper", "Error restoring database: " + e.getMessage());
        }
    }

    // Open the database
    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    // Close the database
    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    // Insert methods
    public void insertLabel(String label) {
        ContentValues values = new ContentValues();
        values.put(KEY_LABEL, label);
        this.getWritableDatabase().insertOrThrow(TABLE_LABELS, null, values);
    }

    public void insertHabit(String date, String habit, String detail, int times, String category) {
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_HABIT, habit);
        values.put(KEY_DETAIL, detail);
        values.put(KEY_TIMES, times);
        values.put(KEY_CATEGORY, category);
        this.getWritableDatabase().insertOrThrow(TABLE_HABITS, null, values);
    }

    public void insertDoneData(String date, String habit, String detail, int times, String category) {
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_HABIT, habit);
        values.put(KEY_DETAIL, detail);
        values.put(KEY_TIMES, times);
        values.put(KEY_CATEGORY, category);
        this.getWritableDatabase().insertOrThrow(TABLE_DONE, null, values);
    }

    public void insertAvoidedData(String date, String habit, String detail, int times, String category) {
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_HABIT, habit);
        values.put(KEY_DETAIL, detail);
        values.put(KEY_TIMES, times);
        values.put(KEY_CATEGORY, category);
        this.getWritableDatabase().insertOrThrow(TABLE_AVOIDED, null, values);
    }

    public void setNightMode(String mode) {
        ContentValues values = new ContentValues();
        values.put(KEY_NIGHTMODE, mode);
        this.getWritableDatabase().insertOrThrow(TABLE_CHECKNIGHTMODE, null, values);
    }

    // Fetch methods
    public void getNightMode() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_CHECKNIGHTMODE, null);
        if (cursor.moveToFirst()) {
            Helper.isnightmodeon = cursor.getString(0);
        }
        cursor.close();
    }

    public void showHabitsData() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_HABITS, null);
        Helper.habitsdata = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] data = new String[5];
            data[0] = cursor.getString(0);
            data[1] = cursor.getString(1);
            data[2] = cursor.getString(2).replace("geodhola", "'");
            data[3] = cursor.getString(3);
            data[4] = cursor.getString(4);
            Helper.habitsdata.add(data);
        }
        cursor.close();
    }

    public void showAvoidedData() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_AVOIDED, null);
        Helper.avoidedData = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] data = new String[5];
            data[0] = cursor.getString(0);
            data[1] = cursor.getString(1);
            data[2] = cursor.getString(2);
            data[3] = cursor.getString(3);
            data[4] = cursor.getString(4);
            Helper.avoidedData.add(data);
        }
        cursor.close();
    }

    // Update methods
    public void updateHabit(int id, String date, String habit, String detail, String category) {
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_HABIT, habit);
        values.put(KEY_DETAIL, detail);
        values.put(KEY_CATEGORY, category);

        this.getWritableDatabase().update(TABLE_HABITS, values, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void updateDoneData(int id, String date, String habit, String detail, int times, String category) {
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_HABIT, habit);
        values.put(KEY_DETAIL, detail);
        values.put(KEY_TIMES, times);
        values.put(KEY_CATEGORY, category);

        this.getWritableDatabase().update(TABLE_DONE, values, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void updateNightMode(String mode) {
        ContentValues values = new ContentValues();
        values.put(KEY_NIGHTMODE, mode);
        this.getWritableDatabase().update(TABLE_CHECKNIGHTMODE, values, null, null);
        Helper.isnightmodeon = mode;
    }

    // Delete methods
    public void deleteHabit(int id) {
        this.getWritableDatabase().delete(TABLE_HABITS, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteAvoided(int id) {
        this.getWritableDatabase().delete(TABLE_AVOIDED, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteDone(int id) {
        this.getWritableDatabase().delete(TABLE_DONE, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Initialize the database with restoration
    public void initializeDatabase(File backupFile) {
        restoreDatabase(backupFile);
        open(); // Open the database after restoring it
    }
}