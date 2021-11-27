package com.example.traininglog.ui.base.messages.incoming;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Message;

import java.util.List;

public interface IncomingMessagesView extends BaseView {
    void showMessages(List<Message> messages);
}
