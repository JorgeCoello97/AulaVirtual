package com.jorch.proyecto.aulavirtual.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Alumno;
import com.jorch.proyecto.aulavirtual.data.AlumnoDao;
import com.jorch.proyecto.aulavirtual.data.Profesor;
import com.jorch.proyecto.aulavirtual.data.ProfesorDao;
import com.jorch.proyecto.aulavirtual.data.Usuario;
import com.jorch.proyecto.aulavirtual.data.UsuarioDao;
import com.jorch.proyecto.aulavirtual.ui.AulaActivity;
import com.jorch.proyecto.aulavirtual.ui.LoginActivity;
import com.jorch.proyecto.aulavirtual.utils.EncriptarUtils;

import java.util.Set;

/**
 * Created by JORCH on 22/01/2017.
 */

public class DarteDeBajaDialog extends DialogFragment {
    private EditText editTextPassword, editTextPasswordConfirmar;
    private Button buttonCancelar, buttonAceptar;
    private String password, passwordConfirmar;
    private Usuario usuario;
    private Alumno alumno;
    private Profesor profesor;
    public static DarteDeBajaDialog newInstance(Usuario usuario) {

        Bundle args = new Bundle();
        args.putSerializable(LoginActivity.USUARIO_LOGEADO,usuario);
        DarteDeBajaDialog fragment = new DarteDeBajaDialog();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_darte_de_baja,null);
        builder.setView(view);
        editTextPassword = (EditText) view.findViewById(R.id.et_darte_baja_password);
        editTextPasswordConfirmar = (EditText) view.findViewById(R.id.et_darte_baja_password_repetir);
        buttonAceptar = (Button) view.findViewById(R.id.bttn_darte_baja_aceptar);
        buttonCancelar = (Button) view.findViewById(R.id.bttn_darte_baja_cancelar);

        Bundle bundleRecibido = getArguments();
        usuario = (Usuario) bundleRecibido.getSerializable(LoginActivity.USUARIO_LOGEADO);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = editTextPassword.getText().toString();
                passwordConfirmar = editTextPasswordConfirmar.getText().toString();
                View focus = null;
                boolean error = false;
                if (TextUtils.isEmpty(password)){
                    editTextPassword.setError("Introduce tu contraseña");
                    focus = editTextPassword;
                    error = true;
                }
                if (TextUtils.isEmpty(passwordConfirmar)){
                    editTextPasswordConfirmar.setError("Introduce otra vez la contraseña");
                    focus = editTextPasswordConfirmar;
                    error = true;
                }
                if (error){
                    focus.requestFocus();
                }else {
                    if (password.equals(passwordConfirmar)){
                        password = EncriptarUtils.encriptarCadena(password);
                        if (usuario.getContraseña().equals(password)){
                            mostratDialogo();
                        }
                    }
                    else {
                        editTextPassword.setText("");
                        editTextPasswordConfirmar.setText("");
                        editTextPassword.requestFocus();
                        Toast.makeText(getContext(),"Contraseñas inválidas.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return builder.create();
    }
    public void mostratDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ADVERTENCIA")
                .setMessage("¿Estas seguro que quieres continuar?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UsuarioDao dao = UsuarioDao.createInstance(getContext());
                        if (usuario.getRol().equals("PROFESOR")){
                            profesor = ProfesorDao.createInstance(getContext()).obtenerProfesorByUsuario(usuario.getId());
                            dao.eliminarUsuarioProfesor(usuario,profesor);
                        }
                        if (usuario.getRol().equals("ESTUDIANTE")){
                            alumno = AlumnoDao.createInstance(getContext()).obtenerAlumnoByUsuario(usuario.getId());
                            dao.eliminarUsuarioAlumno(usuario,alumno);
                        }
                        startActivity(new Intent(getActivity(),LoginActivity.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }
}
