package dev.nesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {
    private EditText etNomeRegistro, etPasswordRegistro, etEmailRegistro;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        mAuth = FirebaseAuth.getInstance();

        etNomeRegistro = findViewById(R.id.etNomeRegistro);
        etPasswordRegistro = findViewById(R.id.etPasswordRegistro);
        etEmailRegistro = findViewById(R.id.etEmailRegistro);
    }

    public void cadastrar(View view) {

        final String email = etEmailRegistro.getText().toString();
        final String password =  etPasswordRegistro.getText().toString();

        if(email.isEmpty()){
            etEmailRegistro.setError("O campo e-mail não pode ser vazio");
            return;
        } else {
            etEmailRegistro.setError(null);
        }

        if(password.isEmpty()){
            etPasswordRegistro.setError("O campo senha não pode ser vazio");
            return;
        } else {
            etPasswordRegistro.setError(null);
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        goMain();
                    } else {
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void goMain(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void closeCadastro(View view) {
        finish();
    }
}