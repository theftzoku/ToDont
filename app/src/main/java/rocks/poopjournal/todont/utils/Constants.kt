package rocks.poopjournal.todont.utils;


public class Constants {

    companion object {
            const val DATE_FORMAT = "yyyy-MM-dd"
            const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm"
            const val INITIAL_DATE_KEY = "InitialDate"
            const val NO = "no"
            const val YES = "yes"
            const val APP_THEME_PRIMARY_COLOR = "app_theme_clr"
            const val APP_THEME_SECONDARY_COLOR = "app_theme_clr2"
            const val APP_DISABLE_PRIMARY_COLOR = "app_disable_clr"
            const val APP_DISABLE_SECONDARY_COLOR = "app_disable_clr2"

            const val APP_TAG_LINE = "app_tag_line"
            const val DEFAULT_APP_COLOR = "#08a27b"
            const val DEFAULT_APP_COLOR2 = "#26bf62"
            const val DEFAULT_APP_DISABLE_COLOR = "#DCDCDC"
            const val DEFAULT_APP_DISABLE_COLOR2 = "#FFFFFF"

            const val NOTIFICATION_TIME_FORMAT="Time: %02d:%02d, Frequency: %s"
    }

}

enum class ThemeMode(var value:String){
        LIGHT_MODE ("1"),
        DARK_MODE  ("2"),
        FOLLOW_SYS ("3"),
        DRACULA    ("4")
}

enum class HabitStatus(var value: String){
        AVOIDED("Avoided"),DONE("Done")
}
