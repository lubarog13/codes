package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupAnalysis {
    @SerializedName("Stat")
    private List<GroupAnalysisItem> items;

    public List<GroupAnalysisItem> getItems() {
        return items;
    }

    public void setItems(List<GroupAnalysisItem> items) {
        this.items = items;
    }

    public static class GroupAnalysisItem {
        @SerializedName("workout__club__id")
        private int clubId;
        @SerializedName("pcount")
        private int presenceCount;

        public int getClubId() {
            return clubId;
        }

        public void setClubId(int clubId) {
            this.clubId = clubId;
        }

        public int getPresenceCount() {
            return presenceCount;
        }

        public void setPresenceCount(int presenceCount) {
            this.presenceCount = presenceCount;
        }

        @Override
        public String toString() {
            return "GroupAnalysisItem{" +
                    "clubId=" + clubId +
                    ", presenceCount=" + presenceCount +
                    '}';
        }
    }

    public static class DayType {
        @SerializedName("day")
        private String day;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }
}
