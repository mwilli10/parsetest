package com.test.parsetest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.test.parsetest.model.Assignment;
import com.test.parsetest.model.Hints;

import java.util.ArrayList;
import java.util.List;

import com.test.parsetest.ScreenSlidePageFragment;
import com.test.parsetest.model.Hints;

import java.util.List;

public class ScreenSlidePagerActivity extends FragmentActivity {

    private static final String CATEGORY = "Category";
    private static final String HINT = "Hint";
    public static final String ARG_PAGE = "page";
    private String mCategory = null;
    public List<Hints> mHints;
    private static HintsLab sHintsLab;

    private String[] paper = {"Have someone edit your paper for things that don’t make sense before finalizing. Read your paper out loud to spot grammatical errors.",
            " Review your notes and textbooks to get supporting facts for your paper. Write the body of your paper first, then go back to the intro and conclusion to know what to address.",
            "Write an outline of your paper (intro, paragraph 1 topic, paragraph 2 topic, conclusion). Create a rough draft of your paper – don’t worry about making smooth transitions, just get the important parts down first!",
            "If you have a choice of topics, chose the one you understand best. Discuss your strongest arguments first, then your weakest argument last. ",
            "Read the question and break it down to smaller parts that you need to answer. Ask your teacher for help formulating your topic or thesis statement."};
    private String[] exam = {"Create practice tests to review what you know/don’t know. Check over your answers before turning it in.",
            "Use notecards to study. Teach someone else what you are studying! Teaching helps you put together knowledge in your head.",
            "Create a study schedule of half an hour time blocks the week before the test. Break down the unit (ex. Westward expansion) into smaller topics (ex. Louisiana purchase, Lewis/Clark expedition etc.)",
            "Before checking your notes, take a minute to see what you remember about the topic of the test. Ask yourself: which topics did I not understand that well?",
            "Ask questions in class on topics you are unclear about. When taking the test, make sure you highlight key words like NOT, OR, AND, etc."};
    private String[] project = {"If you have to present, practice your presentation in front of friends/family. If you created a poster or model, make sure everything is securely glued or taped in place the night before you bring it to class.",
            "Use the library or the internet to find more facts for more evidence. Revisit the rubric to make sure you don’t miss anything as you work.",
            "Break it down to smaller parts and create deadlines for yourself for those parts. Gather all the supplies you need before starting",
            " For group projects, assign roles based on people’s interests and strengths. Allow more time to work on the tasks that you struggle with",
            "Read the rubric thoroughly. Ask the teacher if you are unsure how to do something."};

    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                mCategory = null;
            } else {
                mCategory = extras.getString(CATEGORY);
            }
        } else {
            mCategory = (String) savedInstanceState.getSerializable(CATEGORY);
        }

        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }



    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int page;
            ScreenSlidePageFragment fragment = ScreenSlidePageFragment.create(position);
            page = fragment.getArguments().getInt(ARG_PAGE);
            Bundle bundle = new Bundle();
//            bundle.putString(HINT, getHint(mCategory, page));
            bundle.putString(CATEGORY, mCategory);
            bundle.putInt(ARG_PAGE, page);
            fragment.setArguments(bundle);


            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }



    private String getHints(String category, int page) {
        switch (category) {
            case "Paper":
                return paper[page];

            case "Project":
                return project[page];

            case "Exam":
                return exam[page];
            default:
                return " ";


        }

    }
}