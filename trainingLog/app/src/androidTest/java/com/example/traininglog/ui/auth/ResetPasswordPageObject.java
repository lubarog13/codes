package com.example.traininglog.ui.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import com.example.traininglog.R;

import org.hamcrest.Matcher;

public class ResetPasswordPageObject {
    private Matcher<View> header = withId(R.id.resetText);

    public void assertPage() {
        onView(header).check(matches(isDisplayed()));
    }
}
