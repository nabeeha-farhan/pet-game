package group47.cs2212.petgame;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class ParentalControls implements Serializable {

    private String password;
    private int dailyTimeLimit;
    private LocalTime startAllowedTime;
    private LocalTime endAllowedTime;
    private final Map<LocalDate, Integer> playtimeTracker;

    public ParentalControls(String initialPassword) {
        this.password = initialPassword;
        this.dailyTimeLimit = 60; // Default daily limit
        this.startAllowedTime = LocalTime.of(7, 0);
        this.endAllowedTime = LocalTime.of(21, 0);
        this.playtimeTracker = new HashMap<>();
    }

    public boolean isPasswordSet() {
        return password != null && !password.isEmpty();
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean authenticate(String inputPassword) {
        return password != null && password.equals(inputPassword);
    }

    public void setDailyTimeLimit(int minutes) {
        this.dailyTimeLimit = minutes;
    }

    public String getPlaytimeStatistics() {
        LocalDate today = LocalDate.now();
        int todayPlaytime = playtimeTracker.getOrDefault(today, 0);
        return "Today's playtime: " + todayPlaytime + " minutes / " + dailyTimeLimit + " minutes.";
    }

    public void setRestrictedHours(LocalTime start, LocalTime end) {
        this.startAllowedTime = start;
        this.endAllowedTime = end;
    }

    public boolean canPlay() {
        LocalDate today = LocalDate.now();
        int todayPlaytime = playtimeTracker.getOrDefault(today, 0);

        // Check if the current time is within allowed hours
        LocalTime now = LocalTime.now();
        boolean withinAllowedHours = now.isAfter(startAllowedTime) && now.isBefore(endAllowedTime);

        // Check if the daily time limit has been exceeded
        boolean underDailyLimit = todayPlaytime < dailyTimeLimit;

        // Return true only if both conditions are satisfied
        return withinAllowedHours && underDailyLimit;
    }


}

