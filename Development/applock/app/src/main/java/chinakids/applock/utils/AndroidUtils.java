package chinakids.applock.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.SparseArray;

/**
 * Created by fyunli on 15/4/29.
 */
public class AndroidUtils {

    private static final SparseArray<Typeface> typefacesCache = new SparseArray<>();

    public static Typeface getTypeface(Context context, int typeface) {
        synchronized (typefacesCache) {
            if (typefacesCache.indexOfKey(typeface) < 0) {
                typefacesCache.put(typeface, AndroidUtils.createTypeface(context, typeface));
            }

            return typefacesCache.get(typeface);
        }
    }

    /**
     * Returns the screen width in pixels
     *
     * @param context is the context to get the resources
     * @return the screen width in pixels
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        return metrics.widthPixels;
    }

    /**
     * Returns the size in pixels of an attribute dimension
     *
     * @param context the context to get the resources from
     * @param attr    is the attribute dimension we want to know the size from
     * @return the size in pixels of an attribute dimension
     */
    public static int getThemeAttributeDimensionSize(Context context, int attr) {
        TypedArray a = null;
        try {
            a = context.getTheme().obtainStyledAttributes(new int[]{attr});
            return a.getDimensionPixelSize(0, 0);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public static Typeface createTypeface(Context context, int typeface) {
        return Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s.ttf", context.getString(typeface)));
    }
}
