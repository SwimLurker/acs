package org.slstudio.acs.kernal.session.idgenerator;

import org.slstudio.acs.kernal.exception.SessionException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ÉÏÎç1:01
 */
public class DefaultSessionIDGenerator implements ISessionIDGenerator {
    private static String baseUUID = null;
    private static long incrementingValue = 0;
    private static Random myRand = null;

    public synchronized String generateSessionID() throws SessionException {
        if (baseUUID == null) {
            baseUUID = getInitialUUID();
        }
        if(++incrementingValue >= Long.MAX_VALUE){
            incrementingValue = 0;
        }
        return baseUUID + new Date().getTime() + incrementingValue;
    }

    protected String getInitialUUID() throws SessionException{
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
            throw new SessionException("ID generator algorithm not found", e);
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
}
