package com.elegion.test.behancer.ui.projects;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elegion.test.behancer.R;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.utils.DateUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectsHolder extends RecyclerView.ViewHolder {

    private static final int FIRST_OWNER_INDEX = 0;

    private ImageView mImage;
    private TextView mName;
    private TextView mUsername;
    private TextView mPublishedOn;

    public ProjectsHolder(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.image);
        mName = itemView.findViewById(R.id.tv_name);
        mUsername = itemView.findViewById(R.id.tv_username);
        mPublishedOn = itemView.findViewById(R.id.tv_published);
    }

    public void bind(Project item, ProjectsAdapter.OnItemClickListener onItemClickListener) {
        Picasso.with(mImage.getContext()).load(item.getCover().getPhotoUrl())
                .fit()
                .into(mImage);

        mName.setText(item.getName());
        mUsername.setText(item.getOwners().get(FIRST_OWNER_INDEX).getUsername());
        mPublishedOn.setText(DateUtils.format(item.getPublishedOn()));

        if (onItemClickListener != null) {
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(
                    item.getOwners()
                            .get(FIRST_OWNER_INDEX)
                            .getUsername()
            ));
        }
    }
}
