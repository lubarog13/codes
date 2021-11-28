package com.example.traininglog.ui.base.messages;

import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Message;
import com.example.traininglog.data.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MessageHolder extends RecyclerView.ViewHolder {
    private TextView mSenderName;
    private TextView mSendTime;
    private TextView mUserType;
    private TextView mHeader;
    private TextView mText;
    private TextView mMessageText;
    private Button mShowButton;
    private Button mResendButton;
    private boolean mIsOpen = false;
    private Resources resources;
    private boolean mIsInput;
    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        mSenderName = itemView.findViewById(R.id.sender_name);
        mSendTime = itemView.findViewById(R.id.send_date);
        mUserType = itemView.findViewById(R.id.message_type);
        mHeader = itemView.findViewById(R.id.message_title);
        mText = itemView.findViewById(R.id.text_in_message);
        mMessageText = itemView.findViewById(R.id.message_text);
        mResendButton = itemView.findViewById(R.id.resend_message_button);
        mShowButton = itemView.findViewById(R.id.show_text_button);
        resources = itemView.getResources();
    }

    public void bind(Message item, boolean isInput, MessageAdapter.onItemClickListener onItemClickListener) {
        mIsInput = isInput;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(item.getSend_time());
        calendar.add(Calendar.HOUR, -3);
        item.setSend_time(calendar.getTime());
        User titleUser = isInput? item.getSender(): item.getRecipient();
        if (!isInput) {
            mResendButton.setBackground(resources.getDrawable(R.drawable.outlined_button_red));
            mResendButton.setTextColor(resources.getColor(R.color.colorRedDark));
            mResendButton.setText("Редактировать");
            mUserType.setText("кому:");
        }
        mSenderName.setText(String.format("%s %s.%s.", titleUser.getLast_name(), titleUser.getFirst_name().charAt(0), titleUser.getSecond_name().charAt(0)));
        mMessageText.setText(item.getMessage());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        mSendTime.setText(simpleDateFormat.format(item.getSend_time().getTime()));
        mHeader.setText(item.getHeding());
        mShowButton.setOnClickListener(v -> showText());
        if (onItemClickListener!=null) {
            if(!isInput) {
                mResendButton.setOnClickListener(v -> {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar.add(Calendar.HOUR, -24);
                    if(calendar1.getTime().compareTo(item.getSend_time())>=0)
                        onItemClickListener.onUpdateClick(item.getId());
                    else Toast.makeText(mSenderName.getContext(), "С момента отправки прошло более 24х часов", Toast.LENGTH_LONG).show();
                });
            }
        }
    }

    private void showText() {
        if(mIsOpen) {
            mText.setVisibility(View.GONE);
            mMessageText.setVisibility(View.GONE);
            mShowButton.setText("Показать текст сообщения");
        } else {
            mText.setVisibility(View.VISIBLE);
            mMessageText.setVisibility(View.VISIBLE);
            mShowButton.setText("Скрыть текст");
        }
        mIsOpen=!mIsOpen;
    }
}
