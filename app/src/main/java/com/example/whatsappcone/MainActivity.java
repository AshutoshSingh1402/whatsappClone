package com.example.whatsappcone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatsappcone.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

  ActivityMainBinding binding;
  FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("WhatsApp");
    mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    System.out.println(R.id.settings);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//    switch (item.getItemId()) {
//      case R.id.toolbar: {
//        Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
//        break;
//      }
//      case R.id.groupChat: {
//        Toast.makeText(this, "groupChat", Toast.LENGTH_SHORT).show();
//      }
//      case R.id.logout: {
//        Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
//      }
//    }
    if (item.getItemId() == R.id.settings) {
      Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
    } else if (item.getItemId() == R.id.groupChat) {
      Toast.makeText(this, "groupChat", Toast.LENGTH_SHORT).show();
    } else if (item.getItemId() == R.id.logout) {
      mAuth.signOut();
      Intent intent = new Intent(MainActivity.this, SignInActivity.class);
      startActivity(intent);
    }
    return super.onOptionsItemSelected(item);
  }
}