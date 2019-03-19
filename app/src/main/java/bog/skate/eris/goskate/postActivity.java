package bog.skate.eris.goskate;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class postActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ImageButton selectPost;
    private Button publicarPost;
    private EditText rider,spot,truco;
    private Uri ImageUri;
    private  String savecurrentDate, saveCurrentTime, postRandomName, downloadUrl;


    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private static final int galeria_foto = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        selectPost = (ImageButton) findViewById(R.id.post_galery);
        publicarPost = (Button) findViewById(R.id.btn_compartir);
        rider = (EditText) findViewById(R.id.riderPost);
        truco = (EditText) findViewById(R.id.trucoPost);
        spot = (EditText) findViewById(R.id.spotPost);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        selectPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirGaleria();
            }
        });


        publicarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valitePostInfo();
            }
        });

    }

    //Menu
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home = new Intent(postActivity.this,MainActivity.class);
                    startActivity(home);
                    break;
                case R.id.navigation_mapa:
                    Intent intent = new Intent(postActivity.this,MapsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_post:
                    break;
                case R.id.navigation_notifications:
                    Intent shop = new Intent(postActivity.this,shopActivity.class);
                    startActivity(shop);
                    break;
                case R.id.navigation_salir:

                    FirebaseAuth.getInstance().signOut();
                    Intent login = new Intent(postActivity.this,loginActivity.class);
                    startActivity(login);
                    break;
            }


            return false;

        }
    };

    private void valitePostInfo()
    {
        String riderp = rider.getText().toString();
        String trucop = truco.getText().toString();
        String spotp = spot.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(this, "Porfavor selecciona imagen", Toast.LENGTH_SHORT).show();
        }
         else if (TextUtils.isEmpty(riderp))
        {
            Toast.makeText(this, "Porfavor ingresa el nombre del RIDER", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(trucop))
        {
            Toast.makeText(this, "Porfavor ingresar truco", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(spotp))
        {
            Toast.makeText(this, "Porfavor ingresar SPOT", Toast.LENGTH_SHORT).show();
        }
        else
        {
            storingImageToFirebaseStorage();


        }

    }

    private void saveInfoPostFirebase()
    {

    }

    private void storingImageToFirebaseStorage()
    {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        savecurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currenTime = new SimpleDateFormat("HH-mm");
        saveCurrentTime = currenTime.format(calFordTime.getTime());

        postRandomName = savecurrentDate + saveCurrentTime;

        StorageReference filePath = storageReference.child("PostImage").child(ImageUri.getLastPathSegment() + postRandomName + "jpg");
        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                    saveInfoPostFirebase();
                    Toast.makeText(postActivity.this,"Imagen Compartida", Toast.LENGTH_SHORT).show();
                    EnviarUsuarioToMain();
                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(postActivity.this,"Ocurrio un Error"+ message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void EnviarUsuarioToMain()
    {
        Intent main = new Intent(postActivity.this,MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main);
    }

    private void AbrirGaleria()
    {

        Intent galeria = new Intent();
        galeria.setAction(Intent.ACTION_GET_CONTENT);
        galeria.setType("image/*");
        startActivityForResult(galeria,galeria_foto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==galeria_foto && resultCode==RESULT_OK && data!= null)
        {
            ImageUri = data.getData();
            selectPost.setImageURI(ImageUri);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
