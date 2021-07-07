package com.jorch.proyecto.aulavirtual.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Alumno;
import com.jorch.proyecto.aulavirtual.data.AlumnoCursoDao;
import com.jorch.proyecto.aulavirtual.data.Curso;
import com.jorch.proyecto.aulavirtual.data.CursoDao;
import com.jorch.proyecto.aulavirtual.data.Usuario;
import com.jorch.proyecto.aulavirtual.ui.AulaActivity;
import com.jorch.proyecto.aulavirtual.ui.LoginActivity;

/**
 * Created by JORCH on 19/01/2017.
 */

public class AddCursoAlumnoDialog extends DialogFragment {
    private EditText editTextCodigo;
    private Button buttonAceptar;
    private String codigo;
    private Alumno alumno;
    private Bundle bundle;
    private Curso curso;
    public static AddCursoAlumnoDialog newInstance(Alumno alumno) {

        Bundle args = new Bundle();
        args.putSerializable(AulaActivity.ALUMNO_LOGEADO,alumno);
        AddCursoAlumnoDialog fragment = new AddCursoAlumnoDialog();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_add_curso_alumno,null);
        builder.setView(view);

        editTextCodigo = (EditText) view.findViewById(R.id.et_add_curso_alumno_codigo);
        buttonAceptar = (Button) view.findViewById(R.id.bttn_add_curso_añadir);
        bundle = getArguments();
        alumno = (Alumno) bundle.getSerializable(AulaActivity.ALUMNO_LOGEADO);
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigo = editTextCodigo.getText().toString();
                if (TextUtils.isEmpty(codigo)){
                    Toast.makeText(getContext(),"Introduzca un codigo",Toast.LENGTH_SHORT).show();
                    editTextCodigo.requestFocus();
                }
                else {
                    if (CursoDao.createInstance(getContext()).comprobarCodigoCurso(codigo)){
                        curso = CursoDao.createInstance(getContext()).obtenerCursobyCodigoCurso(codigo);
                        AlumnoCursoDao.createInstance(getContext()).insertarCurso(curso,alumno.getId());
                        dismiss();
                        Toast.makeText(getContext(),"Agregado nuevo curso.\nDesliza para refrescar",Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(getContext(),"Codigo inválido.",Toast.LENGTH_SHORT).show();
                    }
                    Cursor cursor = CursoDao.createInstance(getContext()).obtenerCodigoCurso(codigo);
                    if (cursor != null && cursor.moveToFirst()){
                         curso = new Curso(cursor);
                    }

                }
            }
        });
        return builder.create();
    }
}
