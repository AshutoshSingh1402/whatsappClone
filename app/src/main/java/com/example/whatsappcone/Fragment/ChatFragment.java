package com.example.whatsappcone.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.whatsappcone.Adapter.UserAdapter;
import com.example.whatsappcone.Models.Users;
import com.example.whatsappcone.R;
import com.example.whatsappcone.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

  FragmentChatBinding binding;
  FirebaseDatabase database;
  ArrayList<Users> usersList = new ArrayList<>();

  public ChatFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentChatBinding.inflate(inflater, container, false);
    database = FirebaseDatabase.getInstance();

    UserAdapter adapter = new UserAdapter(getContext(), usersList);
    binding.chatRecyclerView.setAdapter(adapter);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    binding.chatRecyclerView.setLayoutManager(layoutManager);

    database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        System.out.println(snapshot.getChildren());
        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
          Users user = dataSnapshot.getValue(Users.class);
          user.setUserId(dataSnapshot.getKey());
          if (!user.getUserId().equals(FirebaseAuth.getInstance().getUid())) {
            usersList.add(user);
          }
        }
        adapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

    return binding.getRoot();
  }
}