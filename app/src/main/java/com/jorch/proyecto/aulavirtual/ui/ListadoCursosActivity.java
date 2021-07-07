package com.jorch.proyecto.aulavirtual.ui;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Alumno;
import com.jorch.proyecto.aulavirtual.data.AlumnoCursoDao;
import com.jorch.proyecto.aulavirtual.data.AlumnoDao;
import com.jorch.proyecto.aulavirtual.data.Asignatura;
import com.jorch.proyecto.aulavirtual.data.AsignaturaCursoDao;
import com.jorch.proyecto.aulavirtual.data.AulaVirtualContract;
import com.jorch.proyecto.aulavirtual.data.Curso;
import com.jorch.proyecto.aulavirtual.data.Profesor;
import com.jorch.proyecto.aulavirtual.data.ProfesorCursoDao;
import com.jorch.proyecto.aulavirtual.data.ProfesorDao;
import com.jorch.proyecto.aulavirtual.data.Usuario;
import com.jorch.proyecto.aulavirtual.utils.AdapterRecyclerViewAsignaturas;
import com.jorch.proyecto.aulavirtual.utils.AdapterRecyclerViewCursosDetalle;

import java.util.ArrayList;
import java.util.List;

public class ListadoCursosActivity extends AppCompatActivity implements  AdapterRecyclerViewCursosDetalle.OnItemClickListener{
    private List<Curso> listaCursos = new ArrayList<>();
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdapterRecyclerViewCursosDetalle adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Usuario usuario;
    private Profesor profesor;
    private Alumno alumno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_cursos);
        Intent intentRecibido = getIntent();
        Bundle bundleRecibido = intentRecibido.getExtras();
        usuario = (Usuario) bundleRecibido.getSerializable(LoginActivity.USUARIO_LOGEADO);
        toolbar = (Toolbar) findViewById(R.id.toolbar_listado_cursos);
        recyclerView = (RecyclerView) findViewById(R.id.rv_listado_cursos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (usuario.getRol().equals("ESTUDIANTE")){
            alumno = AlumnoDao.createInstance(getApplicationContext()).obtenerAlumnoByUsuario(usuario.getId());
            adapter = new AdapterRecyclerViewCursosDetalle(obtenerListaCursos(alumno),this);
        }
        if (usuario.getRol().equals("PROFESOR")){
            profesor = ProfesorDao.createInstance(getApplicationContext()).obtenerProfesorByUsuario(usuario.getId());
            adapter = new AdapterRecyclerViewCursosDetalle(obtenerListaCursos(profesor),this);
        }
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
    public List<Curso> obtenerListaCursos(Alumno alumno){
        listaCursos = new ArrayList<Curso>(){};
        String id,nombre, descripcion, codigo;
        int imagen;
        Cursor cursor = AlumnoCursoDao.createInstance(this).obtenerAllCursosByAlumnoId(alumno.getId());
        if (cursor.moveToFirst()){
            do {
                id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.ID));
                nombre = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.NOMBRE));
                descripcion = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.DESCRIPCION));
                imagen = cursor.getInt(cursor.getColumnIndex(AulaVirtualContract.Cursos.FOTO_CURSO));
                codigo = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.CODIGO_CURSO));
                Curso curso = new Curso(nombre,descripcion,imagen);
                curso.setId(id);
                curso.setCodigoCurso(codigo);
                listaCursos.add(curso);
            }while (cursor.moveToNext());
        }
        return listaCursos;
    }
    public List<Curso> obtenerListaCursos(Profesor profesor){
        listaCursos = new ArrayList<Curso>(){};
        Cursor cursor = ProfesorCursoDao.createInstance(this).obtenerAllCursosByProfesorId(profesor.getId());
        String id,nombre, descripcion, codigo;
        int imagen;
        if (cursor.moveToFirst()){
            do {
                id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.ID));
                nombre = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.NOMBRE));
                descripcion = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.DESCRIPCION));
                imagen = cursor.getInt(cursor.getColumnIndex(AulaVirtualContract.Cursos.FOTO_CURSO));
                codigo = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.CODIGO_CURSO));
                Curso curso = new Curso(nombre,descripcion,imagen);
                curso.setId(id);
                curso.setCodigoCurso(codigo);
                listaCursos.add(curso);
            }while (cursor.moveToNext());
        }
        return listaCursos;
    }
    @Override
    public void onItemClick(Curso curso) {
        //TODO hacer una actividad de editar curso
    }

    @Override
    public boolean onNavigateUp() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
