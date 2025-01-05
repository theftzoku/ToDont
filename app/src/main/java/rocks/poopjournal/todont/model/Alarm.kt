package rocks.poopjournal.todont.model

import rocks.poopjournal.todont.utils.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Alarm // Constructor
    (// Getters and setters
    var habitId:Int?,var alarmTime: Long, var frequency: String
) {
    val formattedAlarmTime: String
        // Optional: Convert alarm time to readable format (for display purposes)
        get() {
            val sdf =
                SimpleDateFormat(Constants.DATE_TIME_FORMAT, Locale.getDefault())
            return sdf.format(Date(alarmTime))
        }
}

