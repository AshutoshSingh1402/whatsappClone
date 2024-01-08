package com.example.whatsappcone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappcone.Adapter.MessageAdapter;
import com.example.whatsappcone.Models.Message;
import com.example.whatsappcone.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

  ActivityChatBinding binding;
  FirebaseAuth auth;
  FirebaseDatabase database;
  ArrayList<Message> messageList = new ArrayList<>();
  String senderUserId;
  String receiverUserId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityChatBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    auth = FirebaseAuth.getInstance();
    database = FirebaseDatabase.getInstance();

    senderUserId = auth.getUid();
    receiverUserId = getIntent().getStringExtra("userId");
    String profileImage = getIntent().getStringExtra("profileImage");
    String username = getIntent().getStringExtra("username");

    binding.userName.setText(username);
    Picasso.get().load(profileImage).placeholder(R.drawable.avatar).into(binding.profileImage);
    messageList.add(new Message("kjidhi", "hello User", "hello", new Date()));

    MessageAdapter messageAdapter = new MessageAdapter(this, messageList);
    binding.chats.setAdapter(messageAdapter);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    binding.chats.setLayoutManager(layoutManager);

    //todo .orderByChild("timeStamp").limitToFirst(5) shorting is not done
    database.getReference().child("Chats").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        messageList.clear();
        for(DataSnapshot chatSnapshot: snapshot.getChildren()) {
          System.out.println(chatSnapshot.getValue());
          Message message = chatSnapshot.getValue(Message.class);
          if (auth.getUid().equals(message.getSenderId()) || auth.getUid().equals(message.getReceiverId())) {
            messageList.add(message);
            message.setUniqueId(chatSnapshot.getKey());
          }
        }
        messageAdapter.notifyDataSetChanged();
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

  }

  public void openHomePage(View view) {
    Intent intent = new Intent(ChatActivity.this, MainActivity.class);
    startActivity(intent);
  }

  public void sendMessage(View view) {
    String messageText = binding.inputMessage.getText().toString().trim();
    if (messageText.length() == 0) {
      Toast.makeText(this, "message missing", Toast.LENGTH_SHORT).show();
      return;
    }
    Message message = new Message(senderUserId, messageText, receiverUserId, new Date());
    DatabaseReference chatReference = database.getReference("Chats");
    chatReference.child(UUID.randomUUID().toString()).setValue(message)
      .addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          if (task.isSuccessful()) {
            binding.inputMessage.setText("");
          } else {
            Toast.makeText(ChatActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });

  }
}