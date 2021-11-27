package com.example.traininglog.ui.base.messages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {
    private final List<Message> mMessages = new ArrayList<>();
    private final boolean isInput;

    public MessageAdapter(boolean isInput) {
        this.isInput = isInput;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_item_layout, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.bind(message, isInput);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public void addData(List<Message> data, boolean isRefreshed) {
        if(isRefreshed) mMessages.clear();
        mMessages.addAll(data);
        notifyDataSetChanged();
    }
}
