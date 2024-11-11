package rocks.poopjournal.todont;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Db_Controller extends SQLiteOpenHelper {

    private static final String TABLE_ALARMS = "alarms";
    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_ALARM_TIME = "alarm_time";
    private static final String COLUMN_FREQUENCY = "frequency";
    private static final String CREATE_TABLE_ALARMS = "CREATE TABLE " + TABLE_ALARMS + " (" +
            COLUMN_TASK_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_ALARM_TIME + " INTEGER, " + // store alarm time as Unix timestamp
            COLUMN_FREQUENCY + " TEXT)";
    public Db_Controller(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "todont.sqlite", factory, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        db.execSQL("CREATE TABLE HABITS(ID INTEGER Primary key ,DATE TEXT,HABIT TEXT,DETAIL TEXT,TIMES INTEGER,CATAGORY TEXT);");
        db.execSQL("CREATE TABLE AVOIDED(ID INTEGER Primary key ,DATE TEXT,HABIT TEXT,DETAIL TEXT,TIMES INTEGER,CATAGORY TEXT);");
        db.execSQL("CREATE TABLE DONE(ID INTEGER Primary key ,DATE TEXT,HABIT TEXT,DETAIL TEXT,TIMES INTEGER,CATAGORY TEXT);");
        db.execSQL("CREATE TABLE LABELS(LABEL TEXT);");
        db.execSQL("CREATE TABLE CHECKNIGHTMODE(NIGHTMODE TEXT);");
        db.execSQL(CREATE_TABLE_ALARMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS AVOIDED;");
        db.execSQL("DROP TABLE IF EXISTS HABITS;");
        db.execSQL("DROP TABLE IF EXISTS DONE;");
        db.execSQL("DROP TABLE IF EXISTS LABELS;");
        db.execSQL("DROP TABLE IF EXISTS CHECKNIGHTMODE;");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ALARMS+";");
        onCreate(db);
    }

    // Insert or Update alarm
    public void insertOrUpdateAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_ID, alarm.getTaskId());
        values.put(COLUMN_ALARM_TIME, alarm.getAlarmTime());
        values.put(COLUMN_FREQUENCY, alarm.getFrequency());

        db.replace(TABLE_ALARMS, null, values); // Use replace to update if task_id exists
        db.close();
    }
    // Delete alarm by task ID
    public void deleteAlarm(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARMS, COLUMN_TASK_ID + " = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }
    // Get alarm details by task ID
    public Alarm getAlarm(int taskId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ALARMS, null, COLUMN_TASK_ID + " = ?", new String[]{String.valueOf(taskId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            long alarmTime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ALARM_TIME));
            String frequency = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FREQUENCY));
            cursor.close();
            db.close();
            return new Alarm(taskId, alarmTime, frequency);
        } else {
            if (cursor != null) cursor.close();
            db.close();
            return null; // Alarm not found
        }
    }
    // Get all alarms
    public List<Alarm> getAllAlarms() {
        List<Alarm> alarmList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ALARMS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int taskId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_ID));
                long alarmTime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ALARM_TIME));
                String frequency = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FREQUENCY));
                alarmList.add(new Alarm(taskId, alarmTime, frequency));
            } while (cursor.moveToNext());
        }

        if (cursor != null) cursor.close();
        db.close();
        return alarmList;
    }


    public void insert_label(String label) {
        Log.d("insert_label...", "" + label);
        ContentValues con = new ContentValues();
        con.put("LABEL", label);
        this.getWritableDatabase().insertOrThrow("LABELS", "", con);

    }

    public void setNightMode(String a) {
        ContentValues con = new ContentValues();
        con.put("NIGHTMODE", a);
        this.getWritableDatabase().insertOrThrow("CHECKNIGHTMODE", "", con);
    }

    public void getNightMode() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM CHECKNIGHTMODE", null);
        while (cursor.moveToNext()) {
            Helper.isnightmodeon = cursor.getString(0);
        }

    }

    public void update_nightmode(String a) {
        this.getWritableDatabase().execSQL("UPDATE CHECKNIGHTMODE SET NIGHTMODE='" + a + "'");
        Helper.isnightmodeon = a;
    }

    public void insert_data(int id, String date, String habit, String detail, int times, String catagory) {
        ContentValues con = new ContentValues();
        con.put("ID", id);
        con.put("DATE", date);
        con.put("HABIT", habit);
        con.put("DETAIL", detail);
        con.put("TIMES", times);
        con.put("CATAGORY", catagory);
        this.getWritableDatabase().insertOrThrow("AVOIDED", "", con);

    }


    public void insert_habits(int id, String date, String habit, String detail, int times, String catagory) {
        ContentValues con = new ContentValues();
        con.put("ID", id);
        con.put("DATE", date);
        con.put("HABIT", habit);
        con.put("DETAIL", detail);
        con.put("CATAGORY", catagory);
        con.put("TIMES", times);
        this.getWritableDatabase().insertOrThrow("HABITS", "", con);

    }

    public void insert_done_data(int id, String date, String habit, String detail, int times, String catagory) {
        ContentValues con = new ContentValues();
        con.put("ID", id);
        con.put("DATE", date);
        con.put("HABIT", habit);
        con.put("DETAIL", detail);
        con.put("CATAGORY", catagory);
        con.put("TIMES", times);

        this.getWritableDatabase().insertOrThrow("DONE", "", con);
    }

    public void show_habits_data() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM HABITS", null);
        Helper.habitsdata = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] temp = new String[6];
            temp[0] = (cursor.getString(0));
            temp[1] = (cursor.getString(1));
            String str = (cursor.getString(2));
            if (str.contains("geodhola")) {
                str = str.replace("geodhola", "'");
            }
            temp[2] = str;
            temp[3] = (cursor.getString(3));
            temp[4] = (cursor.getString(4));
            temp[5] = (cursor.getString(5));
            Helper.habitsdata.add(temp);
        }

    }

    public void show_avoided_data() {

        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM AVOIDED", null);
        Helper.avoidedData = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] temp = new String[6];
            temp[0] = (cursor.getString(0));
            temp[1] = (cursor.getString(1));
            temp[2] = (cursor.getString(2));
            temp[3] = (cursor.getString(3));
            temp[4] = (cursor.getString(4));
            temp[5] = (cursor.getString(5));

            Helper.avoidedData.add(temp);
        }

    }

    public void show_done_data() {

        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM DONE", null);
        Helper.donedata = new ArrayList<>();

        while (cursor.moveToNext()) {
            String[] temp = new String[6];
            temp[0] = (cursor.getString(0));
            temp[1] = (cursor.getString(1));
            temp[2] = (cursor.getString(2));
            temp[3] = (cursor.getString(3));
            temp[4] = (cursor.getString(4));
            temp[5] = (cursor.getString(5));


            Helper.donedata.add(temp);
        }
    }


    public void show_labels() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM LABELS", null);
        Helper.labels_array = new ArrayList<>();
        while (cursor.moveToNext()) {
            Helper.labels_array.add(cursor.getString(0));
        }
    }

        public void update_habitsdata(int id, String date, String habit, String detail,int count, String catagory) {
            this.getWritableDatabase().execSQL("UPDATE HABITS SET DATE='" + date + "',HABIT='" + habit + "',DETAIL='" + detail + "',TIMES='" + count + "',CATAGORY='" + catagory + "' WHERE ID='" + id + "'");

        }
/*    public void update_habitsdata(int id, String date, String habit, String detail, int times, String category) {
        Log.i("UpdateData", "Updating data for ID: " + id);

        String query = "UPDATE HABITS SET DATE=?, HABIT=?, DETAIL=?, TIMES=?, CATAGORY=? WHERE ID=?";
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL(query, new String[]{date, habit, detail, String.valueOf(times), category, String.valueOf(id)});
            Log.i("tariq", "Update successful");
        } catch (SQLException e) {
            Log.e("tariq", "Error updating data: " + e.getMessage());
        } finally {
            db.close();
        }
    }*/

    /*   public void update_data(int id, String date, String habit, String detail,int times, String catagory) {
           Log.i("tariq", "update_data: "+times);
           this.getWritableDatabase().execSQL("UPDATE AVOIDED SET DATE='" + date + "',HABIT='" + habit + "',DETAIL='" + detail +"',TIMES ='"+times+ "',CATAGORY='" + catagory + "' WHERE ID='" + id + "'");

       }*/
    public void update_data(int id, String date, String habit, String detail, int times, String category) {
        Log.i("UpdateData", "Updating data for ID: " + id);

        String query = "UPDATE AVOIDED SET DATE=?, HABIT=?, DETAIL=?, TIMES=?, CATAGORY=? WHERE ID=?";
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL(query, new String[]{date, habit, detail, String.valueOf(times), category, String.valueOf(id)});
            Log.i("tariq", "Update successful");
        } catch (SQLException e) {
            Log.e("tariq", "Error updating data: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public void update_data_avoided(int id, String date, String habit, String detail, int times, String catagory) {

        ContentValues values = new ContentValues();
        values.put("DATE", date);
        values.put("HABIT", habit);
        values.put("DETAIL", detail);
        values.put("TIMES", times);
        values.put("CATAGORY", catagory);
// Add more columns and values as needed

        String selection = "ID = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int rowsAffected = this.getWritableDatabase().update("AVOIDED", values, selection, selectionArgs);

// Check if update was successful
        if (rowsAffected > 0) {
            Log.d("SQLiteUpdate", "Table updated successfully");
        } else {
            Log.d("SQLiteUpdate", "No rows updated");
        }

        this.getWritableDatabase().close();


        /*Log.i("tariq", "update_data: "+times);
        this.getWritableDatabase().execSQL("UPDATE AVOIDED SET DATE='" + date + "',HABIT='" + habit + "',DETAIL='" + detail +"',TIMES ='"+times+ "',CATAGORY='" + catagory + "' WHERE ID='" + id + "'");*/

    }


/*    public void update_done_data(int id, String date, String habit, String detail, String catagory) {

        this.getWritableDatabase().execSQL("UPDATE DONE SET DATE='" + date + "',HABIT='" + habit + "',DETAIL='" + detail + "',CATAGORY='" + catagory + "' WHERE ID='" + id + "'");

    }*/
    public void update_done_data(int id, String date, String habit, String detail, int times, String category) {
        Log.i("tariq", "Updating data for ID: " + id);
        String query = "UPDATE DONE SET DATE=?, HABIT=?, DETAIL=?, TIMES=?, CATAGORY=? WHERE ID=?";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(query, new String[]{date, habit, detail, String.valueOf(times), category, String.valueOf(id)});
            Log.i("tariq", "Update successful");
        } catch (SQLException e) {
            Log.e("tariq", "Error updating data: " + e.getMessage());
        } finally {
            db.close();
        }
    }
    public void delete_habits(int id) {
        Log.d("dfadelete", "" + id);

        String getHabitName = "";
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM HABITS WHERE id ='" + id + "'", null);
        if (cursor.moveToFirst()) {
            getHabitName = (cursor.getString(2));
        }
        Log.d("dfaaaaaaaadelete", "hi" + getHabitName);
        this.getWritableDatabase().delete("HABITS", "ID='" + id + "'", null);
        this.getWritableDatabase().delete("AVOIDED", "HABIT='" + getHabitName + "'", null);
        this.getWritableDatabase().delete("DONE", "HABIT='" + getHabitName + "'", null);

    }

    public void delete_avoided(int id) {
        Log.d("aaaadelete", "" + id);
        this.getWritableDatabase().delete("AVOIDED", "ID='" + id + "'", null);

    }

    public void delete_done(int id) {
        this.getWritableDatabase().delete("DONE", "ID='" + id + "'", null);

    }

    public void updateHabitsIdsAfterDeletion(int i) {
        int j = i - 1;
        this.getWritableDatabase().execSQL("UPDATE HABITS SET ID='" + j + "' WHERE ID='" + i + "'");


    }

    public void updateIdsAfterDeletion(int i) {
        int j = i - 1;
        Log.d("aaaaupdate", "" + i);
        this.getWritableDatabase().execSQL("UPDATE AVOIDED SET ID='" + j + "' WHERE ID='" + i + "'");


    }

    public void updateDoneIdsAfterDeletion(int i) {
        int j = i - 1;
        this.getWritableDatabase().execSQL("UPDATE DONE SET ID='" + j + "' WHERE ID='" + i + "'");


    }

    public void delete_label(String label) {
        this.getWritableDatabase().delete("LABELS", "LABEL='" + label + "'", null);

    }

    public void checkifAlreadyExists(String date) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM AVOIDED WHERE DATE='" + date + "'", null);
    }

    public void getDailyDoneRecord(String date) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM DONE WHERE DATE ='" + date + "'", null);
        Helper.donelogdata = new ArrayList<>();

        while (cursor.moveToNext()) {
            String[] temp = new String[6];
            temp[0] = (cursor.getString(0));
            temp[1] = (cursor.getString(1));
            temp[2] = (cursor.getString(2));
            temp[3] = (cursor.getString(3));
            temp[4] = (cursor.getString(4));
            temp[5] = (cursor.getString(5));


            Helper.donelogdata.add(temp);
        }
    }

    public void getDailyAvoidedRecord(String date) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM AVOIDED WHERE DATE ='" + date + "'", null);
        Helper.avoidedlogdata = new ArrayList<>();

        while (cursor.moveToNext()) {
            String[] temp = new String[6];
            temp[0] = (cursor.getString(0));
            temp[1] = (cursor.getString(1));
            temp[2] = (cursor.getString(2));
            temp[3] = (cursor.getString(3));
            temp[4] = (cursor.getString(4));
            temp[5] = (cursor.getString(5));


            Helper.avoidedlogdata.add(temp);
        }
    }


    public String getWeeklyAvoidedRecord(String date, String date2) {
        Helper.avoidedweeklydata = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM AVOIDED WHERE DATE BETWEEN '" + date + "' AND '" + date2 + "'", null);
        while (cursor.moveToNext()) {
            Helper.avoidedweeklydata.add(cursor.getString(2));
        }
        int mostFre = 0, val = 0;
        for (int i = 0; i < Helper.avoidedweeklydata.size(); i++) {
            int count = 0;
            for (int j = i + 1; j < Helper.avoidedweeklydata.size(); j++) {
                if ((Helper.avoidedweeklydata.get(i)).equals(Helper.avoidedweeklydata.get(j))) {
                    count++;
                    if (count > mostFre) {
                        mostFre = count;
                        val = j;
                    }
                    Log.d("aaaCount: ", "" + count + " i:" + i + " j:" + j);
                }
            }
        }
        if (Helper.avoidedweeklydata.size() == 0) {
            return "";
        } else {
            return Helper.avoidedweeklydata.get(val);
        }
    }

    public String getWeeklyDoneRecord(String date, String date2) {
        Helper.doneweeklydata = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM DONE WHERE DATE BETWEEN '" + date + "' AND '" + date2 + "'", null);
        while (cursor.moveToNext()) {
            Helper.doneweeklydata.add(cursor.getString(2));
        }
        int mostFre = 0, val = 0;
        for (int i = 0; i < Helper.doneweeklydata.size(); i++) {
            int count = 0;
            for (int j = i + 1; j < Helper.doneweeklydata.size(); j++) {
                if ((Helper.doneweeklydata.get(i)).equals(Helper.doneweeklydata.get(j))) {
                    count++;
                    if (count > mostFre) {
                        mostFre = count;
                        val = j;
                    }
                    Log.d("aaaCount: ", "" + count + " i:" + i + " j:" + j);
                }
            }
        }
        if (Helper.doneweeklydata.size() == 0) {
            return "";
        } else {
            return Helper.doneweeklydata.get(val);
        }
    }

    public String getMonthlyAvoidedData(String date, String date2) {
        Log.d("splitteddate444 ", "" + date + " * " + date2);
        Helper.avoidedmonthlydata = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM AVOIDED WHERE DATE BETWEEN '" + date + "' AND '" + date2 + "'", null);
        while (cursor.moveToNext()) {
            Log.d("date", "" + cursor.getString(2));
            Helper.avoidedmonthlydata.add(cursor.getString(2));
        }
        Log.d("split", "" + Helper.avoidedmonthlydata.size());

        int mostFre = 0, val = 0;
        for (int i = 0; i < Helper.avoidedmonthlydata.size(); i++) {
            int count = 0;
            for (int j = i + 1; j < Helper.avoidedmonthlydata.size(); j++) {
                if ((Helper.avoidedmonthlydata.get(i)).equals(Helper.avoidedmonthlydata.get(j))) {
                    count++;
                    if (count > mostFre) {
                        mostFre = count;
                        val = j;
                    }
                    Log.d("aaaCount: ", "" + count + " i:" + i + " j:" + j);
                }
            }
        }
        Log.d("checkkrnaplz", "" + Helper.avoidedmonthlydata.size());
        if (Helper.avoidedmonthlydata.size() == 0) {
            return "";
        } else {
            return Helper.avoidedmonthlydata.get(val);
        }
    }

    public String getMonthlyDoneData(String date, String date2) {
        Log.d("splitteddate444 ", "" + date + " * " + date2);
        Helper.donemonthlydata = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM DONE WHERE DATE BETWEEN '" + date + "' AND '" + date2 + "'", null);
        while (cursor.moveToNext()) {
            Log.d("date", "" + cursor.getString(2));
            Helper.donemonthlydata.add(cursor.getString(2));
        }
        Log.d("split", "" + Helper.donemonthlydata.size());

        int mostFre = 0, val = 0;
        for (int i = 0; i < Helper.donemonthlydata.size(); i++) {
            int count = 0;
            for (int j = i + 1; j < Helper.donemonthlydata.size(); j++) {
                if ((Helper.donemonthlydata.get(i)).equals(Helper.donemonthlydata.get(j))) {
                    count++;
                    if (count > mostFre) {
                        mostFre = count;
                        val = j;
                    }
                    Log.d("aaaCount: ", "" + count + " i:" + i + " j:" + j);
                }
            }
        }
        Log.d("checkkrnaplz", "" + Helper.donemonthlydata.size());
        if (Helper.donemonthlydata.size() == 0) {
            return "";
        } else {
            return Helper.donemonthlydata.get(val);
        }
    }

    public String getYearlyAvoidedData(String date, String date2) {
        Log.d("splitteddate444 ", "" + date + " * " + date2);
        Helper.avoidedyearlydata = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM AVOIDED WHERE DATE BETWEEN '" + date + "' AND '" + date2 + "'", null);
        while (cursor.moveToNext()) {
            Log.d("date", "" + cursor.getString(2));
            Helper.avoidedyearlydata.add(cursor.getString(2));
        }
        Log.d("split", "" + Helper.avoidedyearlydata.size());

        int mostFre = 0, val = 0;
        for (int i = 0; i < Helper.avoidedyearlydata.size(); i++) {
            int count = 0;
            for (int j = i + 1; j < Helper.avoidedyearlydata.size(); j++) {
                if ((Helper.avoidedyearlydata.get(i)).equals(Helper.avoidedyearlydata.get(j))) {
                    count++;
                    if (count > mostFre) {
                        mostFre = count;
                        val = j;
                    }
                    Log.d("aaaCount: ", "" + count + " i:" + i + " j:" + j);
                }
            }
        }
        Log.d("checkkrnaplz", "" + Helper.avoidedyearlydata.size());
        if (Helper.avoidedyearlydata.size() == 0) {
            return "";
        } else {
            return Helper.avoidedyearlydata.get(val);
        }
    }

    public String getYearlyDoneData(String date, String date2) {
        Log.d("splitteddate444 ", "" + date + " * " + date2);
        Helper.doneyearlydata = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM DONE WHERE DATE BETWEEN '" + date + "' AND '" + date2 + "'", null);
        while (cursor.moveToNext()) {
            Log.d("date", "" + cursor.getString(2));
            Helper.doneyearlydata.add(cursor.getString(2));
        }
        Log.d("split", "" + Helper.doneyearlydata.size());

        int mostFre = 0, val = 0;
        for (int i = 0; i < Helper.doneyearlydata.size(); i++) {
            int count = 0;
            for (int j = i + 1; j < Helper.doneyearlydata.size(); j++) {
                if ((Helper.doneyearlydata.get(i)).equals(Helper.doneyearlydata.get(j))) {
                    count++;
                    if (count > mostFre) {
                        mostFre = count;
                        val = j;
                    }
                    Log.d("aaaCount: ", "" + count + " i:" + i + " j:" + j);
                }
            }
        }
        Log.d("checkkrnaplz", "" + Helper.doneyearlydata.size());
        if (Helper.doneyearlydata.size() == 0) {
            return "";
        } else {
            return Helper.doneyearlydata.get(val);
        }
    }

    public int countLabels(String catagory) {
        int count = 0;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM HABITS WHERE CATAGORY='" + catagory + "'", null);
        count = cursor.getCount();
        cursor.close();
        return count;
    }

}