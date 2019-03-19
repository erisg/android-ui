package bog.skate.eris.goskate;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSION = 111;

    private FirebaseAuth mAuth;
    private EditText EmailUsuario;
    private EditText PasswordUsuario;
    private TextView TextNoacount;
    private Button btn_ingresar;
    private ProgressDialog loadingBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        solicitarPermisos();

        // inicializacion objeto
        mAuth = FirebaseAuth.getInstance();


        EmailUsuario = (EditText) findViewById(R.id.emal_login);
        PasswordUsuario = (EditText) findViewById(R.id.passw_login);
        TextNoacount = (TextView) findViewById(R.id.no_acoount);
        btn_ingresar = (Button)findViewById(R.id.ingresar_btn);
        loadingBar = new ProgressDialog(this);


        // No Tiene Cuenta enviar a Registrar
        TextNoacount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToRegistrerActivity();
            }
        });


        //Button de ingresar

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToLogin();
            }
        });



    }

    private void SendUserToLogin() {
        String email = EmailUsuario.getText().toString();
        String passwsword = PasswordUsuario.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Porfavor Ingresa tu Email...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(passwsword)){
            Toast.makeText(this,"Porfavor Ingresa tu Contraseña...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Ingresando a GoSkate Bogota");
            loadingBar.setMessage("Por favor Espera, Ingresando a GoSkate Bogota...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);


            mAuth.signInWithEmailAndPassword(email, passwsword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()){

                              SendUserToMainActivity();
                              
                              Toast.makeText(loginActivity.this,"Inicio de seccion exitoso",Toast.LENGTH_SHORT).show();
                              loadingBar.dismiss();
                          }
                          else{
                              String message = task.getException().getMessage();
                              Toast.makeText(loginActivity.this,"Error" + message, Toast.LENGTH_SHORT).show();
                              loadingBar.dismiss();
                          }
                        }
                    });
        }
    }

    private void SendUserToMainActivity() {
        Intent main = new Intent(loginActivity.this,MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main);
    }

    private void SendUserToRegistrerActivity() {
        Intent registrarse = new Intent(loginActivity.this,registroActivity.class);
        startActivity(registrarse);
    }

    //Permisos
    private void solicitarPermisos() {
        int permisoInternet = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int permisoUbicacion = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permisoUbi = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permisoInternet != PackageManager.PERMISSION_GRANTED || permisoUbi != PackageManager.PERMISSION_GRANTED || permisoUbicacion != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ASK_PERMISSION);

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
