package dev.nesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    public void goCadastro(View view) {
        startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
    }

    public void goMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void logar(View view) {
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        if(email.isEmpty()){
            etEmail.setError("Campo e-mail não pode ser vazio!");
            return;
        } else {
            etEmail.setError(null);
        }

        if(password.isEmpty()){
            etPassword.setError("Campo senha não pode ser vazio!");
            return;
        } else {
            etPassword.setError(null);
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        goMain();
                    } else {
                        Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void recoveryPassword(View view) {
        final String email = etEmail.getText().toString();
        if(email.isEmpty()){
            etEmail.setError("Campo e-mail não pode ser vazio!");
            return;
        } else {
            etEmail.setError(null);
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Um email com a redefinição de senha foi enviado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null) {
            goMain();
        }
    }

}