package com.jorch.proyecto.aulavirtual.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by JORCH on 21/01/2017.
 */

public class AsignaturaCursoDao {
    private static AulaVirtualSQLiteHelper baseDatos;
    private static AsignaturaCursoDao instance = new AsignaturaCursoDao();
    private AsignaturaCursoDao(){}

    public static AsignaturaCursoDao createInstance (Context context){
        if (baseDatos == null){
            baseDatos = new AulaVirtualSQLiteHelper(context);
        }
        return instance;
    }
    public void insertarAsignatura(Asignatura asignatura,Curso curso){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String idNuevoAsignaturaCurso = AulaVirtualContract.ProfesoresCursos.generarIdProfesorCurso();
        String idNuevaAsignatura = AulaVirtualContract.Asignaturas.generarIdAsignatura();
        asignatura.setId(idNuevaAsignatura);
        AsignaturaCurso asignaturaCurso = new AsignaturaCurso(idNuevoAsignaturaCurso,idNuevaAsignatura, curso.getId());
        db.insert(AulaVirtualSQLiteHelper.Tablas.ASIGNATURAS,null,asignatura.toContentValues());
        db.insert(AulaVirtualSQLiteHelper.Tablas.ASIGNATURAS_CURSOS,null,asignaturaCurso.toContentValues());
    }
    public Cursor obtenerAllAsignaturasByCursoId(String cursoId){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        String inners = "" +
                "asignaturas_cursos INNER JOIN asignaturas ON asignaturas_cursos.asignatura_id = asignaturas.id " +
                "INNER JOIN cursos ON asignaturas_cursos.curso_id = cursos.id";
        builder.setTables(inners);
        String[] columnas = {
                AulaVirtualSQLiteHelper.Tablas.ASIGNATURAS+"."+AulaVirtualContract.Asignaturas.ID,
                AulaVirtualSQLiteHelper.Tablas.ASIGNATURAS+"."+AulaVirtualContract.Asignaturas.NOMBRE,
                AulaVirtualSQLiteHelper.Tablas.ASIGNATURAS+"."+AulaVirtualContract.Asignaturas.DESCRIPCION,
                AulaVirtualContract.Asignaturas.FOTO_ASIGNATURA,
                AulaVirtualContract.Asignaturas.FECHA_INICIO,
                AulaVirtualContract.Asignaturas.FECHA_FIN,
                AulaVirtualContract.Asignaturas.HORA_INICIO,
                AulaVirtualContract.Asignaturas.HORA_FIN
        };
        String whereClause = String.format("%s=?", AulaVirtualContract.AsignaturasCursos.ID_CURSO);
        String[] whereArgs = {cursoId};
        return builder.query(db,columnas,whereClause,whereArgs,null,null,null);
    }

}
