package com.example.traininglog.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

import javax.xml.namespace.QName;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "user_id"
        ), @ForeignKey(
                entity = Workout.class,
        parentColumns = "id",
        childColumns = "workout_id"
)
})
public class Presence {
    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @Ignore
    @SerializedName("user")
    private User user;
    @Ignore
    @SerializedName("workout")
    private Workout workout;
    @ColumnInfo(name = "is_attend")
    @SerializedName("is_attend")
    @Ignore
    private Boolean is_attend;
    @ColumnInfo(name = "reason")
    @SerializedName("reason")
    private String reason;
    @ColumnInfo(name = "delay")
    @SerializedName("delay")
    private boolean delay;
    @ColumnInfo(name = "early_ret")
    @SerializedName("early_ret")
    private boolean early_ret;

    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "workout_id")
    private int workoutId;

    @ColumnInfo(name = "is_attend")
    private boolean isAttend;

    public boolean isAttend() {
        return isAttend;
    }

    public void setAttend(boolean attend) {
        isAttend = attend;
    }

    public Boolean getIs_attend() {
        return is_attend;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public Presence(int id) {
        this.id = id;
    }

    public Presence(int id, User user, Workout workout, boolean is_attend, String reason, boolean delay, boolean early_ret) {
        this.id = id;
        this.user = user;
        this.workout = workout;
        this.is_attend = is_attend;
        this.reason = reason;
        this.delay = delay;
        this.early_ret = early_ret;
    }

    public Presence() {
    }

    public boolean isInDay(Calendar calendar) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(this.workout.getStart_time());
        return calendar.get(Calendar.DATE) == calendar1.get(Calendar.DATE);
    }

    public Presence(Boolean is_attend) {this.is_attend = is_attend;}


    public Presence(Boolean is_attend, String reason) {this.is_attend = is_attend; this.reason = reason;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Boolean isIs_attend() {
        return is_attend;
    }

    public void setIs_attend(Boolean is_attend) {
        this.is_attend = is_attend;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isDelay() {
        return delay;
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
    }

    public boolean isEarly_ret() {
        return early_ret;
    }

    public void setEarly_ret(boolean early_ret) {
        this.early_ret = early_ret;
    }
}
