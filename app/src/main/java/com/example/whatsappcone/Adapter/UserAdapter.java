package com.example.whatsappcone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappcone.Models.Users;
import com.example.whatsappcone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

  Context context;
  ArrayList<Users> usersList;

  public UserAdapter(Context context, ArrayList<Users> usersList) {
    this.context = context;
    this.usersList = usersList;
  }
  @NonNull
  @Override
  public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.chat_list_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
    Users user = usersList.get(position);
    Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.avatar3).into(holder.ProfileImage);
    holder.username.setText(user.getUsername());
  }

  @Override
  public int getItemCount() {
    return usersList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView ProfileImage;
    TextView username, lastMessage;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      ProfileImage = itemView.findViewById(R.id.profile_image);
      username = itemView.findViewById(R.id.user_name);
      lastMessage = itemView.findViewById(R.id.last_message);
    }
  }
}
