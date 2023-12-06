package com.example.whatsappcone;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsappcone.Models.Users;
import com.example.whatsappcone.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

  private ActivitySignUpBinding binding;
  private FirebaseAuth mAuth;
  FirebaseDatabase database;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySignUpBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    mAuth = FirebaseAuth.getInstance();
    database = FirebaseDatabase.getInstance();
  }

  public void onClickSignUpButton(View view) {
    if ((binding.editTextUsername.getText().length() < 1) || (binding.editTextEmail.getText().length() < 1)
     || (binding.editTextPassword.getText().length() < 1)) {
      Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
      return;
    }
//    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
//    builder.setTitle("Creating Account");
//    builder.setMessage("we are creating your account");
//    builder.create().show();

    // show dialog that we are creating account
    ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
    progressDialog.setTitle("Creating Account");
    progressDialog.setMessage("we are creating your account");
    progressDialog.show();

    // add data to database
    mAuth.createUserWithEmailAndPassword(binding.editTextEmail.getText().toString(), binding.editTextPassword.getText().toString())
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
          String id = task.getResult().getUser().getUid();
          Users user = new Users(binding.editTextUsername.getText().toString(), binding.editTextEmail.getText().toString(), binding.editTextPassword.getText().toString());
          database.getReference().child("Users").child(id).setValue(user);
//          user.setUserId(id);
          Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
      }
    });
  }

  public void openSignInPage(View view) {
    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
    startActivity(intent);
  }
}