package com.jorch.proyecto.aulavirtual.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by JORCH on 14/01/2017.
 */

public class AulaVirtualSQLiteHelper extends SQLiteOpenHelper{
    private final Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Jorch.db";

    interface Tablas{
        String USUARIOS = "usuarios";
        String ALUMNOS = "alumnos";
        String PROFESORES = "profesores";
        String CURSOS = "cursos";
        String ALUMNOS_CURSOS = "alumnos_cursos";
        String PROFESORES_CURSOS = "profesores_cursos";
        String ASIGNATURAS = "asignaturas";
        String ASIGNATURAS_CURSOS = "asignaturas_cursos";
    }
    interface Referencias {
        String ID_USER = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                Tablas.USUARIOS,AulaVirtualContract.Usuarios.ID);
        String ID_ALUMNO = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                Tablas.ALUMNOS,AulaVirtualContract.Alumnos.ID);
        String ID_PROFESOR = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                Tablas.PROFESORES,AulaVirtualContract.Profesores.ID);
        String ID_ASIGNATURA = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                Tablas.ASIGNATURAS,AulaVirtualContract.Asignaturas.ID);
        String ID_CURSO = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                Tablas.CURSOS,AulaVirtualContract.Cursos.ID);
    }

    public AulaVirtualSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (" +
                "%s TEXT PRIMARY KEY,%s TEXT NOT NULL," +
                "%s TEXT NOT NULL,%s TEXT," +
                "%s TEXT NOT NULL);",
                Tablas.USUARIOS,
                AulaVirtualContract.Usuarios.ID,AulaVirtualContract.Usuarios.USUARIO,
                AulaVirtualContract.Usuarios.CONTRASEÑA,AulaVirtualContract.Usuarios.CORREO,
                AulaVirtualContract.Usuarios.ROL));
        db.execSQL(String.format("CREATE TABLE %s (" +
                "%s TEXT PRIMARY KEY,%s TEXT," +
                "%s TEXT,%s TEXT," +
                "%s INTEGER,%s TEXT," +
                "%s INTEGER," +
                "FOREIGN KEY (%s) %s);",
                Tablas.ALUMNOS,
                AulaVirtualContract.Alumnos.ID, AulaVirtualContract.Alumnos.USER_ID,
                AulaVirtualContract.Alumnos.NOMBRE,AulaVirtualContract.Alumnos.APELLIDOS,
                AulaVirtualContract.Alumnos.EDAD,AulaVirtualContract.Alumnos.DIRECCION,
                AulaVirtualContract.Alumnos.FOTO_PERFIL,
                AulaVirtualContract.Alumnos.USER_ID,Referencias.ID_USER));
        db.execSQL(String.format("CREATE TABLE %s (" +
                "%s TEXT PRIMARY KEY,%s TEXT," +
                "%s TEXT,%s TEXT," +
                "%s INTEGER,%s TEXT," +
                "%s INTEGER," +
                "FOREIGN KEY (%s) %s);",
                Tablas.PROFESORES,
                AulaVirtualContract.Profesores.ID,AulaVirtualContract.Profesores.USER_ID,
                AulaVirtualContract.Profesores.NOMBRE,AulaVirtualContract.Profesores.APELLIDOS,
                AulaVirtualContract.Profesores.EDAD,AulaVirtualContract.Profesores.DIRECCION,
                AulaVirtualContract.Profesores.FOTO_PERFIL,
                AulaVirtualContract.Profesores.USER_ID,Referencias.ID_USER));
        db.execSQL(String.format("CREATE TABLE %s(" +
                "%s TEXT PRIMARY KEY,%s TEXT," +
                "%s TEXT,%s INTEGER,%s TEXT);",
                Tablas.CURSOS,
                AulaVirtualContract.Cursos.ID,AulaVirtualContract.Cursos.NOMBRE,
                AulaVirtualContract.Cursos.DESCRIPCION,AulaVirtualContract.Cursos.FOTO_CURSO,
                AulaVirtualContract.Cursos.CODIGO_CURSO));
        db.execSQL(String.format("CREATE TABLE %s(" +
                "%s TEXT PRIMARY KEY," +
                "%s TEXT, %s TEXT," +
                "%s TEXT, %s TEXT," +
                "%s TEXT, %s TEXT," +
                "%s INTEGER);",
                Tablas.ASIGNATURAS,
                AulaVirtualContract.Asignaturas.ID,
                AulaVirtualContract.Asignaturas.NOMBRE, AulaVirtualContract.Asignaturas.DESCRIPCION,
                AulaVirtualContract.Asignaturas.FECHA_INICIO, AulaVirtualContract.Asignaturas.FECHA_FIN,
                AulaVirtualContract.Asignaturas.HORA_INICIO, AulaVirtualContract.Asignaturas.HORA_FIN,
                AulaVirtualContract.Asignaturas.FOTO_ASIGNATURA));
        db.execSQL(String.format("CREATE TABLE %s (" +
                "%s TEXT PRIMARY KEY," +
                "%s TEXT," +
                "%s TEXT," +
                "FOREIGN KEY (%s) %s," +
                "FOREIGN KEY (%s) %s);",
                Tablas.ALUMNOS_CURSOS,
                AulaVirtualContract.AlumnosCursos.ID,
                AulaVirtualContract.AlumnosCursos.ID_ALUMNO,
                AulaVirtualContract.AlumnosCursos.ID_CURSO,
                AulaVirtualContract.AlumnosCursos.ID_ALUMNO,Referencias.ID_ALUMNO,
                AulaVirtualContract.AlumnosCursos.ID_CURSO,Referencias.ID_CURSO));
        db.execSQL(String.format("CREATE TABLE %s (" +
                "%s TEXT PRIMARY KEY," +
                "%s TEXT," +
                "%s TEXT," +
                "FOREIGN KEY (%s) %s," +
                "FOREIGN KEY (%s) %s);",
                Tablas.PROFESORES_CURSOS,
                AulaVirtualContract.ProfesoresCursos.ID,
                AulaVirtualContract.ProfesoresCursos.ID_PROFESOR,
                AulaVirtualContract.ProfesoresCursos.ID_CURSO,
                AulaVirtualContract.ProfesoresCursos.ID_CURSO,Referencias.ID_CURSO,
                AulaVirtualContract.ProfesoresCursos.ID_PROFESOR, Referencias.ID_PROFESOR));
        db.execSQL(String.format("CREATE TABLE %s (" +
                "%s TEXT PRIMARY KEY," +
                "%s TEXT," +
                "%s TEXT," +
                "FOREIGN KEY (%s) %s," +
                "FOREIGN KEY (%s) %s);",
                Tablas.ASIGNATURAS_CURSOS,
                AulaVirtualContract.AsignaturasCursos.ID,
                AulaVirtualContract.AsignaturasCursos.ASIGNATURA_ID,
                AulaVirtualContract.AsignaturasCursos.ID_CURSO,
                AulaVirtualContract.AsignaturasCursos.ASIGNATURA_ID, Referencias.ID_ASIGNATURA,
                AulaVirtualContract.AsignaturasCursos.ID_CURSO,Referencias.ID_CURSO));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Tablas.USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS "+ Tablas.ALUMNOS);
        db.execSQL("DROP TABLE IF EXISTS "+ Tablas.PROFESORES);
        db.execSQL("DROP TABLE IF EXISTS "+ Tablas.CURSOS);
        db.execSQL("DROP TABLE IF EXISTS "+ Tablas.ASIGNATURAS);
        db.execSQL("DROP TABLE IF EXISTS "+ Tablas.ALUMNOS_CURSOS);
        db.execSQL("DROP TABLE IF EXISTS "+ Tablas.PROFESORES_CURSOS);
        db.execSQL("DROP TABLE IF EXISTS "+ Tablas.ASIGNATURAS_CURSOS);
        onCreate(db);
    }
}
