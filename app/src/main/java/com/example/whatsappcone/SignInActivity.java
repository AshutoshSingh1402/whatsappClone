package com.example.whatsappcone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappcone.databinding.ActivitySignInBinding;
import com.example.whatsappcone.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

  ActivitySignInBinding binding;
  FirebaseAuth mAuth;
  FirebaseDatabase firebaseDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySignInBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    mAuth = FirebaseAuth.getInstance();
    firebaseDatabase = FirebaseDatabase.getInstance();
    if (mAuth.getCurrentUser() != null) {
      Intent intent = new Intent(SignInActivity.this, MainActivity.class);
      startActivity(intent);
    }
  }

  public void onClickSignInButton(View view) {
    if (binding.editTextEmail.getText().toString().isEmpty() || binding.password.getText().toString().isEmpty()) {
      Toast.makeText(SignInActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
      return;
    }
    mAuth.signInWithEmailAndPassword(binding.editTextEmail.getText().toString(), binding.password.getText().toString())
      .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
          if (task.isSuccessful()) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
          } else {
            Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
  }

  public void openSignUpPage(View view) {
    Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
    startActivity(intent);
  }
}