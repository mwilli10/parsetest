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



//    private String getHints(String category, int page) {
//        switch (category) {
//            case "Paper":
//                return paper[page];
//
//            case "Project":
//                return project[page];
//
//            case "Exam":
//                return exam[page];
//            default:
//                return " ";
//
//
//        }

//    }
}