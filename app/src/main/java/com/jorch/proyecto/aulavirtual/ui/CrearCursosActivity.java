package com.jorch.proyecto.aulavirtual.ui;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.AulaVirtualContract;
import com.jorch.proyecto.aulavirtual.data.Curso;
import com.jorch.proyecto.aulavirtual.data.CursoDao;
import com.jorch.proyecto.aulavirtual.data.Profesor;
import com.jorch.proyecto.aulavirtual.data.ProfesorCursoDao;
import com.jorch.proyecto.aulavirtual.utils.AdapterSpinnerImagenes;
import com.jorch.proyecto.aulavirtual.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class CrearCursosActivity extends AppCompatActivity{
    public static final String CURSO_CREADO = "CURSO_CREADO";
    private Profesor profesor;
    private EditText editTextNombreCurso, editTextDescripcion;
    private Button buttonGenerarCodigo;
    private TextView textViewCodigoGenerado;
    private Spinner spinnerImagenCurso;
    private List<Integer> list = new ArrayList<Integer>(){};
    private String nombre, descripcion;
    private int imagen;
    private Bundle bundle;
    private Intent intent;
    private  String codigoGenerado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cursos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CREAR NUEVO CURSO");
        intent = getIntent();
        bundle = intent.getExtras();
        profesor = (Profesor) bundle.getSerializable(AulaActivity.PROFESOR_LOGEADO);
        profesor = (Profesor) getIntent().getExtras().getSerializable(AulaActivity.PROFESOR_LOGEADO);
        llenarLista();
        editTextNombreCurso = (EditText) findViewById(R.id.et_crear_asignatura_nombre);
        editTextDescripcion = (EditText) findViewById(R.id.et_crear_curso_descripcion);
        spinnerImagenCurso = (Spinner) findViewById(R.id.spinner_imagen_asignatura);
        buttonGenerarCodigo = (Button) findViewById(R.id.bttn_crear_curso_generar_codigo);
        textViewCodigoGenerado = (TextView) findViewById(R.id.tv_crear_curso_codigo);
        spinnerImagenCurso.setAdapter(new AdapterSpinnerImagenes(this,list));
        spinnerImagenCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imagen = list.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        codigoGenerado = RandomUtils.generarCodigo();
        textViewCodigoGenerado.setText(codigoGenerado);
        buttonGenerarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoGenerado = RandomUtils.generarCodigo();
                Cursor cursor =CursoDao.createInstance(getApplicationContext()).obtenerCodigoCurso(codigoGenerado);
                if (cursor.moveToFirst()){
                    do {
                        String codigo = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Cursos.CODIGO_CURSO));
                        if (codigoGenerado.equalsIgnoreCase(codigo)){
                            Toast.makeText(getApplicationContext(),
                                    "Por favor, Genere otro codigo de curso",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    } while (cursor.moveToNext());
                }
                textViewCodigoGenerado.setText(codigoGenerado);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crear_cursos,menu);
        return true;
    }

    @Override
    public boolean onNavigateUp() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_crear_curso_item_guardar:
                boolean error = false;
                nombre = editTextNombreCurso.getText().toString();
                descripcion = editTextDescripcion.getText().toString();
                codigoGenerado = textViewCodigoGenerado.getText().toString();
                if (TextUtils.isEmpty(codigoGenerado)){
                    error = true;
                    Toast.makeText(getApplicationContext(),"Necesitas generar un codigo de curso",Toast.LENGTH_SHORT).show();
                    textViewCodigoGenerado.requestFocus();
                }
                if (TextUtils.isEmpty(nombre)) {
                    error = true;
                    editTextNombreCurso.setError("Falta el nombre del curso");
                }
                if (TextUtils.isEmpty(descripcion)) {
                    error = true;
                    editTextDescripcion.setError("Falta la descripcion del curso");
                }
                if (error) {
                    return false;
                }
                Curso curso = new Curso(nombre, descripcion, imagen);
                curso.setCodigoCurso(codigoGenerado);

                ProfesorCursoDao.createInstance(getApplicationContext()).insertarCurso(curso, profesor.getId());
                Curso cursoCompleto = CursoDao.createInstance(getApplicationContext()).obtenerCursobyCodigoCurso(codigoGenerado);
                bundle.putSerializable(CURSO_CREADO,cursoCompleto);

                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
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
