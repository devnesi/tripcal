package dev.nesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dev.nesi.adapters.ViagensAdapter;
import dev.nesi.models.Viagem;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AppBarLayout appBar;
    private FirebaseAuth mAuth;

    private ListView listViewViagem;
    private DatabaseReference mDatabase;
    private ArrayAdapter<Viagem> adapter;
    private ArrayList<Viagem> viagems;
    private ImageView imageMain;
    private TextView textMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        appBar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        appBar.setOutlineProvider(null);

        imageMain = findViewById(R.id.imageMain);
        textMain = findViewById(R.id.textMain);

        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(mAuth.getCurrentUser().getUid()).child("viagens");
        listViewViagem = findViewById(R.id.listViewViagem);

        viagems = new ArrayList<>();
        adapter = new ViagensAdapter(this, viagems);
        listViewViagem.setAdapter(adapter);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viagems.clear();
                for(DataSnapshot dados : snapshot.getChildren()){
                    imageMain.setVisibility(View.GONE);
                    textMain.setVisibility(View.GONE);

                    Viagem viagem = dados.getValue(Viagem.class);
                    viagems.add(viagem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        mAuth.signOut();
        goLogin();
    }

    public void goLogin(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void novaViagem(View view) {
        startActivity(new Intent(MainActivity.this, CadastroViagemActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() == null) {
            goLogin();
        }
    }
}