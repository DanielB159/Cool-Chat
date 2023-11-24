package com.example.cool_chat_supreme.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cool_chat_supreme.R;
import com.example.cool_chat_supreme.entities.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>{

    private static final int VIEW_TYPE_SENDER = 1;
    private static final int VIEW_TYPE_RECEIVER = 2;
    @Override
    public int getItemViewType(int position) {
        // Determine the view type based on the content of the item at the given position
        if (messages.get(position).getSender().getUsername().equals(username)) {
            return VIEW_TYPE_SENDER;
        } else {
            return VIEW_TYPE_RECEIVER;
        }
    }


    class MessageViewHolder extends RecyclerView.ViewHolder{
        private final TextView finalText;
        private final TextView dateOfFinalText;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.finalText = itemView.findViewById(R.id.finalText);
            this.dateOfFinalText = itemView.findViewById(R.id.dateOfFinalText);
        }
    }

    private final LayoutInflater mInflater;

    private String username = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyDataSetChanged();
    }
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    // constructor
    public MessageListAdapter(Context context) {mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MessageViewHolder viewHolder;

        if (viewType == VIEW_TYPE_SENDER) {
            View view = mInflater.inflate(R.layout.message_sender, parent, false);
            viewHolder = new MessageViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.message_receiver, parent, false);
            viewHolder = new MessageViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (messages != null && username != null) {
            final Message current = messages.get(position);
            // set the message content
            holder.finalText.setText(current.getContent());
            // set the time and date of message
            holder.dateOfFinalText.setText(timeParser(current.getTime()));
        }
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }

    public String timeParser(String time) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");

        try {
            // Parse the input string into a Date object
            Date date = inputFormat.parse(time);
            // Format the Date object to extract the time in hours:minutes
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }
}
