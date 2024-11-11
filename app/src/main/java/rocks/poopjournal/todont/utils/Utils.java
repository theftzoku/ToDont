package rocks.poopjournal.todont.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static final String TOKEN = "c2VudGluZWwyMDE2";
    public static final String REQUEST = "ios";
    static File tempFile;

    public static String getThemeColor(Context context) {
        SharedPrefUtils prefUtils = new SharedPrefUtils(context);
        return prefUtils.getString(Constant.APP_THEME_PRIMARY_COLOR, Constant.DEFAULT_APP_COLOR);
    }

    public static String getThemeColor2(Context context) {
        SharedPrefUtils prefUtils = new SharedPrefUtils(context);
        return prefUtils.getString(Constant.APP_THEME_SECONDARY_COLOR, Constant.DEFAULT_APP_COLOR2);
    }

    public static String getDisableThemeColor(Context context) {
        SharedPrefUtils prefUtils = new SharedPrefUtils(context);
        return prefUtils.getString(Constant.APP_DISABLE_PRIMARY_COLOR, Constant.DEFAULT_APP_DISABLE_COLOR);
    }

    public static String getDisableTheme2Color(Context context) {
        SharedPrefUtils prefUtils = new SharedPrefUtils(context);
        return prefUtils.getString(Constant.APP_DISABLE_SECONDARY_COLOR, Constant.DEFAULT_APP_DISABLE_COLOR2);
    }

    public static GradientDrawable getGradient(String Color1, String Color2, float radius, GradientDrawable.Orientation orientation, int gradientType, int strokeWidth, String strokeClr) {
        int[] colors = {Color.parseColor(Color1), Color.parseColor(Color2)};
        GradientDrawable gd = new GradientDrawable(orientation, colors); //orientation GradientDrawable.Orientation.TOP_BOTTOM
        gd.setStroke(strokeWidth, Color.parseColor(strokeClr));
        gd.setGradientType(gradientType); //gradientType = GradientDrawable.LINEAR_GRADIENT;
        gd.setCornerRadius(radius);
        return gd;
    }

    public static String changeDateFormate(String inputPattern, String outputPattern, String stringDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        String time = "";
        Date date = null;
        try {
            date = inputFormat.parse(stringDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            time = outputFormat.format(date);
        }
        return time;
    }

    public static Date getDate(String sdf, String dateInString) {
        SimpleDateFormat format = new SimpleDateFormat(sdf, Locale.ENGLISH);
        Date dt = null;
        try {
            dt = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static String getStringDate(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        return sdf.format(date);

    }






    public static Drawable changeDrawableColor(Context mContext, int drawable, int color) {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(mContext, drawable);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return unwrappedDrawable;
    }

    public static Drawable changeTrackColor(Context context, int drawableId) {
        Drawable changeClr1 = Utils.changeDrawableColor(context, drawableId, Color.parseColor(getThemeColor(context)));
        return changeClr1;
    }

}
