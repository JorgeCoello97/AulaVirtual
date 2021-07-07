package com.jorch.proyecto.aulavirtual.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Asignatura;
import com.jorch.proyecto.aulavirtual.data.AsignaturaCursoDao;
import com.jorch.proyecto.aulavirtual.data.Curso;
import com.jorch.proyecto.aulavirtual.utils.AdapterSpinnerImagenes;

import java.util.ArrayList;
import java.util.List;

public class CrearAsignaturasActivity extends AppCompatActivity {
    public static final String ASIGNATURA_CREADA = "ASIGNATURA_CREADA";
    private EditText editTextNombre, editTextFechaInicial, editTextFechaFinal, editTextHoraInicial, editTextHoraFinal, editTextDescripcion;
    private Spinner spinnerImagenAsignatura;
    private List<Integer> list = new ArrayList<Integer>(){};
    private Intent intentRecibido;
    private Bundle bundleRecibido;
    private Curso curso;
    private int imagen;
    private String nombre,descripcion,fechainicial,fechafinal,horainicial,horafinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_asignaturas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CREAR NUEVA ASIGNATURA");
        editTextNombre = (EditText) findViewById(R.id.et_crear_asignatura_nombre);
        editTextDescripcion = (EditText) findViewById(R.id.et_crear_asignatura_descripcion);
        editTextFechaInicial = (EditText) findViewById(R.id.et_crear_asignatura_fecha_inicial);
        editTextFechaFinal = (EditText) findViewById(R.id.et_crear_asignatura_fecha_final);
        editTextHoraInicial = (EditText) findViewById(R.id.et_crear_asignatura_hora_inicio);
        editTextHoraFinal = (EditText) findViewById(R.id.et_crear_asignatura_hora_fin);
        spinnerImagenAsignatura = (Spinner) findViewById(R.id.spinner_imagen_asignatura);
        llenarLista();

        spinnerImagenAsignatura.setAdapter(new AdapterSpinnerImagenes(this,list));
        spinnerImagenAsignatura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imagen = list.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        intentRecibido = getIntent();
        bundleRecibido = intentRecibido.getExtras();
        curso = (Curso) bundleRecibido.getSerializable(AsignaturasProfesorActivity.CURSO);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crear_asignaturas,menu);
        return true;
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
            case R.id.menu_crear_asignatura_item_guardar:
                boolean error = false;
                nombre = editTextNombre.getText().toString();
                descripcion = editTextDescripcion.getText().toString();
                fechainicial = editTextFechaInicial.getText().toString();
                fechafinal = editTextFechaFinal.getText().toString();
                horainicial = editTextHoraInicial.getText().toString();
                horafinal = editTextHoraFinal.getText().toString();
                if (TextUtils.isEmpty(nombre)){
                    error = true;
                    editTextNombre.setError("Falta el nombre de la asignatura");
                }
                if (TextUtils.isEmpty(descripcion)){
                    error = true;
                    editTextDescripcion.setError("Se necesita una descipción");
                }
                if (TextUtils.isEmpty(fechainicial)){
                    error = true;
                    editTextFechaInicial.setError("Indica la fecha inicial");
                }
                if (TextUtils.isEmpty(fechafinal)){
                    error = true;
                    editTextFechaFinal.setError("Indica la fecha final");
                }
                if (TextUtils.isEmpty(horainicial)){
                    error = true;
                    editTextHoraInicial.setError("Indica la hora inicial");
                }
                if (TextUtils.isEmpty(horafinal)){
                    error = true;
                    editTextHoraFinal.setError("Indica la hora final");
                }
                if (error){
                    return false;
                }
                Asignatura asignatura = new Asignatura(nombre,descripcion,imagen,fechainicial,fechafinal,horainicial,horafinal);
                AsignaturaCursoDao.createInstance(getApplicationContext()).insertarAsignatura(asignatura,curso);
                bundleRecibido.putSerializable(ASIGNATURA_CREADA,asignatura);
                intentRecibido.putExtras(bundleRecibido);
                setResult(RESULT_OK,intentRecibido);
                finish();
                break;
        }
        return true;
    }

    public void llenarLista(){
        list.add(R.drawable.header_amarillo);
        list.add(R.drawable.header_amarillo_oscuro);
        list.add(R.drawable.header_azul);
        list.add(R.drawable.header_celeste);
        list.add(R.drawable.header_marron);
        list.add(R.drawable.header_marron_oscuro);
        list.add(R.drawable.header_morado);
        list.add(R.drawable.header_naranja);
        list.add(R.drawable.header_negro);
        list.add(R.drawable.header_rojo);
        list.add(R.drawable.header_rosa);
        list.add(R.drawable.header_verde);
    }
}
