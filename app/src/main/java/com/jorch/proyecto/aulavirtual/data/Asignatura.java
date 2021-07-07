package com.jorch.proyecto.aulavirtual.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Created by JORCH on 17/01/2017.
 */

public class Asignatura implements Serializable{
    private String id;
    private String nombre;
    private String descripcion;
    private int foto_asignatura;
    private String fecha_inicio;
    private String fecha_fin;
    private String hora_inicio;
    private String hora_fin;

    public Asignatura(){};

    public Asignatura(String nombre, String descripcion, int foto_asignatura, String fecha_inicio, String fecha_fin, String hora_inicio, String hora_fin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto_asignatura = foto_asignatura;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
    }

    public Asignatura(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.ID));
        nombre = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.NOMBRE));
        descripcion = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.DESCRIPCION));
        foto_asignatura = cursor.getInt(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.FOTO_ASIGNATURA));
        fecha_inicio = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.FECHA_INICIO));
        fecha_fin = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.FECHA_FIN));
        hora_inicio = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.HORA_INICIO));
        hora_fin = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.HORA_FIN));
    }
    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(AulaVirtualContract.Asignaturas.ID,id);
        values.put(AulaVirtualContract.Asignaturas.NOMBRE,nombre);
        values.put(AulaVirtualContract.Asignaturas.DESCRIPCION,descripcion);
        values.put(AulaVirtualContract.Asignaturas.FOTO_ASIGNATURA,foto_asignatura);
        values.put(AulaVirtualContract.Asignaturas.FECHA_INICIO,fecha_inicio);
        values.put(AulaVirtualContract.Asignaturas.FECHA_FIN,fecha_fin);
        values.put(AulaVirtualContract.Asignaturas.HORA_INICIO,hora_inicio);
        values.put(AulaVirtualContract.Asignaturas.HORA_FIN,hora_fin);
        return values;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFoto_asignatura() {
        return foto_asignatura;
    }

    public void setFoto_asignatura(int foto_asignatura) {
        this.foto_asignatura = foto_asignatura;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", foto_asignatura=" + foto_asignatura +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", fecha_fin='" + fecha_fin + '\'' +
                ", hora_inicio='" + hora_inicio + '\'' +
                ", hora_fin='" + hora_fin + '\'' +
                '}';
    }
}
