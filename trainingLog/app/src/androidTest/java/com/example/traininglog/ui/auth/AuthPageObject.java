package com.example.traininglog.ui.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import com.example.traininglog.R;

import org.hamcrest.Matcher;

public class AuthPageObject {
    private Matcher<View> login = withId(R.id.login_enter);
    private Matcher<View> password = withId(R.id.password_enter);
    private Matcher<View> loginButton = withId(R.id.login);
    private Matcher<View> errorText = withId(R.id.error_text);
    private Matcher<View> incognitoLogin = withId(R.id.incognito);
    private Matcher<View> registrationClick = withId(R.id.reg);
    private Matcher<View> resetPassword = withId(R.id.set_pass);

    public void assertPage() {
        onView(login).check(matches(isDisplayed()));
        onView(password).check(matches(isDisplayed()));
        onView(loginButton).check(matches(isDisplayed()));
    }

    public void inputLogin(String log) {
        onView(login).perform(typeText(log));
    }

    public void inputPass(String pass) {
        onView(password).perform(typeText(pass));
    }

    public void clickLogin() {
        onView(loginButton).perform(click());
    }

    public void errorReturns(String err) {
        onView(errorText).check(matches(isDisplayed()));
        onView(errorText).check(matches(withText(err)));
    }

    public void closeSoftKeyboard() {
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
    }

    public void incognitoClick() {
        onView(incognitoLogin).perform(click());
    }

    public void regClick() {
        onView(registrationClick).perform(click());
    }

    public void resetPasswordClick() { onView(resetPassword).perform(click());}
}
