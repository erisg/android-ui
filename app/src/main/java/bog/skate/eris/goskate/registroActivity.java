package bog.skate.eris.goskate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class registroActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    private EditText RegEmail, RegPassword, ComfirPassword;
    private ProgressDialog loadingBar;
    private Button btnRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // inicializacion objeto
        mAuth = FirebaseAuth.getInstance();

        RegEmail = (EditText) findViewById(R.id.email_reg);
        RegPassword = (EditText) findViewById(R.id.pasw_reg);
        ComfirPassword = (EditText) findViewById(R.id.comfirpas);
        btnRegistrar = (Button) findViewById(R.id.btn_regis);
        loadingBar = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearCuentaNueva();
            }
        });

    }

    private void CrearCuentaNueva() {


        String email = RegEmail.getText().toString();
        String password = RegPassword.getText().toString();
        String comfirpassword = ComfirPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Porfavor Ingresa tu Email...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Porfavor Escribe tu Contraseña...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(comfirpassword)){
            Toast.makeText(this,"Porfavor Comfirma tu Contraseña...", Toast.LENGTH_SHORT).show();
        }
        else if (! password.equals(comfirpassword)){

            Toast.makeText(this,"Las contraseñas no coinciden...", Toast.LENGTH_SHORT).show();
        }

        else {

            loadingBar.setTitle("Creando cuenta en GoSkate");
            loadingBar.setMessage("Por favor Espera, Ingresando a GoSkate Bogota...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                SendUserToMainActivity();
                                Toast.makeText(registroActivity.this,"Autenticacion Exitosa...", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String message = task.getException().getMessage();
                                Toast.makeText(registroActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    private void SendUserToMainActivity() {
        Intent main = new Intent(registroActivity.this,MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
