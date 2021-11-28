package com.example.traininglog.ui.base.messages.update;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Message;

public interface UpdateMessageView extends BaseView {
    void showMessage(Message message);
    void saveMessage();
}
