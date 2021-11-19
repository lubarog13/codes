package com.example.traininglog.ui.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.traininglog.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void navigateHome() {
        onView(withId(R.id.login_enter)).perform(typeText("ivan_smirnov_10"));
        onView(withId(R.id.password_enter)).perform(typeText("hello123 Somebody"));
        onView(withId(R.id.login)).perform(click());
        SystemClock.sleep(1500);
        onView(withId(R.id.week_workouts)).check(matches(withText("Тренировки на этой неделе")));
    }

    @Test
    public void wrongPassword() {
        onView(withId(R.id.login_enter)).perform(typeText("ivan_smirnov_10"));
        onView(withId(R.id.password_enter)).perform(typeText("hello123Somebody"));
        onView(withId(R.id.login)).perform(click());
        SystemClock.sleep(1500);
        onView(withId(R.id.error_text)).check(matches(isDisplayed()));
        onView(withId(R.id.error_text)).check(matches(withText("Неверный логин или пароль")));
    }


    @Test
    public void noLoginEnter() {
        onView(withId(R.id.password_enter)).perform(typeText("hello123 Somebody"));
        onView(withId(R.id.login)).perform(click());
        SystemClock.sleep(150);
        onView(withId(R.id.error_text)).check(matches(isDisplayed()));
        onView(withId(R.id.error_text)).check(matches(withText("Введите логин")));
    }

    @Test
    public void noPasswordEnter() {
        onView(withId(R.id.login_enter)).perform(typeText("ivan_smirnov_10"));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        SystemClock.sleep(150);
        onView(withId(R.id.error_text)).check(matches(isDisplayed()));
        onView(withId(R.id.error_text)).check(matches(withText("Введите пароль")));
    }

    @Test
    public void asGuestEnter() {
        onView(withId(R.id.incognito)).perform(click());
        SystemClock.sleep(1500);
        onView(withId(R.id.week_workouts)).check(matches(withText("Тренировки на этой неделе")));
    }
}