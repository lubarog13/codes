package com.example.traininglog.ui.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import com.example.traininglog.R;

import org.hamcrest.Matcher;

public class HomePageObject {
    private Matcher<View> header = withId(R.id.week_workouts);

    public void assertPage() {
        onView(header).check(matches(isDisplayed()));
        onView(header).check(matches(withText("Тренировки на этой неделе")));
    }
}
