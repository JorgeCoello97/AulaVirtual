package com.jorch.proyecto.aulavirtual.utils;

import java.util.Random;

/**
 * Created by JORCH on 19/01/2017.
 */

public class RandomUtils
{
    private static Random r;
    public static String generarCodigo(){
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        r = new Random(milis);
        int i = 0;
        while ( i < 4){
            char c = (char)r.nextInt(255);
            if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
                cadenaAleatoria += c;
                i ++;
            }
        }
        return cadenaAleatoria;
    }
}
