package com.jorch.proyecto.aulavirtual.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.AulaVirtualContract;
import com.jorch.proyecto.aulavirtual.data.AulaVirtualSQLiteHelper;
import com.jorch.proyecto.aulavirtual.data.Usuario;
import com.jorch.proyecto.aulavirtual.data.UsuarioDao;
import com.jorch.proyecto.aulavirtual.dialogs.CrearCuentaDialog;
import com.jorch.proyecto.aulavirtual.utils.EncriptarUtils;
import com.jorch.proyecto.aulavirtual.utils.SesionPrefs;

public class LoginActivity extends AppCompatActivity {
    public static final String USUARIO_LOGEADO = "USUARIO_LOGEADO";
    private UserLoginTask mAuthTask = null;

    // UI references.
    private ImageView imageViewLogo;
    private EditText editTextUsuario, editTextPassword;
    private View mProgressView, mLoginFormView;
    private Button buttonIniciarSesion, buttonCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageViewLogo = (ImageView) findViewById(R.id.img_login_logo);
        editTextUsuario = (EditText) findViewById(R.id.et_login_usuario);
        editTextPassword = (EditText) findViewById(R.id.et_login_password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        buttonIniciarSesion = (Button) findViewById(R.id.bttn_login_iniciar_sesion);
        buttonCrearCuenta = (Button) findViewById(R.id.bttn_login_crear_cuenta);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        buttonIniciarSesion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //HAGO ESTO PARA CERRAR EL TECLADO AL DARLE AL BOTON
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (editTextUsuario.hasFocus())
                    inputMethodManager.hideSoftInputFromWindow(editTextUsuario.getWindowToken(),0);
                if (editTextPassword.hasFocus())
                    inputMethodManager.hideSoftInputFromWindow(editTextPassword.getWindowToken(),0);

                attemptLogin();
            }
        });
        buttonCrearCuenta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().add(new CrearCuentaDialog(),"CREAR_CUENTA").commit();
            }
        });
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        editTextUsuario.setError(null);
        editTextPassword.setError(null);

        String usuario = editTextUsuario.getText().toString();
        String password = editTextPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

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
            cancel = true;
        } else if (!isPasswordValid(password)) {
            editTextPassword.setError("Contraseña inválida");
            focusView = editTextPassword;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            password = EncriptarUtils.encriptarCadena(password);
            mAuthTask = new UserLoginTask(usuario, password, this);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUserValid(String usuario) {
        return usuario.length()<=10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() <=15;
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        imageViewLogo.setVisibility(visibility);
        mLoginFormView.setVisibility(visibility);
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mUsuario;
        private final String mPassword;
        private Cursor cursor;
        private  Context context;
        private String usuario,password,idUsuario,correo,rol;
        private Usuario usuarioLogeado;
        UserLoginTask(String usuario, String password, Context context) {
            mUsuario = usuario;
            mPassword = password;
            this.context = context;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return 3;
            }
            UsuarioDao dao = UsuarioDao.createInstance(getApplicationContext());
            cursor = dao.obtenerAllUsuarios();
            if (cursor.moveToFirst()){
                do {
                    usuario = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Usuarios.USUARIO));
                    password = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Usuarios.CONTRASEÑA));
                    if (mUsuario.equals(usuario) && mPassword.equals(password)) {
                        idUsuario = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Usuarios.ID));
                        correo = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Usuarios.CORREO));
                        rol = cursor.getString(cursor.getColumnIndex(AulaVirtualContract.Usuarios.ROL));
                        usuarioLogeado = new Usuario(usuario,password,correo,rol);
                        usuarioLogeado.setId(idUsuario);
                        return 2;
                    }
                }while (cursor.moveToNext());
            }
            return 1;
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            showProgress(false);

            switch (success){
                case 1:
                    showError("Usuario y/o Contraseña inválidas");
                    break;
                case 2:

                    SesionPrefs.get(LoginActivity.this).saveLogInUsuario(new Usuario(cursor));
                    showAulaVirtual();
                    break;
                case 3:
                    showError("Error en el servidor.");
                    break;
            }
        }

        private void showAulaVirtual(){
            Intent intent = new Intent(LoginActivity.this,AulaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(USUARIO_LOGEADO,usuarioLogeado);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
        private void showError(String error){
            Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

