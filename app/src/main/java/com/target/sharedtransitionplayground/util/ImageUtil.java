package com.target.sharedtransitionplayground.util;

import android.support.annotation.IntRange;
import android.support.annotation.Px;

import java.util.Locale;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ImageUtil {

    private final static String THUMB_URL = "http://target.scene7.com/is/image/Target/50277815?wid=%d&hei=%d&qlt=%d&fmt=pjpeg";

    public static String getUrl(@Px int size,
                                     @IntRange(from = 1, to = 100) int quality) {
        return String.format(Locale.US, THUMB_URL, size, size , quality);
    }

    private ImageUtil() {
    }
}
