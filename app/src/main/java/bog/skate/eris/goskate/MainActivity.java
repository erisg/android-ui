package bog.skate.eris.goskate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    DatabaseReference mRef;
    FirebaseDatabase mFirebaseDatabase;
    RecyclerView mRecyclerView;
    ArrayList<Model> list;
    Adapter adapter;


    private TextView mTextMessage;


    //Menu
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intentHome = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intentHome);
                    break;
                case R.id.navigation_mapa:
                    Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_post:
                    Intent intentPost = new Intent(MainActivity.this,postActivity.class);
                    startActivity(intentPost);
                    break;
                case R.id.navigation_notifications:
                    Intent shop = new Intent(MainActivity.this,shopActivity.class);
                    startActivity(shop);
                    break;
                case R.id.navigation_salir:

                    FirebaseAuth.getInstance().signOut();
                    Intent login = new Intent(MainActivity.this,loginActivity.class);
                    startActivity(login);
                    break;
            }


            return false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =findViewById(R.id.toolbar_main);
        toolbar.setTitle("GoSkate");
        toolbar.setTitleTextColor(Color.WHITE);


        mTextMessage = (TextView) findViewById(R.id.message);


        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Data");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Model m = dataSnapshot1.getValue(Model.class);
                    list.add(m);
                }
                adapter = new Adapter(MainActivity.this,list);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Algo  esta mal", Toast.LENGTH_SHORT).show();
            }
        });

        list = new ArrayList<Model>();
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
