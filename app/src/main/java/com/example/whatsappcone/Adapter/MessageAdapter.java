package com.example.whatsappcone.Adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.whatsappcone.Models.Message;
import com.example.whatsappcone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageAdapter extends RecyclerView.Adapter {

  Context context;
  ArrayList<Message> messageList;
  String receiverId;
  int SENDER_VIEW_TYPE = 1;
  int RECEIVER_VIEW_TYPE = 2;

  public MessageAdapter(Context context, ArrayList<Message> messageList) {
    this.context = context;
    this.messageList = messageList;
  }

  public MessageAdapter(ArrayList<Message> messageList, String receiverId) {
    this.receiverId = receiverId;
    this.messageList = messageList;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == SENDER_VIEW_TYPE) {
      View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
      return new SenderViewHolder(view);
    } else {
      View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever, parent, false);
      return new ReceiverViewHolder(view);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    Message message = messageList.get(position);

    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        //todo not working when long press on text
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Message");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              FirebaseDatabase.getInstance().getReference()
                .child("Chats").child(message.getUniqueId()).removeValue();
            }
          });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
            }
          });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return false;
      }
    });

    SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
    Date date = new Date(message.getTimeStamp().toString());
    if (holder.getClass() == SenderViewHolder.class) {
      ((SenderViewHolder) holder).senderMessage.setText(message.getMessage());
      ((SenderViewHolder) holder).senderTime.setText(dateFormat.format(date));
    } else {
      ((ReceiverViewHolder) holder).receiverMessage.setText(message.getMessage());
      ((ReceiverViewHolder) holder).receiverTime.setText(dateFormat.format(date));
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (messageList.get(position).getSenderId().equals(FirebaseAuth.getInstance().getUid())) {
      return SENDER_VIEW_TYPE;
    } else {
      return RECEIVER_VIEW_TYPE;
    }
  }

  @Override
  public int getItemCount() {
    return messageList.size();
  }

  public class ReceiverViewHolder extends RecyclerView.ViewHolder {
    TextView receiverMessage, receiverTime;
    public ReceiverViewHolder(@NonNull View itemView) {
      super(itemView);
      receiverMessage = itemView.findViewById(R.id.recieverMessage);
      receiverTime = itemView.findViewById(R.id.recieverTime);
    }
  }

  public class SenderViewHolder extends RecyclerView.ViewHolder {
    TextView senderMessage, senderTime;
    public SenderViewHolder(@NonNull View itemView) {
      super(itemView);
      senderMessage = itemView.findViewById(R.id.senderMessage);
      senderTime = itemView.findViewById(R.id.senderTime);
    }
  }
}
