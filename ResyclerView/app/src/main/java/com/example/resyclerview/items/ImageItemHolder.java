package com.example.resyclerview.items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resyclerview.R;
import com.squareup.picasso.Picasso;

public class ImageItemHolder extends RecyclerView.ViewHolder {
    private View mView;
    private ImageView mImage;
    private TextView mText;
    public ImageItemHolder(@NonNull View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.image);
        mView = itemView;
        mText = itemView.findViewById(R.id.name);
    }
    public void bind(ImageItem imageItem) {
        mText.setText(imageItem.getName());
        Picasso.get().load(imageItem.getImage()).into(mImage);
        mImage.setVisibility(View.VISIBLE);
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }
}
