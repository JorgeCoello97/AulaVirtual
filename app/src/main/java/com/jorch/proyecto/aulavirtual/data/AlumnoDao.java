package com.jorch.proyecto.aulavirtual.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JORCH on 17/01/2017.
 */

public class AlumnoDao {
    private static AulaVirtualSQLiteHelper baseDatos;
    private static  AlumnoDao instance = new AlumnoDao();
    private AlumnoDao(){
    }
    public static AlumnoDao createInstance(Context context){
        if (baseDatos == null){
            baseDatos = new AulaVirtualSQLiteHelper(context);
        }
        return instance;
    }
    public String insertarAlumno(Alumno alumno){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String idAlumno = AulaVirtualContract.Alumnos.generarIdAlumno();
        alumno.setId(idAlumno);
        db.insert(AulaVirtualSQLiteHelper.Tablas.ALUMNOS,null,alumno.toContentValues());
        return idAlumno;
    }
    public String insertarAlumno(String user_id,String nombre,String apellidos, int edad,String direccion, int foto_perfil){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        Alumno alumno = new Alumno(user_id,nombre,apellidos,edad,direccion,foto_perfil);
        String idAlumno = AulaVirtualContract.Alumnos.generarIdAlumno();
        alumno.setId(idAlumno);
        db.insertOrThrow(AulaVirtualSQLiteHelper.Tablas.ALUMNOS,null,alumno.toContentValues());
        return idAlumno;
    }
    public boolean actualizarAlumno (Alumno alumno){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?",AulaVirtualContract.Alumnos.ID);
        String[] whereArgs ={alumno.getId()};
        int resultado = db.update(
                AulaVirtualSQLiteHelper.Tablas.ALUMNOS,
                alumno.toContentValues(),
                whereClause,
                whereArgs
        );
        return resultado > 0;
    }
    public boolean eliminarAlumno(String alumnoId, String userId){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?",AulaVirtualContract.Alumnos.ID);
        int resultado = db.delete(AulaVirtualSQLiteHelper.Tablas.ALUMNOS,whereClause,new String[]{alumnoId});

        whereClause = String.format("%s=?",AulaVirtualContract.Usuarios.ID);
        db.delete(AulaVirtualSQLiteHelper.Tablas.USUARIOS,whereClause,new String[]{userId});
        return resultado > 0;
    }
    public boolean eliminarAlumno(Alumno alumno){
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?",AulaVirtualContract.Alumnos.ID);
        int resultado = db.delete(AulaVirtualSQLiteHelper.Tablas.ALUMNOS,whereClause,new String[]{alumno.getId()});

        whereClause = String.format("%s=?",AulaVirtualContract.Alumnos.USER_ID);
        db.delete(AulaVirtualSQLiteHelper.Tablas.USUARIOS,whereClause,new String[]{alumno.getUser_id()});
        return resultado > 0;
    }

    public Cursor obtenerAlumno(String alumnoId){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?",
                AulaVirtualSQLiteHelper.Tablas.ALUMNOS,
                AulaVirtualContract.Alumnos.ID);
        return db.rawQuery(sql,new String[]{alumnoId});
    }
    public Alumno obtenerAlumnoByUsuario(String userIdBuscado){
        Cursor cursor = obtenerAllAlumnos();
        if (cursor.moveToFirst()){
            do {
                String id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Alumnos.ID));
                String user_id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Alumnos.USER_ID));
                String nombre = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Alumnos.NOMBRE));
                String apellidos = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Alumnos.APELLIDOS));
                int edad = cursor.getInt(cursor.getColumnIndex(AulaVirtualContract.Alumnos.EDAD));
                String direccion = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Alumnos.DIRECCION));
                int  foto_perfil = cursor.getInt(cursor.getColumnIndex(AulaVirtualContract.Alumnos.FOTO_PERFIL));
                String userId = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Alumnos.USER_ID));
                if (userId.equals(userIdBuscado)){
                    Alumno alumno = new Alumno(user_id,nombre,apellidos,edad,direccion,foto_perfil);
                    alumno.setId(id);
                    return alumno;
                }
            }while (cursor.moveToNext());
        }
        return null;
    }
    public Cursor obtenerAllAlumnos(){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        //String sql = String.format("SELECT * FROM %s", AulaVirtualSQLiteHelper.Tablas.USUARIOS);
        return db.query(AulaVirtualSQLiteHelper.Tablas.ALUMNOS,null,null,null,null,null,null);
    }
}
