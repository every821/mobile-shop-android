package com.bateeqshop.utility;

import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * Created by Daniel.Nababan on 6/8/2016.
 */
public class NumberFormatter {
    public static String formatNumber(Object number, Locale locale){
        NumberFormat formatter = NumberFormat.getInstance(locale);
        return formatter.format(number);
    }
}
