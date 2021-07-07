package com.jorch.proyecto.aulavirtual.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Alumno;
import com.jorch.proyecto.aulavirtual.data.AlumnoCursoDao;
import com.jorch.proyecto.aulavirtual.data.Asignatura;
import com.jorch.proyecto.aulavirtual.data.AsignaturaCursoDao;
import com.jorch.proyecto.aulavirtual.data.AulaVirtualContract;
import com.jorch.proyecto.aulavirtual.data.Curso;
import com.jorch.proyecto.aulavirtual.data.Profesor;
import com.jorch.proyecto.aulavirtual.data.Usuario;
import com.jorch.proyecto.aulavirtual.utils.AdapterRecyclerViewAsignaturas;
import com.jorch.proyecto.aulavirtual.utils.AdapterRecyclerViewCursos;

import java.util.ArrayList;
import java.util.List;

public class AsignaturasProfesorActivity extends AppCompatActivity implements AdapterRecyclerViewAsignaturas.OnItemClickListener{
    public static final String CURSO = "CURSO";
    public static final int REQUEST_ADD_ASIGNATURA_PROFESOR = 0;
    private List<Asignatura> listaAsignaturas = new ArrayList<>();
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdapterRecyclerViewAsignaturas adapter;
    private RecyclerView.LayoutManager layoutManager;
    private com.melnykov.fab.FloatingActionButton fab;
    private SwipeRefreshLayout refreshLayout;
    private Curso curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturas_profesor);
        Intent intentRecibido = getIntent();
        Bundle bundleRecibido = intentRecibido.getExtras();
        curso = (Curso) bundleRecibido.getSerializable(AulaActivity.CURSO_SELECCIONADO);
        toolbar = (Toolbar) findViewById(R.id.toolbar_asignaturas_profesor);
        recyclerView = (RecyclerView) findViewById(R.id.rv_cursos_asignaturas_profesor);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshRecyclerView_asignaturas_profesor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ASIGNATURAS DE "+curso.getNombre());
        fab = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab_añadir_asignaturas_profesor);

        adapter = new AdapterRecyclerViewAsignaturas(obtenerListaAsignaturas(curso),this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               actualizarLista();
                refreshLayout.setRefreshing(false);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCrearCursoAlumno = new Intent(AsignaturasProfesorActivity.this,CrearAsignaturasActivity.class);
                Bundle bundleCrearCursoAlumno = new Bundle();
                bundleCrearCursoAlumno.putSerializable(CURSO,curso);
                intentCrearCursoAlumno.putExtras(bundleCrearCursoAlumno);
                startActivityForResult(intentCrearCursoAlumno,REQUEST_ADD_ASIGNATURA_PROFESOR);
            }
        });

    }
    public void actualizarLista(){
        android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(obtenerListaAsignaturas(curso));
                refreshLayout.setRefreshing(false);
            }
        });
    }
    @Override
    public boolean onNavigateUp() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //TODO
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(Asignatura asignatura) {
        //TODO Hacer activity asignatura datos diseño
        Toast.makeText(getApplicationContext(),asignatura.toString(),Toast.LENGTH_SHORT).show();
    }

    public List<Asignatura> obtenerListaAsignaturas(Curso curso){
        listaAsignaturas = new ArrayList<Asignatura>(){};
        String nombre, descripcion;
        int imagen;
        String id,fechaInicio,fechaFin,horaInicio,horaFin;
        Cursor cursor = AsignaturaCursoDao.createInstance(this).obtenerAllAsignaturasByCursoId(curso.getId());
        if (cursor.moveToFirst()){
            do {
                id = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.ID));
                nombre = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.NOMBRE));
                descripcion = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.DESCRIPCION));
                imagen = cursor.getInt(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.FOTO_ASIGNATURA));
                fechaInicio = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.FECHA_INICIO));
                fechaFin = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.FECHA_FIN));
                horaInicio = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.HORA_INICIO));
                horaFin = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Asignaturas.HORA_FIN));
                Asignatura asignatura = new Asignatura(nombre,descripcion,imagen,fechaInicio,fechaFin,horaInicio,horaFin);
                asignatura.setId(id);
                listaAsignaturas.add(asignatura);
            }while (cursor.moveToNext());
        }
        return listaAsignaturas;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_ADD_ASIGNATURA_PROFESOR){
                Bundle bundleRecogido = data.getExtras();
                Asignatura asignatura = (Asignatura) bundleRecogido.getSerializable(CrearAsignaturasActivity.ASIGNATURA_CREADA);
                Toast.makeText(getApplicationContext(),"Asignatura "+asignatura.getNombre()+" creada.\nDeslize hacia abajo para actualizar.",Toast.LENGTH_LONG).show();
            }
        }
        if (resultCode == RESULT_CANCELED){

        }
    }
}
