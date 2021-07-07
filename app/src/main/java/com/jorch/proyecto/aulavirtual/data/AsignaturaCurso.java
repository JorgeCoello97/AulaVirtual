package com.jorch.proyecto.aulavirtual.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by JORCH on 17/01/2017.
 */

public class AsignaturaCurso {
    private String id;
    private String id_asignatura;
    private String id_curso;

    public AsignaturaCurso(){};

    public AsignaturaCurso(String id, String id_asignatura, String id_curso) {
        this.id = id;
        this.id_asignatura = id_asignatura;
        this.id_curso = id_curso;
    }
    public AsignaturaCurso(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.AsignaturasCursos.ID));
        id_asignatura = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.AsignaturasCursos.ASIGNATURA_ID));
        id_curso = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.AsignaturasCursos.ID_CURSO));
    }
    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(AulaVirtualContract.AsignaturasCursos.ID,id);
        values.put(AulaVirtualContract.AsignaturasCursos.ASIGNATURA_ID,id_asignatura);
        values.put(AulaVirtualContract.AsignaturasCursos.ID_CURSO,id_curso);
        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_asignatura() {
        return id_asignatura;
    }

    public void setId_asignatura(String id_asignatura) {
        this.id_asignatura = id_asignatura;
    }

    public String getId_curso() {
        return id_curso;
    }

    public void setId_curso(String id_curso) {
        this.id_curso = id_curso;
    }
}
