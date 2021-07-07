package com.jorch.proyecto.aulavirtual.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by JORCH on 18/01/2017.
 */

public class CursoDao
{
    private static AulaVirtualSQLiteHelper baseDatos;
    private static CursoDao instance = new CursoDao();
    private CursoDao(){}

    public static CursoDao createInstance (Context context){
        if (baseDatos == null){
            baseDatos = new AulaVirtualSQLiteHelper(context);
        }
        return instance;
    }

    public Cursor obtenerCodigoCurso(String codigo){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String whereClause = String.format("%s=?", AulaVirtualContract.Cursos.CODIGO_CURSO);
        String[] whereArgs = {codigo};
        return db.query(AulaVirtualSQLiteHelper.Tablas.CURSOS,null,whereClause,whereArgs,null,null,null);
    }
    public Curso obtenerCursobyCodigoCurso(String codigoCurso){
        Cursor cursor = obtenerCodigoCurso(codigoCurso);
        if (cursor.moveToFirst()) {
            do {
                String codigo = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.CODIGO_CURSO));
                if (codigo.equals(codigoCurso)){
                    break;
                }
            }while (cursor.moveToNext());
        }
        Curso curso = new Curso(cursor);
        return curso;
    }
    public  boolean comprobarCodigoCurso(String codigo){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String whereClause = String.format("%s=?", AulaVirtualContract.Cursos.CODIGO_CURSO);
        String[] whereArgs = {codigo};
        Cursor cursor = db.query(AulaVirtualSQLiteHelper.Tablas.CURSOS,new String[]{AulaVirtualContract.Cursos.CODIGO_CURSO}
        ,whereClause,whereArgs,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String codigoCurso = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.CODIGO_CURSO));
                if (codigo.equals(codigoCurso)){
                    cursor.close();
                    return true;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }


}
