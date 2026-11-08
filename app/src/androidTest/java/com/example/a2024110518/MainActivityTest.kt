package com.example.a2024110518

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 界面跳转、菜单对话框与关键控件的 UI 测试。
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun mainScreen_displaysPlayerInfoAndEntries() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.tvNameValue)).check(matches(isDisplayed()))
            onView(withId(R.id.tvHighScoreValue)).check(matches(isDisplayed()))
            onView(withId(R.id.btnEditInfo)).check(matches(isDisplayed()))
            onView(withId(R.id.btnCardGame)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun clickAboutMenu_showsAboutDialog() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withText(R.string.menu_about)).perform(click())

            onView(withText(R.string.about_title)).check(matches(isDisplayed()))
            onView(withText(R.string.about_ok)).perform(click())
        }
    }

    @Test
    fun clickEditInfo_opensPersonInfoScreen() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.btnEditInfo)).perform(click())

            onView(withId(R.id.etName)).check(matches(isDisplayed()))
            onView(withId(R.id.rgGender)).check(matches(isDisplayed()))
            onView(withId(R.id.btnSave)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun clickCardGame_opensCardGameScreen() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.btnCardGame)).perform(click())

            onView(withId(R.id.tvScore)).check(matches(isDisplayed()))
            onView(withId(R.id.rvCards)).check(matches(isDisplayed()))
            onView(withId(R.id.btnReset)).check(matches(isDisplayed()))
        }
    }
}
