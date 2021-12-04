package com.example.traininglog.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity(foreignKeys =  {@ForeignKey(
        entity = Hall.class,
        parentColumns = "id",
        childColumns = "hall_id"
), @ForeignKey(
        entity = Club.class,
        parentColumns = "id",
        childColumns = "club_id"
), @ForeignKey(
        entity = Coach.class,
        parentColumns = "id",
        childColumns = "coach_id"
)})
public class Workout {
    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("start_time")
    @Ignore
    private Date start_time;
    @SerializedName("end_time")
    @Ignore
    private Date end_time;
    @SerializedName("type")
    @ColumnInfo(name = "type")
    private String type;
    @SerializedName("other_type")
    @ColumnInfo(name = "other_type")
    private String other_type;
    @SerializedName("is_carried_out")
    @ColumnInfo(name = "is_carried_out")
    private boolean is_carried_out;
    @SerializedName("coach_replace")
    @Ignore
    private Coach coach;
    @SerializedName("hall")
    @Ignore
    private Hall hall;
    @SerializedName("club")
    @Ignore
    private Club club;
    @SerializedName("on_train")
    @ColumnInfo(name = "on_train")
    private int on_train;
    @SerializedName("dont_know")
    @ColumnInfo(name = "dont_know")
    private int dont_know;
    @SerializedName("not_on_train")
    @ColumnInfo(name = "not_on_train")
    private int not_on_train;
    @SerializedName("is_on")
    @ColumnInfo(name = "is_on")
    private Boolean is_on;

    @ColumnInfo(name = "club_id")
    private int club_id;
    @ColumnInfo(name = "coach_id")
    private int coach_id;
    @ColumnInfo(name = "hall_id")
    private int hall_id;

    @ColumnInfo(name = "start_time")
    private long startTime;
    @ColumnInfo(name = "end_time")
    private long endTime;

    public Workout(int id, Date start_time, Date end_time, String type, String other_type, boolean is_carried_out, Coach coach, Hall hall, Club club, int on_train, int dont_know, int not_on_train, Boolean is_on) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.type = type;
        this.other_type = other_type;
        this.is_carried_out = is_carried_out;
        this.coach = coach;
        this.hall = hall;
        this.club = club;
        this.on_train = on_train;
        this.dont_know = dont_know;
        this.not_on_train = not_on_train;
        this.is_on = is_on;
    }

    public Workout(int id, Date start_time, Date end_time, String type, String other_type, boolean is_carried_out, Coach coach, Hall hall, Club club) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.type = type;
        this.other_type = other_type;
        this.is_carried_out = is_carried_out;
        this.coach = coach;
        this.hall = hall;
        this.club = club;
    }

    public Workout(int id) {
        this.id = id;
    }

    public Workout() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOther_type() {
        return other_type;
    }

    public void setOther_type(String other_type) {
        this.other_type = other_type;
    }

    public boolean isIs_carried_out() {
        return is_carried_out;
    }

    public void setIs_carried_out(boolean is_carried_out) {
        this.is_carried_out = is_carried_out;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getOn_train() {
        return on_train;
    }

    public void setOn_train(int on_train) {
        this.on_train = on_train;
    }

    public int getDont_know() {
        return dont_know;
    }

    public void setDont_know(int dont_know) {
        this.dont_know = dont_know;
    }

    public int getNot_on_train() {
        return not_on_train;
    }

    public void setNot_on_train(int not_on_train) {
        this.not_on_train = not_on_train;
    }

    public Boolean Is_on() {
        return is_on;
    }

    public void setIs_on(Boolean is_on) {
        this.is_on = is_on;
    }

    public Boolean getIs_on() {
        return is_on;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }

    public int getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(int coach_id) {
        this.coach_id = coach_id;
    }

    public int getHall_id() {
        return hall_id;
    }

    public void setHall_id(int hall_id) {
        this.hall_id = hall_id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public long getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", type='" + type + '\'' +
                ", other_type='" + other_type + '\'' +
                ", is_carried_out=" + is_carried_out +
                ", coach=" + coach +
                ", hall=" + hall +
                ", club=" + club +
                ", on_train=" + on_train +
                ", dont_know=" + dont_know +
                ", not_on_train=" + not_on_train +
                ", is_on=" + is_on +
                ", club_id=" + club_id +
                ", coach_id=" + coach_id +
                ", hall_id=" + hall_id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public boolean isInDay(Calendar calendar) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(this.start_time);
        return calendar.get(Calendar.DATE) == calendar1.get(Calendar.DATE);
    }
}
