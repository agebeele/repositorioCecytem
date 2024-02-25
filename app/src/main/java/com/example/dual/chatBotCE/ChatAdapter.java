package com.example.dual.chatBotCE;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dual.R;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private List<ChatMessage> messages;
    private Context context;

    public ChatAdapter(Context context) {
        this.messages = new ArrayList<>();
        this.context = context;
    }

    public ChatAdapter() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.senderTextView.setText(message.getSender());
        holder.messageTextView.setText(message.getMessage());

        // Llama al método customizeTextStyle para ajustar el estilo de la letra
        customizeTextStyle(holder, message.getSender());

    }

    // Método para personalizar el estilo de la letra
    private void customizeTextStyle(MessageViewHolder holder, String sender) {
        try {
            if ("Usuario".equals(sender)) {
                // Estilo de letra para mensajes del Usuario
                holder.senderTextView.setTypeface(null, Typeface.BOLD);
                holder.messageTextView.setTextColor(ContextCompat.getColor(context, R.color.colorUserMessage));
            } else if ("ChatBot".equals(sender)) {
                // Estilo de letra para mensajes del ChatBot
                holder.senderTextView.setTypeface(null, Typeface.NORMAL);
                holder.messageTextView.setTextColor(ContextCompat.getColor(context, R.color.colorChatBotMessage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView senderTextView;
        TextView messageTextView;

        MessageViewHolder(View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senderTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }
}