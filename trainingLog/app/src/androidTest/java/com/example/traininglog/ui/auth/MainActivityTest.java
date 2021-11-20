package com.example.traininglog.ui.auth;

import android.os.SystemClock;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    private AuthPageObject pageObject = new AuthPageObject();
    private HomePageObject homePageObject = new HomePageObject();
    private RegistrationPageObject registrationPageObject = new RegistrationPageObject();
    private ResetPasswordPageObject resetPasswordPageObject = new ResetPasswordPageObject();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void navigateHome() {
        pageObject.assertPage();
        pageObject.inputLogin("ivan_smirnov_10");
        pageObject.inputPass("hello123 Somebody");
        pageObject.clickLogin();
        SystemClock.sleep(1500);
        homePageObject.assertPage();
    }

    @Test
    public void wrongPassword() {
        pageObject.assertPage();
        pageObject.inputLogin("ivan_smirnov_10");
        pageObject.inputPass("hello123Somebody");
        pageObject.clickLogin();
        SystemClock.sleep(500);
        pageObject.errorReturns("Неверный логин или пароль");
    }

    @Test
    public void wrongLogin() {
        pageObject.assertPage();
        pageObject.inputLogin("ivan_smirnov_11");
        pageObject.inputPass("hello123 Somebody");
        pageObject.clickLogin();
        SystemClock.sleep(500);
        pageObject.errorReturns("Неверный логин или пароль");
    }


    @Test
    public void noLoginEnter() {
        pageObject.inputPass("hello123 Somebody");
        pageObject.clickLogin();
        SystemClock.sleep(150);
        pageObject.errorReturns("Введите логин");
    }

    @Test
    public void noPasswordEnter() {
        pageObject.inputLogin("ivan_smirnov_10");
        pageObject.closeSoftKeyboard();
        pageObject.clickLogin();
        SystemClock.sleep(150);
        pageObject.errorReturns("Введите пароль");
    }

    @Test
    public void asGuestEnter() {
        pageObject.incognitoClick();
        SystemClock.sleep(500);
        homePageObject.assertPage();
    }

    @Test
    public void registrationEnter() {
        pageObject.regClick();
        SystemClock.sleep(20);
        registrationPageObject.assertPage();
    }

    @Test
    public void resetPasswordEnter() {
        pageObject.resetPasswordClick();
        SystemClock.sleep(20);
        resetPasswordPageObject.assertPage();
    }

}