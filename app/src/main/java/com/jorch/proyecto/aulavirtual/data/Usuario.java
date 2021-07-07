package com.jorch.proyecto.aulavirtual.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Created by JORCH on 14/01/2017.
 */

public class Usuario implements Serializable{
    private String id;
    private String usuario;
    private String contraseña;
    private String correo;
    private String rol;
    public Usuario(){

    }
    public Usuario(String nombre, String contraseña, String correo, String rol) {
        this.usuario = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.rol = rol;
    }

    public Usuario(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.ColumnasUsuarios.ID));
        usuario = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.ColumnasUsuarios.USUARIO));
        contraseña = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.ColumnasUsuarios.CONTRASEÑA));
        correo = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.ColumnasUsuarios.CORREO));
        rol = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.ColumnasUsuarios.ROL));
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(AulaVirtualContract.ColumnasUsuarios.ID,id);
        values.put(AulaVirtualContract.ColumnasUsuarios.USUARIO, usuario);
        values.put(AulaVirtualContract.ColumnasUsuarios.CONTRASEÑA,contraseña);
        values.put(AulaVirtualContract.ColumnasUsuarios.CORREO,correo);
        values.put(AulaVirtualContract.ColumnasUsuarios.ROL,rol);
        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", correo='" + correo + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
