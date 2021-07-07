package com.jorch.proyecto.aulavirtual.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Alumno;
import com.jorch.proyecto.aulavirtual.data.AlumnoCursoDao;
import com.jorch.proyecto.aulavirtual.data.AlumnoDao;
import com.jorch.proyecto.aulavirtual.data.AulaVirtualContract;
import com.jorch.proyecto.aulavirtual.data.Curso;
import com.jorch.proyecto.aulavirtual.data.Profesor;
import com.jorch.proyecto.aulavirtual.data.ProfesorCursoDao;
import com.jorch.proyecto.aulavirtual.data.ProfesorDao;
import com.jorch.proyecto.aulavirtual.data.Usuario;
import com.jorch.proyecto.aulavirtual.dialogs.AcercaDeDialog;
import com.jorch.proyecto.aulavirtual.dialogs.AddCursoAlumnoDialog;
import com.jorch.proyecto.aulavirtual.dialogs.CerrarSesionDialog;
import com.jorch.proyecto.aulavirtual.dialogs.DarteDeBajaDialog;
import com.jorch.proyecto.aulavirtual.utils.AdapterRecyclerViewCursos;
import com.jorch.proyecto.aulavirtual.utils.SesionPrefs;

import java.util.ArrayList;
import java.util.List;

public class AulaActivity extends AppCompatActivity
        implements AdapterRecyclerViewCursos.OnItemClickListener,
        CerrarSesionDialog.OnCerrarSesionActionsListener
{
    public static final String ALUMNO_LOGEADO = "ALUMNO_LOGEADO";
    public static final String PROFESOR_LOGEADO = "PROFESOR_LOGEADO";
    public static final String CURSO_SELECCIONADO = "CURSO_SELECCIONADO";
    private static final int REQUEST_ADD_CURSO_PROFESOR = 0;
    private Alumno alumno;
    private Profesor profesor;
    private List<Curso> listaCursos = new ArrayList<>();
    private NavigationView navigationViewAula;
    private DrawerLayout drawerLayoutAula;
    private ActionBarDrawerToggle drawerToggleAula;
    private Toolbar toolbarAula;
    private String ROL_USUARIO;
    private Intent intentLogin;
    private Bundle bundleLogin;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private com.melnykov.fab.FloatingActionButton fab;
    private SwipeRefreshLayout refreshLayout;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!SesionPrefs.get(this).isLoggedIn()){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_aula);
        intentLogin = getIntent();
        bundleLogin = intentLogin.getExtras();
        usuario = (Usuario) bundleLogin.getSerializable(LoginActivity.USUARIO_LOGEADO);

        toolbarAula = (Toolbar) findViewById(R.id.toolbar_aula);
        navigationViewAula = (NavigationView) findViewById(R.id.navigationview_aula);
        drawerLayoutAula = (DrawerLayout) findViewById(R.id.drawer_layout_aula);
        recyclerView = (RecyclerView) findViewById(R.id.rv_cursos);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshRecyclerView);
        setSupportActionBar(toolbarAula);
        drawerToggleAula = new ActionBarDrawerToggle(this,drawerLayoutAula,toolbarAula,R.string.open_drawer,R.string.close_drawer);
        drawerLayoutAula.addDrawerListener(drawerToggleAula);
        drawerToggleAula.syncState();
        fab = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab_cursos_añadir);

        navigationViewAula.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //TODO
                switch (item.getItemId()){
                    case R.id.menu_nv_aula_item_perfil:
                        break;
                    case R.id.menu_nv_aula_item_darte_de_baja:
                        drawerLayoutAula.closeDrawers();
                        DarteDeBajaDialog darteDeBajaDialog = DarteDeBajaDialog.newInstance(usuario);
                        getFragmentManager().beginTransaction().add(darteDeBajaDialog,"DIALOGO_DARTE_BAJA").commit();
                        break;
                    case R.id.menu_nv_aula_item_cursos:
                        drawerLayoutAula.closeDrawers();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(LoginActivity.USUARIO_LOGEADO,usuario);
                        Intent intent = new Intent(AulaActivity.this,ListadoCursosActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case R.id.menu_nv_aula_item_cerrar_sesion:
                        getFragmentManager().beginTransaction().add(new CerrarSesionDialog(),"DIALOG_CERRAR_SESION").commit();
                        drawerLayoutAula.closeDrawers();
                        break;
                }
                return true;
            }
        });
        ROL_USUARIO = usuario.getRol();
        if (ROL_USUARIO.equalsIgnoreCase("ESTUDIANTE")){
            alumno = AlumnoDao.createInstance(this).obtenerAlumnoByUsuario(usuario.getId());
            adapter = new AdapterRecyclerViewCursos(obtenerListaCursos(alumno),this);
            layoutManager = new GridLayoutManager(this,2);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    adapter = new AdapterRecyclerViewCursos(obtenerListaCursos(alumno), new AdapterRecyclerViewCursos.OnItemClickListener() {
                        @Override
                        public void onItemClick(Curso curso) {
                            Intent intentAsignaturaCursoAlumno = new Intent(AulaActivity.this,AsignaturasAlumnoActivity.class);
                            Bundle bundleAsignaturaCursoAlumno = new Bundle();
                            bundleAsignaturaCursoAlumno.putSerializable(CURSO_SELECCIONADO,curso);
                            intentAsignaturaCursoAlumno.putExtras(bundleAsignaturaCursoAlumno);
                            startActivity(intentAsignaturaCursoAlumno);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddCursoAlumnoDialog addCursoAlumnoDialog = AddCursoAlumnoDialog.newInstance(alumno);
                    getSupportFragmentManager().beginTransaction().add(addCursoAlumnoDialog,"DIALOG_AÑADIR_CURSO").commit();
                }
            });
        }
        else if(ROL_USUARIO.equalsIgnoreCase("PROFESOR")){
            profesor = ProfesorDao.createInstance(this).obtenerProfesorByUsuario(usuario.getId());
            adapter = new AdapterRecyclerViewCursos(obtenerListaCursos(profesor), this);
            layoutManager = new GridLayoutManager(this,2);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    adapter = new AdapterRecyclerViewCursos(obtenerListaCursos(profesor), new AdapterRecyclerViewCursos.OnItemClickListener() {
                        @Override
                        public void onItemClick(Curso curso) {
                            Intent intentAsignaturaCursoAlumno = new Intent(AulaActivity.this,AsignaturasProfesorActivity.class);
                            Bundle bundleAsignaturaCursoAlumno = new Bundle();
                            bundleAsignaturaCursoAlumno.putSerializable(CURSO_SELECCIONADO,curso);
                            intentAsignaturaCursoAlumno.putExtras(bundleAsignaturaCursoAlumno);
                            startActivity(intentAsignaturaCursoAlumno);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);
                }
            });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intentCrearCursoAlumno = new Intent(AulaActivity.this,CrearCursosActivity.class);
                    Bundle bundleCrearCursoAlumno = new Bundle();
                    bundleCrearCursoAlumno.putSerializable(PROFESOR_LOGEADO,profesor);
                    intentCrearCursoAlumno.putExtras(bundleCrearCursoAlumno);
                    startActivityForResult(intentCrearCursoAlumno,REQUEST_ADD_CURSO_PROFESOR);
                }
            });
        }
    }

    @Override
    public void onItemClick(Curso curso) {
        if (ROL_USUARIO.equals("ESTUDIANTE")){
            Intent intentAsignaturaCursoAlumno = new Intent(AulaActivity.this,AsignaturasAlumnoActivity.class);
            Bundle bundleAsignaturaCursoAlumno = new Bundle();
            bundleAsignaturaCursoAlumno.putSerializable(CURSO_SELECCIONADO,curso);
            intentAsignaturaCursoAlumno.putExtras(bundleAsignaturaCursoAlumno);
            startActivity(intentAsignaturaCursoAlumno);
        }
        if (ROL_USUARIO.equals("PROFESOR")){
            Intent intentAsignaturaCursoProfesor = new Intent(AulaActivity.this,AsignaturasProfesorActivity.class);
            Bundle bundleAsignaturaCursoProfesor = new Bundle();
            bundleAsignaturaCursoProfesor.putSerializable(CURSO_SELECCIONADO,curso);
            intentAsignaturaCursoProfesor.putExtras(bundleAsignaturaCursoProfesor);
            startActivity(intentAsignaturaCursoProfesor);
        }
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggleAula.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_ADD_CURSO_PROFESOR){
                Bundle bundleRecogido = data.getExtras();
                Curso cursoRecogido = (Curso) bundleRecogido.getSerializable(CrearCursosActivity.CURSO_CREADO);
                Toast.makeText(getApplicationContext(),"Curso "+cursoRecogido.getNombre()+" creado.\nDeslize hacia abajo para actualizar.",Toast.LENGTH_LONG).show();
            }
        }
        if (resultCode == RESULT_CANCELED){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_aula_virtual_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_aula_main_acerca_de:
                getFragmentManager().beginTransaction().add(new AcercaDeDialog(),"DIALOGO_ACERCA_DE").commit();
                break;
            case R.id.menu_aula_main_cerrar_sesion:
                getFragmentManager().beginTransaction().add(new CerrarSesionDialog(),"DIALOGO_CERRAR_SESION").commit();
                break;
        }
        return true;
    }

    @Override
    public void buttonAceptarCerrarSesion() {
        startActivity(new Intent(AulaActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().beginTransaction().add(new CerrarSesionDialog(),"DIALOGO_CERRAR_SESION").commit();
    }
}
