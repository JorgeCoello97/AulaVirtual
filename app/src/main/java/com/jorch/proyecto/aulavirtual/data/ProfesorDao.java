package com.jorch.proyecto.aulavirtual.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JORCH on 17/01/2017.
 */

public class ProfesorDao {
    private static AulaVirtualSQLiteHelper baseDatos;
    private static  ProfesorDao instance = new ProfesorDao();
    private ProfesorDao(){
    }
    public static ProfesorDao createInstance(Context context){
        if (baseDatos == null){
            baseDatos = new AulaVirtualSQLiteHelper(context);
        }
        return instance;
    }
    public String insertarProfesor(Profesor profesor){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String idProfesor = AulaVirtualContract.Profesores.generarIdProfesor();
        profesor.setId(idProfesor);
        db.insert(AulaVirtualSQLiteHelper.Tablas.PROFESORES,null,profesor.toContentValues());
        return idProfesor;
    }
    public String insertarProfesor(String user_id,String nombre,String apellidos, int edad,String direccion, int foto_perfil){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        Profesor profesor = new Profesor(user_id,nombre,apellidos,edad,direccion,foto_perfil);
        String idProfesor = AulaVirtualContract.Alumnos.generarIdAlumno();
        profesor.setId(idProfesor);
        db.insertOrThrow(AulaVirtualSQLiteHelper.Tablas.PROFESORES,null,profesor.toContentValues());
        return idProfesor;
    }
    public boolean actualizarProfesor (Profesor profesor){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?",AulaVirtualContract.Profesores.ID);
        String[] whereArgs ={profesor.getId()};
        int resultado = db.update(
                AulaVirtualSQLiteHelper.Tablas.PROFESORES,
                profesor.toContentValues(),
                whereClause,
                whereArgs
        );
        return resultado > 0;
    }
    public boolean eliminarProfesor(String profesorId, String userId){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?",AulaVirtualContract.Profesores.ID);
        int resultado = db.delete(AulaVirtualSQLiteHelper.Tablas.PROFESORES,whereClause,new String[]{profesorId});

        whereClause = String.format("%s=?",AulaVirtualContract.Usuarios.ID);
        db.delete(AulaVirtualSQLiteHelper.Tablas.USUARIOS,whereClause,new String[]{userId});
        return resultado > 0;
    }
    public boolean eliminarProfesor(Profesor profesor){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?",AulaVirtualContract.Profesores.ID);
        int resultado = db.delete(AulaVirtualSQLiteHelper.Tablas.PROFESORES,whereClause,new String[]{profesor.getId()});

        whereClause = String.format("%s=?",AulaVirtualContract.Profesores.USER_ID);
        db.delete(AulaVirtualSQLiteHelper.Tablas.USUARIOS,whereClause,new String[]{profesor.getUser_id()});
        return resultado > 0;
    }

    public Cursor obtenerProfesor(String profesorId){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?",
                AulaVirtualSQLiteHelper.Tablas.PROFESORES,
                AulaVirtualContract.Profesores.ID);
        return db.rawQuery(sql,new String[]{profesorId});
    }
    public Profesor obtenerProfesorByUsuario(String userIdBuscado){
        Cursor cursor = obtenerAllProfesores();
        if (cursor.moveToFirst()){
            do {
                String userId = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Profesores.USER_ID));
                if (userId.equals(userIdBuscado)) {
                    return new Profesor(cursor);
                }
            }while (cursor.moveToNext());
        }
        return null;
    }
    public Cursor obtenerAllProfesores(){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        //String sql = String.format("SELECT * FROM %s", AulaVirtualSQLiteHelper.Tablas.USUARIOS);
        return db.query(AulaVirtualSQLiteHelper.Tablas.PROFESORES,null,null,null,null,null,null);
    }
}
