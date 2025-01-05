package rocks.poopjournal.todont.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    const val TOKEN: String = "c2VudGluZWwyMDE2"
    const val REQUEST: String = "ios"
    var tempFile: File? = null

    fun getThemeColor(context: Context): String? {
        val prefUtils = SharedPrefUtils(context)
        return prefUtils.getString(Constants.APP_THEME_PRIMARY_COLOR, Constants.DEFAULT_APP_COLOR)
    }

    fun getThemeColor2(context: Context): String? {
        val prefUtils = SharedPrefUtils(context)
        return prefUtils.getString(
            Constants.APP_THEME_SECONDARY_COLOR,
            Constants.DEFAULT_APP_COLOR2
        )
    }

    fun getDisableThemeColor(context: Context): String? {
        val prefUtils = SharedPrefUtils(context)
        return prefUtils.getString(
            Constants.APP_DISABLE_PRIMARY_COLOR,
            Constants.DEFAULT_APP_DISABLE_COLOR
        )
    }

    fun getDisableTheme2Color(context: Context): String? {
        val prefUtils = SharedPrefUtils(context)
        return prefUtils.getString(
            Constants.APP_DISABLE_SECONDARY_COLOR,
            Constants.DEFAULT_APP_DISABLE_COLOR2
        )
    }

    fun getGradient(
        Color1: String?,
        Color2: String?,
        radius: Float,
        orientation: GradientDrawable.Orientation?,
        gradientType: Int,
        strokeWidth: Int,
        strokeClr: String?
    ): GradientDrawable {
        val colors = intArrayOf(Color.parseColor(Color1), Color.parseColor(Color2))
        val gd = GradientDrawable(
            orientation,
            colors
        ) //orientation GradientDrawable.Orientation.TOP_BOTTOM
        gd.setStroke(strokeWidth, Color.parseColor(strokeClr))
        gd.gradientType = gradientType //gradientType = GradientDrawable.LINEAR_GRADIENT;
        gd.cornerRadius = radius
        return gd
    }

    fun changeDateFormate(
        inputPattern: String?,
        outputPattern: String?,
        stringDate: String
    ): String {
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        var time = ""
        var date: Date? = null
        try {
            date = inputFormat.parse(stringDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (date != null) {
            time = outputFormat.format(date)
        }
        return time
    }

    fun getDate(sdf: String?, dateInString: String): Date? {
        val format = SimpleDateFormat(sdf, Locale.ENGLISH)
        var dt: Date? = null
        try {
            dt = format.parse(dateInString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dt
    }

    fun getStringDate(format: String?, date: Date): String {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        return sdf.format(date)
    }


    fun changeDrawableColor(mContext: Context, drawable: Int, color: Int): Drawable {
        val unwrappedDrawable = checkNotNull(AppCompatResources.getDrawable(mContext, drawable))
        val wrappedDrawable = DrawableCompat.wrap(
            unwrappedDrawable
        )
        DrawableCompat.setTint(wrappedDrawable, color)
        return unwrappedDrawable
    }

    fun changeTrackColor(context: Context, drawableId: Int): Drawable {
        val changeClr1 = changeDrawableColor(
            context, drawableId, Color.parseColor(
                getThemeColor(context)
            )
        )
        return changeClr1
    }
}
