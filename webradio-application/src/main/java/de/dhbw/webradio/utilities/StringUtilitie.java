package de.dhbw.webradio.utilities;

import java.io.UnsupportedEncodingException;

public class StringUtilitie {
    private static final String DEFAULT_CHARSET = "ISO-8859-1";

    /**
     *
     * @param toTrim the string to be trimmed
     * @return a new string without any illegal characters
     */
    public static String trimStringRightEnd(String toTrim) {
        int endingPosition = toTrim.length() - 1;
        char endingChar;
        while (endingPosition >= 0) {
            endingChar = toTrim.charAt(endingPosition);
            //check for invalid characters
            if (endingChar > 32) {
                break;
            }
            endingPosition--;
        }
        if (endingPosition == toTrim.length() - 1) {
            return toTrim;
        } else if (endingPosition < 0) {
            return "";
        }
        return toTrim.substring(0, endingPosition + 1);
    }

    public static String byteArrayToStringIgnoreEncodingIssue(byte[] bytesToConvert, int offset, int length) {
        try {
            return new String(bytesToConvert, offset, length, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void stringToByteArray(String s, int offset, int length, byte[] sourceBytes, int destOffset) throws UnsupportedEncodingException {
        String stringCopy = s.substring(offset, offset + length);
        byte[] destBytes = stringCopy.getBytes(DEFAULT_CHARSET);
        if (destBytes.length > 0) {
            System.arraycopy(destBytes, 0, sourceBytes, destOffset, destBytes.length);
        }

    }

}
