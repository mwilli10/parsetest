package com.test.parsetest;

import android.support.v4.app.Fragment;


public class GradeListActivity extends SingleFragmentActivity{

    protected Fragment createFragment() {
        return new GradeListFragment();
    }

}