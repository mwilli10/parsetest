package com.test.parsetest;

import android.support.v4.app.Fragment;


public class AssignmentListActivity extends SingleFragmentActivity{

    protected Fragment createFragment() {
        return new AssignmentListFragment();
    }

}