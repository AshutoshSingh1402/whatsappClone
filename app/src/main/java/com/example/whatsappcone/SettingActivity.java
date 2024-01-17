package com.example.whatsappcone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappcone.Models.Users;
import com.example.whatsappcone.databinding.ActivitySettingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class SettingActivity extends AppCompatActivity {

  ActivitySettingBinding binding;
  FirebaseDatabase database;
  FirebaseAuth auth;
  FirebaseStorage storage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySettingBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    database = FirebaseDatabase.getInstance();
    auth = FirebaseAuth.getInstance();
    storage = FirebaseStorage.getInstance();

    database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        Users user = snapshot.getValue(Users.class);
        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.avatar3).into(binding.profileImage);
        binding.about.setText(user.getAbout());
        binding.userName.setText(user.getUsername());
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }


  public void saveUserDetails(View view) {
    if (binding.userName.getText().toString().length() == 0) {
      Toast.makeText(this, "username missing", Toast.LENGTH_SHORT).show();
      return;
    }
    HashMap<String, Object> userDetails = new HashMap<>();
    if (binding.userName.getText().toString().trim().length() > 0) {
      userDetails.put("username", binding.userName.getText().toString());
    }
    if (binding.about.getText().toString().trim().length() > 0) {
      userDetails.put("about", binding.about.getText().toString());
    }
    database.getReference().child("Users").child(auth.getUid()).updateChildren(userDetails)
      .addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          if (task.isSuccessful()) {
            Toast.makeText(SettingActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(SettingActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
  }

  public void moveBackToMainActivity() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }

  public void UploadProfilePic(View view) {
    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    galleryIntent.setType("image/*");
    startActivityForResult(galleryIntent, 20);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    System.out.println(requestCode + " " + resultCode);
    if (data.getData() != null) {
      Uri profileImg = data.getData();
      binding.profileImage.setImageURI(profileImg);
      StorageReference storageReference = storage.getReference().child("ProfileImage").child(auth.getUid());
      storageReference.putFile(profileImg)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
              @Override
              public void onSuccess(Uri uri) {
                database.getReference().child("Users").child(auth.getUid()).child("profilePic")
                  .setValue(uri.toString());
              }
            });
          }
        });
    }
  }
}