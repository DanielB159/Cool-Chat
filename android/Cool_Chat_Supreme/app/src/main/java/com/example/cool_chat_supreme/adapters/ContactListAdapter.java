package com.example.cool_chat_supreme.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cool_chat_supreme.R;
import com.example.cool_chat_supreme.entities.Chat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>{
    private SelectListener listener;

    class ContactViewHolder extends RecyclerView.ViewHolder{
        public final ConstraintLayout layout;
        private final ImageView profilePic;
        private final TextView finalText;
        private final TextView dateOfFinalText;
        private final TextView displayName;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            this.profilePic = itemView.findViewById(R.id.profilePic);
            this.finalText = itemView.findViewById(R.id.finalText);
            this.dateOfFinalText = itemView.findViewById(R.id.dateOfFinalText);
            this.displayName = itemView.findViewById(R.id.contactDisplayName);
            this.layout = itemView.findViewById(R.id.topLayout);
        }
    }

    private final LayoutInflater mInflater;

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    private List<Chat> chats;

    public ContactListAdapter(Context context, SelectListener listener) {
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_chat_layout, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (chats != null) {
            final Chat current = chats.get(position);
            // set the image of the contact
            String base64Image = current.getOtherUser().getProfilePic();
            byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.profilePic.setImageBitmap(bitmap);

            // set the last message in the chat with the contact
            if (current.getMsg().size() != 0) {
                String content = current.getMsg().get(current.getMsg().size() - 1).getContent();
                if (content.length() >= 20) {
                    content = content.substring(0, 20) + "...";
                }
                holder.finalText.setText(content);
            } else if (current.getLastMessage() != null) {
                String content = current.getLastMessage().getContent();
                if (content.length() >= 20) {
                    content = content.substring(0, 20) + "...";
                }
                holder.finalText.setText(content);
            } else {
                holder.finalText.setText("");
            }

            // set the display name of the contact
            holder.displayName.setText(current.getOtherUser().getDisplayName());

            // set the time and date of the last message in the chat
            if (current.getMsg().size() != 0) {
                holder.dateOfFinalText.setText
                        (timeParser(current.getMsg().get(current.getMsg().size() - 1).getTime()));
            } else if (current.getLastMessage() != null) {
                holder.dateOfFinalText.setText(timeParser(current.getLastMessage().getTime()));
            } else {
                holder.dateOfFinalText.setText("");
            }

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(chats.get(position));
                }
            });

            holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClicked(chats.get(position));
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (chats != null) {
            return chats.size();
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
