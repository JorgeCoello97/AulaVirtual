package com.jorch.proyecto.aulavirtual.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Created by JORCH on 17/01/2017.
 */

public class Profesor implements Serializable{
    private String id;
    private String user_id;
    private String nombre;
    private String apellidos;
    private int edad;
    private String direccion;
    private int foto_perfil;

    public Profesor(String user_id, String nombre, String apellidos, int edad, String direccion, int foto_perfil) {
        this.id = AulaVirtualContract.Profesores.generarIdProfesor();
        this.user_id = user_id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.direccion = direccion;
        this.foto_perfil = foto_perfil;
    }
    public Profesor(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Profesores.ID));
        user_id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Profesores.USER_ID));
        nombre = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Profesores.NOMBRE));
        apellidos = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Profesores.APELLIDOS));
        edad = cursor.getInt(cursor.getColumnIndex(AulaVirtualContract.Profesores.EDAD));
        direccion = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Profesores.DIRECCION));
        foto_perfil = cursor.getInt(cursor.getColumnIndex(AulaVirtualContract.Profesores.FOTO_PERFIL));
    }
    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(AulaVirtualContract.Profesores.ID,id);
        values.put(AulaVirtualContract.Profesores.USER_ID,user_id);
        values.put(AulaVirtualContract.Profesores.NOMBRE,nombre);
        values.put(AulaVirtualContract.Profesores.APELLIDOS,apellidos);
        values.put(AulaVirtualContract.Profesores.EDAD,edad);
        values.put(AulaVirtualContract.Profesores.DIRECCION,direccion);
        values.put(AulaVirtualContract.Profesores.FOTO_PERFIL,foto_perfil);
        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(int foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad=" + edad +
                ", direccion='" + direccion + '\'' +
                ", foto_perfil=" + foto_perfil +
                '}';
    }
}
