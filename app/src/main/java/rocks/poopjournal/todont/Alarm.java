package rocks.poopjournal.todont;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Alarm {
    private int taskId;
    private long alarmTime;
    private String frequency;

    // Constructor
    public Alarm(int taskId, long alarmTime, String frequency) {
        this.taskId = taskId;
        this.alarmTime = alarmTime;
        this.frequency = frequency;
    }

    // Getters and setters
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public long getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    // Optional: Convert alarm time to readable format (for display purposes)
    public String getFormattedAlarmTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(new Date(alarmTime));
    }
}

