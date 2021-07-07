package com.jorch.proyecto.aulavirtual.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jorch.proyecto.aulavirtual.data.Usuario;

/**
 * Created by JORCH on 14/01/2017.
 */

public class SesionPrefs {
    public static final String PREFS_NAME = "AULAVIRTUAL_PREFS";
    public static final String PREF_USUARIO_ID = "PREF_USUARIO_ID";
    public static final String PREF_USUARIO_NAME = "PREF_USUARIO_NAME";
    public static final String PREF_USUARIO_PASSWORD = "PREF_USUARIO_PASSWORD";
    public static final String PREF_USUARIO_CORREO = "PREF_USUARIO_CORREO";
    public static final String PREF_USUARIO_ROL = "PREF_USUARIO_ROL";

    private final SharedPreferences preferences;
    private boolean isLoggedIn = false;
    private static SesionPrefs INSTANCE;


    public static SesionPrefs get(Context context){
        if (INSTANCE == null){
            INSTANCE = new SesionPrefs(context);
        }
        return INSTANCE;
    }

    private SesionPrefs(Context context){
        preferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    public void saveLogInUsuario(Usuario usuario){
        //GUARDO EN PREFERENCIAS EL USUARIO
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USUARIO_NAME,usuario.getUsuario());
        editor.putString(PREF_USUARIO_PASSWORD,usuario.getContraseña());
        editor.apply();
        isLoggedIn = true;
    }
    public void logOutUsuario(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USUARIO_NAME,null);
        editor.putString(PREF_USUARIO_PASSWORD,null);
        editor.apply();
        isLoggedIn = false;
    }

}
