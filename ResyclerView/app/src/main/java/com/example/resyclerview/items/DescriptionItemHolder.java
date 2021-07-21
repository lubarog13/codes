package com.example.resyclerview.items;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resyclerview.R;
import com.squareup.picasso.Picasso;

public class DescriptionItemHolder extends RecyclerView.ViewHolder {
    private View mView;
    private TextView mText;
    private TextView mDescription;

    public DescriptionItemHolder(@NonNull View itemView) {
        super(itemView);
        mText = itemView.findViewById(R.id.name);
        mView = itemView;
        mDescription = itemView.findViewById(R.id.description);
    }
    public void bind(DescriptionItem descriptionItem) {
        mText.setText(descriptionItem.getName());
        mDescription.setText(descriptionItem.getDescription());
        mDescription.setVisibility(View.VISIBLE);
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }
}
