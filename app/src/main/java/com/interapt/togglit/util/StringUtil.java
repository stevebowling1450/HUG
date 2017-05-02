package com.interapt.togglit.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;

import org.apache.commons.lang3.text.WordUtils;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by miller.barrera on 27/10/2016.
 */

public class StringUtil {


    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6})$";
    private static SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
    private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private static SimpleDateFormat dayNumberFormat = new SimpleDateFormat("dd");
    private static SimpleDateFormat dayNameFormat = new SimpleDateFormat("EE");
    private static SimpleDateFormat timeNumberFormat = new SimpleDateFormat("HH:mm");

    public static Pattern getEmailPattern() {
        return Pattern.compile("^[._A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    public static boolean isNullOrEmpty(String input) {
        return input == null || input.isEmpty();
    }

    /**
     * Validate hex with regular expression
     *
     * @return true valid hex, false invalid hex
     */
    public static Pattern getHexColorPattern() {
        return Pattern.compile(HEX_PATTERN);

    }

    public static Calendar getCurrentDate() {
        Calendar c = Calendar.getInstance();
        return c;
    }

    public static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month.toUpperCase();
    }

    public static String getOnlyDayFromDate(Date dateFormat, boolean isOnlyNumber) {
        String day = "";
        if (dateFormat != null) {
            if (isOnlyNumber) {
                day = dayNumberFormat.format(dateFormat);
            } else {
                day = dayNameFormat.format(dateFormat).toUpperCase();
            }

        }

        return day;
    }

    public static String getOnlyMonthFromDate(Date dateFormat) {
        String month = "";
        if (dateFormat != null) {
            month = monthFormat.format(dateFormat);
        }

        return month;
    }

    @NonNull
    public static String getOnlyYearFromDate(Date dateFormat) {
        String year = "";
        if (dateFormat != null) {
            year = yearFormat.format(dateFormat);
        }

        return year.toUpperCase();
    }

    public static String getOnlyTimeFromDate(Date dateFormat) {
        String time = "";
        if (dateFormat != null) {
            time = getDateFromUTCTimestamp(dateFormat.getTime(), "HH:mm");
        }

        return time;
    }

    public static String getDateFromUTCTimestamp(long mTimestamp, String mDateFormate) {
        String date = null;
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.setTimeInMillis(mTimestamp);
            date = android.text.format.DateFormat.format(mDateFormate, cal.getTimeInMillis()).toString();

            SimpleDateFormat formatter = new SimpleDateFormat(mDateFormate);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(date);

            SimpleDateFormat dateFormatter = new SimpleDateFormat(mDateFormate);
            dateFormatter.setTimeZone(TimeZone.getDefault());
            date = dateFormatter.format(value);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    public static long getTimeInMillis(String dateInString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.getTime();
    }

    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        //String datezString = dateString.replace("Z", "GMT+00:00");
        Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

    public static String capitalizeString(String string) {
        return WordUtils.capitalize(string);
    }

    public static String toTwelveHourFormat(String hourOfDay) {
        String s = hourOfDay;
        DateFormat f1 = new SimpleDateFormat("HH:mm"); //HH for hour of the day (0 - 23)
        Date d = null;
        try {
            d = f1.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat f2 = new SimpleDateFormat("h:mma");

        return f2.format(d).toLowerCase(); // "12:18am"
    }

    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }


    //image utils

    static String image = "";
    // Utility for resizing images.
    public static Bitmap resizeImage(Bitmap image) {
        image = Bitmap.createScaledBitmap(image, 100, 100, false);
        return image;
    }
    // Utility for decoding images.
    public static Bitmap decodeImage(String encodedString) {
        byte[] decodedString;
        Bitmap decodedByte;
        if (encodedString != null) {
            if (encodedString.contains(",")) {
                String[] encImage = encodedString.split(",");
                decodedString = Base64.decode(encImage[1], Base64.DEFAULT);
            } else {
                if (encodedString.length() > 200) {
                    decodedString = Base64.decode(encodedString, Base64.DEFAULT);
                } else {
                    decodedString = null;
                }
            }
            if (decodedString != null) {
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                decodedByte = resizeImage(decodedByte);
                return decodedByte;
            }
        }
        return null;
    }
    // Utility for encoding images into base64.
    public static String encodeImage(Bitmap bitmap) {
        bitmap = resizeImage(bitmap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
