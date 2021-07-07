package com.jorch.proyecto.aulavirtual.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by JORCH on 17/01/2017.
 */

public class ProfesorCurso {
    private String id;
    private String id_profesor;
    private String id_curso;

    public ProfesorCurso(){}

    public ProfesorCurso(String id, String id_profesor, String id_curso) {
        this.id = id;
        this.id_profesor = id_profesor;
        this.id_curso = id_curso;
    }

    public ProfesorCurso (Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.ProfesoresCursos.ID));
        id_profesor = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.ProfesoresCursos.ID_PROFESOR));
        id_curso = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.ProfesoresCursos.ID_CURSO));
    }
    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(AulaVirtualContract.ProfesoresCursos.ID,id);
        values.put(AulaVirtualContract.ProfesoresCursos.ID_CURSO,id_curso);
        values.put(AulaVirtualContract.ProfesoresCursos.ID_PROFESOR,id_profesor);
        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(String id_profesor) {
        this.id_profesor = id_profesor;
    }

    public String getId_curso() {
        return id_curso;
    }

    public void setId_curso(String id_curso) {
        this.id_curso = id_curso;
    }
}
