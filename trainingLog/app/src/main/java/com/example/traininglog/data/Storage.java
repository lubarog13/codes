package com.example.traininglog.data;

import android.util.Log;

import com.example.traininglog.data.database.EsheduleDao;
import com.example.traininglog.data.model.Building;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.HallsResponse;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.PresencesResponse;
import com.example.traininglog.data.model.Workout;
import com.example.traininglog.data.model.WorkoutResponse;
import com.example.traininglog.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Storage {
    private EsheduleDao mDao;

    public Storage(EsheduleDao mDao) {
        this.mDao = mDao;
    }

    public void insertWorkouts(WorkoutResponse response) {
        Log.e("storage", "hi");
        List<Workout> workouts = response.getWorkouts();
        for(Workout workout: workouts){
            workout.setStartTime(DateUtils.ToTimestamp(workout.getStart_time()));
            workout.setEndTime(DateUtils.ToTimestamp(workout.getEnd_time()));
            workout.setClub_id(workout.getClub().getId());
            Coach coach;
            if(workout.getCoach()!=null) {
                workout.setCoach_id(workout.getCoach().getId());
                 coach = workout.getCoach();
            }
            else{
                workout.setCoach_id(workout.getClub().getCoach().getId());
                 coach = workout.getClub().getCoach();
            }
            workout.setHall_id(workout.getHall().getId());
            Club club = workout.getClub();
            coach.setUserId(coach.getUser().getId());
            Hall hall = workout.getHall();
            Building building = hall.getBuilding();
            hall.setBuilding_id(building.getId());
            mDao.insertBuilding(building);
            mDao.insertClub(club);
            mDao.insertHall(hall);
            mDao.insertUser(coach.getUser());
            mDao.insertCoach(coach);
        }
        mDao.insertWorkouts(workouts);
        Log.e("storage", this.getWeekWorkout().getWorkouts().toString());
    }

    public WorkoutResponse getWeekWorkout() {
        List<Workout> workouts = mDao.selectWorkouts();
        workouts.removeIf(workout -> DateUtils.FromTimestamp(workout.getStartTime())
                .compareTo(DateUtils.lastMonday(new Date()))<0 ||
                DateUtils.FromTimestamp(workout.getStartTime())
                        .compareTo(DateUtils.nextMonday(new Date()))>0);
        for(Workout workout: workouts){
            workout.setStart_time(DateUtils.FromTimestamp(workout.getStartTime()));
            workout.setEnd_time(DateUtils.FromTimestamp(workout.getEndTime()));
            workout.setHall(mDao.selectHall(workout.getHall_id()));
            Coach coach = mDao.selectCoach(workout.getCoach_id());
            coach.setUser(mDao.selectUser(coach.getUserId()));
            workout.setCoach(coach);
            workout.setClub(mDao.selectClub(workout.getClub_id()));
        }
        return new WorkoutResponse(workouts);
    }

    public WorkoutResponse getMonthWorkout(int month) {
        List<Workout> workouts = mDao.selectWorkouts();
        workouts.removeIf(workout -> {
           Calendar calendar =  Calendar.getInstance();
           calendar.setTime(workout.getStart_time());
           return calendar.get(Calendar.MONTH)!=month;
        });
        for(Workout workout: workouts){
            workout.setStart_time(DateUtils.FromTimestamp(workout.getStartTime()));
            workout.setEnd_time(DateUtils.FromTimestamp(workout.getEndTime()));
            workout.setHall(mDao.selectHall(workout.getHall_id()));
            Coach coach = mDao.selectCoach(workout.getCoach_id());
            coach.setUser(mDao.selectUser(coach.getUserId()));
            workout.setCoach(coach);
            workout.setClub(mDao.selectClub(workout.getClub_id()));
        }
        return new WorkoutResponse(workouts);
    }



    public void insertMonthPresences(PresencesResponse response) {
        List<Presence> presences = new ArrayList<>(response.getPresences());
        mDao.deletePresences();
        List<Workout> workouts = new ArrayList<>();
        for(Presence presence: presences) {
            presence.setWorkoutId(presence.getWorkout().getId());
            presence.setUserId(presence.getUser().getId());
            if(presence.getIs_attend()==null){
                presence.setAttend(false);
            }
            else presence.setAttend(presence.getIs_attend());
            workouts.add(presence.getWorkout());
        }
        this.insertWorkouts(new WorkoutResponse(workouts));
        mDao.insertUser(presences.get(0).getUser());
        Log.e("responce", String.valueOf(presences.size()));
        mDao.insertPresences(presences);
    }

    public PresencesResponse getMonthPresences(int month) {
        List<Presence> presences = mDao.selectPresences();
        for(Presence presence : presences) {
            Workout workout = mDao.selectWorkout(presence.getId());
            workout.setStart_time(DateUtils.FromTimestamp(workout.getStartTime()));
            workout.setEnd_time(DateUtils.FromTimestamp(workout.getEndTime()));
            workout.setHall(mDao.selectHall(workout.getHall_id()));
            Coach coach = mDao.selectCoach(workout.getCoach_id());
            if(coach==null) {
                coach = new Coach();
                coach.setUserId(2);
            }
            coach.setUser(mDao.selectUser(coach.getUserId()));
            workout.setCoach(coach);
            workout.setClub(mDao.selectClub(workout.getClub_id()));
            presence.setWorkout(workout);
            presence.setIs_attend(presence.isAttend());
        }
        for (Presence presence : new ArrayList<>(presences)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(presence.getWorkout().getStart_time());
            Log.e("month", String.valueOf(calendar.get(Calendar.MONTH)));
            if(calendar.get(Calendar.MONTH)!=(month-1)) {
                presences.remove(presence);
            }
        }
        return new PresencesResponse(presences);
    }

    public List<Coach> getCoaches() {
        List<Coach> coaches = mDao.selectCoaches();
        for (Coach coach: coaches) {
            coach.setUser(mDao.selectUser(coach.getUserId()));
        }
        return coaches;
    }

    public void insertCoaches(List<Coach> coaches) {
        for (Coach coach : coaches) {
            coach.setUserId(coach.getUser().getId());
            mDao.insertUser(coach.getUser());
            mDao.insertCoach(coach);
        }
    }

    public void insertBuildings(List<Building> buildings) {
        mDao.insertBuildings(buildings);
    }

    public List<Building> getBuildings() {
        return mDao.selectBuildings();
    }


    public void insertHalls(HallsResponse response) {
        List<Hall> halls = response.getHalls();
        for (Hall hall: halls) {
            hall.setBuilding_id(hall.getBuilding().getId());
        }
        mDao.insertHalls(halls);
    }

    public HallsResponse getHalls(int building_id) {
        List<Hall> halls = mDao.selectHalls(building_id);
        Building building = mDao.selectBuilding(building_id);
        for (Hall hall: halls) {
            hall.setBuilding(building);
        }
        return new HallsResponse(halls);
    }

    public void insertHall(Hall hall) {
        hall.setBuilding_id(hall.getBuilding().getId());
        mDao.insertBuilding(hall.getBuilding());
        mDao.insertHall(hall);
    }


    public Hall getHall(int hall_id) {
        Hall hall = mDao.selectHall(hall_id);
        hall.setBuilding(mDao.selectBuilding(hall.getBuilding_id()));
        return hall;
    }

    public void clearTables() {
        Log.e("storage", "all right");
        mDao.deletePresences();
        Log.e("storage", "all right");
        mDao.deleteWorkouts();
        Log.e("storage", "all right");
        mDao.deleteClubs();
        Log.e("storage", "all right");
        mDao.deleteHalls();
        Log.e("storage", "all right");
        mDao.deleteCoaches();
        Log.e("storage", "all right");
        mDao.deleteUsers();
        Log.e("storage", "all right");
        mDao.deleteBuildings();
    }

    public interface StorageOwner {
        Storage obtainStorage();
    }
}
