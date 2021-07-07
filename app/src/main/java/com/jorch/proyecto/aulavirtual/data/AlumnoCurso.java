package com.jorch.proyecto.aulavirtual.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by JORCH on 17/01/2017.
 */

public class AlumnoCurso {
    private String id;
    private String id_alumno;
    private String id_curso;
    public AlumnoCurso(){}
    public AlumnoCurso(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.AlumnosCursos.ID));
        id_alumno = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.AlumnosCursos.ID_ALUMNO));
        id_curso = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.AlumnosCursos.ID_CURSO));
    }
    public AlumnoCurso(String id_alumno, String id_curso) {
        this.id_alumno = id_alumno;
        this.id_curso = id_curso;
    }
    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(AulaVirtualContract.AlumnosCursos.ID,id);
        values.put(AulaVirtualContract.AlumnosCursos.ID_ALUMNO,id_alumno);
        values.put(AulaVirtualContract.AlumnosCursos.ID_CURSO,id_curso);
        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(String id_alumno) {
        this.id_alumno = id_alumno;
    }

    public String getId_curso() {
        return id_curso;
    }

    public void setId_curso(String id_curso) {
        this.id_curso = id_curso;
    }
}
