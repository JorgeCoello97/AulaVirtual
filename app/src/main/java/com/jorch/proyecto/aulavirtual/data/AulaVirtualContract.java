package com.jorch.proyecto.aulavirtual.data;

import java.util.UUID;

/**
 * Created by JORCH on 17/01/2017.
 */

public class AulaVirtualContract {
    interface ColumnasUsuarios{
        String ID = "id";
        String USUARIO = "usuario";
        String CONTRASEÑA = "contraseña";
        String CORREO = "correo";
        String ROL = "rol";
    }
    interface ColumnasAlumnos {
        String ID = "id";
        String USER_ID = "user_id";
        String NOMBRE = "nombre";
        String APELLIDOS = "apellidos";
        String EDAD = "edad";
        String DIRECCION = "direccion";
        String FOTO_PERFIL = "foto_perfil";
    }
    interface ColumnasProfesores {
        String ID = "id";
        String USER_ID = "user_id";
        String NOMBRE = "nombre";
        String APELLIDOS = "apellidos";
        String EDAD = "edad";
        String DIRECCION = "direccion";
        String FOTO_PERFIL = "foto_perfil";
    }
    interface ColumnasCursos {
        String ID = "id";
        String NOMBRE = "nombre";
        String DESCRIPCION = "descripcion";
        String FOTO_CURSO = "foto_curso";
        String CODIGO_CURSO = "codigo";
    }
    interface ColumnasAlumnosCursos {
        String ID = "id";
        String ID_ALUMNO = "id_alumno";
        String ID_CURSO = "id_curso";
    }
    interface ColumnasProfesoresCursos {
        String ID = "id";
        String ID_PROFESOR = "id_profesor";
        String ID_CURSO = "id_curso";
    }
    interface ColumnasAsignaturas {
        String ID = "id";
        String NOMBRE = "nombre";
        String DESCRIPCION = "descripcion";
        String FOTO_ASIGNATURA = "foto_asignatura";
        String FECHA_INICIO = "fecha_inicio";
        String FECHA_FIN = "fecha_fin";
        String HORA_INICIO = "hora_inicio";
        String HORA_FIN = "hora_fin";
    }
    interface  ColumnasAsignaturasCursos {
        String ID = "id";
        String ID_CURSO = "curso_id";
        String ASIGNATURA_ID = "asignatura_id";
    }
    private  AulaVirtualContract(){};

    public static class Usuarios implements ColumnasUsuarios{
        public static String generarIdUsuario(){
            return "USER-"+ UUID.randomUUID().toString();
        }
    }
    public static class Alumnos implements ColumnasAlumnos{
        public static String generarIdAlumno(){
            return "ALUM-"+ UUID.randomUUID().toString();
        }
    }
    public static class Profesores implements ColumnasProfesores{
        public static String generarIdProfesor(){
            return "PROF-"+ UUID.randomUUID().toString();
        }
    }
    public static class Cursos implements ColumnasCursos{
        public static String generarIdCurso(){
            return "CUR-"+ UUID.randomUUID().toString();
        }
    }
    public static class AlumnosCursos implements ColumnasAlumnosCursos{
        public static String generarIdAlumnoCurso(){
            return "ALUM_CUR-"+ UUID.randomUUID().toString();
        }
    }
    public static class ProfesoresCursos implements ColumnasProfesoresCursos{
        public static String generarIdProfesorCurso(){
            return "PROF_CUR-"+ UUID.randomUUID().toString();
        }
    }
    public static class Asignaturas implements ColumnasAsignaturas{
        public static String generarIdAsignatura(){
            return "ASIG-"+ UUID.randomUUID().toString();
        }
    }
    public static class AsignaturasCursos implements ColumnasAsignaturasCursos{
        public static String generarIdAsignaturaCurso(){
            return "ASIG_CUR-"+ UUID.randomUUID().toString();
        }
    }
}
