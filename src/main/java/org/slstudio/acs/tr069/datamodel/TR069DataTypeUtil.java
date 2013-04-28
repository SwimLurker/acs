package org.slstudio.acs.tr069.datamodel;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ÏÂÎç12:03
 */
public class TR069DataTypeUtil {
    public static String toISO8601DateTime(Calendar calendar) {
        return toISO8601DateTime(calendar.getTime());
    }

    public static String toISO8601DateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(date);
    }

    public static String encodeBASE64(byte[] data) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static byte[] decodeBASE64(String data) throws java.io.IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(data);
    }


    public static Boolean convertToBoolean(String value) {
        if ("true".equalsIgnoreCase(value) || "1".equals(value)) {
            return Boolean.TRUE;
        }
        if("false".equalsIgnoreCase(value) || "0".equals(value)) {
            return Boolean.FALSE;
        }
        throw new IllegalArgumentException();
    }
}