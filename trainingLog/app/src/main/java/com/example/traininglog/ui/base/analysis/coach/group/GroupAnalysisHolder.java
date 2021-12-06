package com.example.traininglog.ui.base.analysis.coach.group;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.TypesResponse;

public class GroupAnalysisHolder extends RecyclerView.ViewHolder {
    private TextView mCardioCount;
    private TextView mStrengthCount;
    private TextView mAnotherCount;
    private TextView mForAllCount;
    private TextView mForTechCount;
    private TextView mTotalCountText;
    private TextView mTotalCount;
    private TextView mTypeText;
    public GroupAnalysisHolder(@NonNull View itemView) {
        super(itemView);
        mTotalCount = itemView.findViewById(R.id.total_count);
        mTypeText = itemView.findViewById(R.id.typeText);
        Shader textShader = new LinearGradient(0, 0,0, mTotalCount.getTextSize(),
                new int[]{
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#ED6C30"),
                        Color.parseColor("#1A237E"),
                }, null, Shader.TileMode.CLAMP);
        mTotalCount.getPaint().setShader(textShader);
        mTotalCount.setTextColor(Color.parseColor("#F8000D"));
        mTotalCountText = itemView.findViewById(R.id.trainings);
        Shader textShader1 = new LinearGradient(0, 0,0, mTotalCountText.getTextSize(),
                new int[]{
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#F8000D"),
                        Color.parseColor("#ED6C30"),
                        Color.parseColor("#1A237E"),
                }, null, Shader.TileMode.CLAMP);
        mTotalCountText.getPaint().setShader(textShader1);
        mTotalCountText.setTextColor(Color.parseColor("#F8000D"));
        mCardioCount = itemView.findViewById(R.id.cardio_workout);
        mForAllCount = itemView.findViewById(R.id.all_workout);
        mForTechCount = itemView.findViewById(R.id.technic_workout);
        mStrengthCount = itemView.findViewById(R.id.strong_workout);
        mAnotherCount = itemView.findViewById(R.id.other_workout);
    }

    public void bind(TypesResponse item, String name) {
        mTypeText.setText(name);
        mCardioCount.setText(String.format("%s кардио", String.valueOf(item.getCardio())));
        mStrengthCount.setText(String.format("%s силовых", String.valueOf(item.getStrength())));
        mForAllCount.setText(String.format("%s общих", String.valueOf(item.getFor_all())));
        mAnotherCount.setText(String.format("%s других", String.valueOf(item.getAnother())));
        mForTechCount.setText(String.format("%s на технику", String.valueOf(item.getFor_tech())));
        int totalCount = item.getFor_all() + item.getFor_tech() + item.getAnother() + item.getCardio() + item.getStrength();
        mTotalCountText.setText(getTotalCountText(totalCount));
    }

    private String getTotalCountText(int totalCount) {
        String totalCountText = "тренировки";
        if(totalCount==1) return "тренировка";
        if (totalCount<5) return totalCountText;
        if(totalCount<21) return "тренировок";
        if (totalCount%10==0) return "тренировок";
        if(totalCount%10==1) return "тренировка";
        if (totalCount%10<5) return totalCountText;
        return "тренировок";
    }
}
