package bog.skate.eris.goskate;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class shopActivity extends AppCompatActivity {

    Toolbar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home = new Intent(shopActivity.this,MainActivity.class);
                    startActivity(home);
                    break;
                case R.id.navigation_mapa:
                    Intent intent = new Intent(shopActivity.this,MapsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_post:
                    Intent intentPost = new Intent(shopActivity.this,postActivity.class);
                    startActivity(intentPost);
                    break;
                case R.id.navigation_notifications:
                    break;
                case R.id.navigation_salir:

                    FirebaseAuth.getInstance().signOut();
                    Intent login = new Intent(shopActivity.this,loginActivity.class);
                    startActivity(login);
                    break;
            }


            return false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        toolbar =findViewById(R.id.toolbar_main);
        toolbar.setTitle("GoSkate");
        toolbar.setTitleTextColor(Color.WHITE);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
