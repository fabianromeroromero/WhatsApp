package com.example.whatsapp.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin, buttonRegister;
    private EditText correo, contrasena;
    private ImageButton imgButtonPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");

        mAuth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        imgButtonPassword = findViewById(R.id.viewPassword);

        //Accion del boton login
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                onClickLogin();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });

        //Accion del ojo
        imgButtonPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onClickTogglePassword();
            }
        });

    }

    //Funcion para iniciar sesion
    private void onClickLogin() {
        String email = correo.getText().toString();
        String password = contrasena.getText().toString();

        if(!email.isEmpty()){
            if(!password.isEmpty()){
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }else{
                contrasena.setError("Campo obligatorio");
            }
        }else{
            correo.setError("Campo obligatorio");
        }
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            System.out.println(user.toString());
            Intent intent = new Intent(this, InterfazActivity.class);
            startActivity(intent);
        }
    }

    //Funcion para ocultar y visualizar la clave
    private void onClickTogglePassword() {
        if (contrasena.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            contrasena.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            contrasena.setTypeface(Typeface.DEFAULT); // Restaurar el tipo de fuente predeterminado
            imgButtonPassword.setImageResource(android.R.drawable.ic_menu_view);
        } else {
            contrasena.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            contrasena.setTypeface(Typeface.DEFAULT); // Restaurar el tipo de fuente predeterminado
            imgButtonPassword.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }

}