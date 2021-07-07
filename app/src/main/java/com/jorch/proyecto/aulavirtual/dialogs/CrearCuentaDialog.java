package com.jorch.proyecto.aulavirtual.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Alumno;
import com.jorch.proyecto.aulavirtual.data.AlumnoDao;
import com.jorch.proyecto.aulavirtual.data.AulaVirtualContract;
import com.jorch.proyecto.aulavirtual.data.Profesor;
import com.jorch.proyecto.aulavirtual.data.ProfesorDao;
import com.jorch.proyecto.aulavirtual.data.UsuarioDao;
import com.jorch.proyecto.aulavirtual.utils.EncriptarUtils;

/**
 * Created by JORCH on 14/01/2017.
 */

public class CrearCuentaDialog extends DialogFragment {
    private Button buttonCrearCuenta;
    private EditText editTextUsuario, editTextPassword, editTextCorreo;
    private Spinner spinnerRol;
    String usuario, password, correo, rol;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_crear_cuenta,null);
        builder.setView(view);
        editTextUsuario = (EditText) view.findViewById(R.id.et_crear_cuenta_usuario);
        editTextPassword = (EditText) view.findViewById(R.id.et_crear_cuenta_password);
        editTextCorreo = (EditText) view.findViewById(R.id.et_crear_cuenta_correo);
        spinnerRol = (Spinner) view.findViewById(R.id.sp_crear_cuenta);
        buttonCrearCuenta = (Button) view.findViewById(R.id.bttn_crear_cuenta);

        spinnerRol.setSelection(0);
        buttonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextUsuario.setError(null);
                editTextPassword.setError(null);
                editTextCorreo.setError(null);
                usuario = editTextUsuario.getText().toString();
                password = editTextPassword.getText().toString();
                correo = editTextCorreo.getText().toString();
                View focusView = null;
                boolean cancel = false;

                if (TextUtils.isEmpty(usuario)) {
                    editTextUsuario.setError("Campo obligatorio");
                    focusView = editTextUsuario;
                    cancel = true;
                } else if (!isUserValid(usuario)) {
                    editTextUsuario.setError("Usuario inválido");
                    focusView = editTextUsuario;
                    cancel = true;
                }
                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Campo obligatorio");
                    focusView = editTextPassword;
                } else if (!isPasswordValid(password)) {
                    editTextPassword.setError("Contraseña inválida");
                    focusView = editTextPassword;
                    cancel = true;
                }
                if (TextUtils.isEmpty(correo)) {
                    editTextCorreo.setError("Campo obligatorio");
                    focusView = editTextCorreo;
                    cancel = true;
                } else if (!isEmailValid(correo)) {
                    editTextCorreo.setError("Email inválido");
                    focusView = editTextCorreo;
                    cancel = true;
                }
                rol = spinnerRol.getSelectedItem().toString();
                spinnerRol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        rol = getResources().getStringArray(R.array.spinner_rol)[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (editTextUsuario.hasFocus())
                        inputMethodManager.hideSoftInputFromWindow(editTextUsuario.getWindowToken(),0);
                    if (editTextPassword.hasFocus())
                        inputMethodManager.hideSoftInputFromWindow(editTextPassword.getWindowToken(),0);
                    if (editTextCorreo.hasFocus())
                        inputMethodManager.hideSoftInputFromWindow(editTextCorreo.getWindowToken(),0);

                    Cursor cursor = UsuarioDao.createInstance(getContext()).obtenerAllUsuarios();
                    boolean usuarioRegistrado = false;
                    if (cursor.moveToFirst()){
                        do {
                            String u = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Usuarios.USUARIO));
                            if (u.equalsIgnoreCase(usuario)){
                                Toast.makeText(getContext(),"Cuenta ya registrada," +
                                        "\nprueba con otro usuario ",Toast.LENGTH_SHORT).show();
                                editTextUsuario.setText("");
                                editTextPassword.setText("");
                                editTextUsuario.requestFocus();
                                usuarioRegistrado = true;
                                break;
                            }
                        }while (cursor.moveToNext());

                    }
                    if (usuarioRegistrado == false){
                        password = EncriptarUtils.encriptarCadena(password);
                        String codigoUsuario = UsuarioDao.createInstance(getContext()).insertarUsuario(usuario,password,correo,rol);
                        if (rol.equals("ESTUDIANTE")){
                            Alumno alumnoNuevo = new Alumno(codigoUsuario," "," ",0," ",0);
                            AlumnoDao.createInstance(getContext()).insertarAlumno(alumnoNuevo);
                        }else {
                            Profesor profesorNuevo = new Profesor(codigoUsuario," "," ",0," ",0);
                            ProfesorDao.createInstance(getContext()).insertarProfesor(profesorNuevo);
                        }
                        Toast.makeText(getContext(),"CUENTA CREADA\nPor favor inicie sesión.",Toast.LENGTH_LONG).show();
                        dismiss();
                    }
                }
            }
        });
        return builder.create();
    }
    private boolean isUserValid(String usuario) {
        return usuario.length()<=10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() <=15;
    }
    private boolean isEmailValid(String correo) {
        return correo.contains("@");
    }

}
