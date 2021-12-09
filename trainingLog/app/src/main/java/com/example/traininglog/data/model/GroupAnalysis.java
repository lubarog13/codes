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
        @SerializedName("workout__club__group")
        private String group;
        @SerializedName("pcount")
        private int presenceCount;
        @SerializedName("club__group")
        private String clubGroup;
        @SerializedName("wcount")
        private int wcount;

        public int getWcount() {
            return wcount;
        }

        public void setWcount(int wcount) {
            this.wcount = wcount;
        }

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
                    ", group='" + group + '\'' +
                    ", presenceCount=" + presenceCount +
                    ", clubGroup='" + clubGroup + '\'' +
                    '}';
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getClubGroup() {
            return clubGroup;
        }

        public void setClubGroup(String clubGroup) {
            this.clubGroup = clubGroup;
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

        public DayType(String day) {
            this.day = day;
        }

        public DayType() {
        }
    }
}
