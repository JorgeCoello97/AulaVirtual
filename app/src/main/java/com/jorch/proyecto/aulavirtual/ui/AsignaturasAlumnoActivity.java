package com.jorch.proyecto.aulavirtual.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Asignatura;
import com.jorch.proyecto.aulavirtual.data.AsignaturaCursoDao;
import com.jorch.proyecto.aulavirtual.data.AulaVirtualContract;
import com.jorch.proyecto.aulavirtual.data.Curso;
import com.jorch.proyecto.aulavirtual.utils.AdapterRecyclerViewAsignaturas;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class AsignaturasAlumnoActivity extends AppCompatActivity implements AdapterRecyclerViewAsignaturas.OnItemClickListener{
    public static final String CURSO = "CURSO";
    public static final int REQUEST_ADD_ASIGNATURA_ALUMNO = 0;
    private List<Asignatura> listaAsignaturas = new ArrayList<>();
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdapterRecyclerViewAsignaturas adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;
    private Curso curso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturas_alumno);
        Intent intentRecibido = getIntent();
        Bundle bundleRecibido = intentRecibido.getExtras();
        curso = (Curso) bundleRecibido.getSerializable(AulaActivity.CURSO_SELECCIONADO);
        toolbar = (Toolbar) findViewById(R.id.toolbar_asignaturas_alumno);
        recyclerView = (RecyclerView) findViewById(R.id.rv_cursos_asignaturas_alumno);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshRecyclerView_asignaturas_alumno);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ASIGNATURAS DE "+curso.getNombre());
        adapter = new AdapterRecyclerViewAsignaturas(listaAsignaturas,this);
        actualizarLista();
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
        cursor.close();
        return listaAsignaturas;
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
