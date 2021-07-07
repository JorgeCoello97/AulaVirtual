package com.jorch.proyecto.aulavirtual.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Created by JORCH on 19/01/2017.
 */

public class EncriptarUtils {
    private static MessageDigest messageDigest;
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    //ENCRIPTA
    public static String encriptarCadena(String cadena){
        byte[] cadenaEnBytes = null;
       try {
           messageDigest = MessageDigest.getInstance("MD5");
           messageDigest.update(cadena.getBytes());
           cadenaEnBytes = messageDigest.digest();

       } catch (NoSuchAlgorithmException e){
           e.printStackTrace();
       }
        String resultado = bytesToHex(cadenaEnBytes);
        return resultado;
    }
    //TRANFORMA BYTES EN HEXA
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
