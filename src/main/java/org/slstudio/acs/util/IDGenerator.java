package org.slstudio.acs.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-31
 * Time: ����1:07
 */
public class IDGenerator {
    private static String baseUUID = null;
    private static long incrementingValue = 0;
    private static Random myRand = null;
    public static String getNewID() {
        if (baseUUID == null) {
            baseUUID = getInitialUUID();
        }
        if(++incrementingValue >= Long.MAX_VALUE){
            incrementingValue = 0;
        }
        return baseUUID + new Date().getTime() + incrementingValue;
    }

    protected static String getInitialUUID() {
        if (myRand == null) {
            myRand = new Random();
        }
        long rand = myRand.nextLong();
        String sid;
        try {
            sid = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            sid = Thread.currentThread().getName();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(sid);
        sb.append(":");
        sb.append(Long.toString(rand));
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            //System.out.println("Error: " + e);
            //todo heve to be properly handle
        }
        md5.update(sb.toString().getBytes());
        byte[] array = md5.digest();
        StringBuffer sb2 = new StringBuffer();
        for (int j = 0; j < array.length; ++j) {
            int b = array[j] & 0xFF;
            sb2.append(Integer.toHexString(b));
        }
        int begin = myRand.nextInt();
        if (begin < 0) begin = begin * -1;
        begin = begin % 8;
        return sb2.toString().substring(begin, begin + 18).toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(IDGenerator.getNewID());
    }
}
