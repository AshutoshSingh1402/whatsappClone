package com.example.whatsappcone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappcone.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

  private FirebaseAuth mAuth;
  ActivitySignUpBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySignUpBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    mAuth = FirebaseAuth.getInstance();
    System.out.println("hello");
  }

  public void onClickSignInButton(View view) {
    System.out.println("hello");
//    System.out.println(binding.editTextUsername.getText().toString());
    Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
  }
}