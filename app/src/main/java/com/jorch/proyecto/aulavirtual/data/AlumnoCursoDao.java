package com.jorch.proyecto.aulavirtual.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by JORCH on 18/01/2017.
 */

public class AlumnoCursoDao {
    private static AulaVirtualSQLiteHelper baseDatos;
    private static AlumnoCursoDao instance = new AlumnoCursoDao();
    private AlumnoCursoDao(){}

    public static AlumnoCursoDao createInstance (Context context){
        if (baseDatos == null){
            baseDatos = new AulaVirtualSQLiteHelper(context);
        }
        return instance;
    }
    public Cursor obtenerAllCursosByAlumnoId(String alumnoId){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        String inners = "" +
                "alumnos_cursos INNER JOIN alumnos ON alumnos_cursos.id_alumno = alumnos.id " +
                "INNER JOIN cursos ON alumnos_cursos.id_curso = cursos.id";
        builder.setTables(inners);
        String[] columnas = {
                AulaVirtualSQLiteHelper.Tablas.CURSOS+"."+AulaVirtualContract.Cursos.ID,
                AulaVirtualSQLiteHelper.Tablas.CURSOS+"."+AulaVirtualContract.Cursos.NOMBRE,
                AulaVirtualContract.Cursos.DESCRIPCION,
                AulaVirtualContract.Cursos.FOTO_CURSO,
                AulaVirtualContract.Cursos.CODIGO_CURSO
        };
        String whereClause = String.format("%s=?", AulaVirtualContract.AlumnosCursos.ID_ALUMNO);
        String[] whereArgs = {alumnoId};
        return builder.query(db,columnas,whereClause,whereArgs,null,null,null);
    }
    public void insertarCurso(Curso curso,String alumnoId){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String idAlumnoCurso = AulaVirtualContract.AlumnosCursos.generarIdAlumnoCurso();
        AlumnoCurso alumnoCurso = new AlumnoCurso(alumnoId,curso.getId());
        alumnoCurso.setId(idAlumnoCurso);
        db.insert(AulaVirtualSQLiteHelper.Tablas.ALUMNOS_CURSOS,null,alumnoCurso.toContentValues());
    }
}
