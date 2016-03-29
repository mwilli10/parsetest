package com.test.parsetest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.test.parsetest.model.Assignment;
import com.test.parsetest.model.Grade;

import java.util.List;
import java.util.UUID;

/**
 * Created by Morgan on 2/2/16.
 */
public class GradePagerActivity  extends AppCompatActivity{

    private static final String EXTRA_DIBBIT_ID =
            "com.test.parsetest.dibbit_id";

    private ViewPager mViewPager;
    private List<Grade> mDibbits;

    public static Intent newIntent(Context packageContext, UUID dibbitId) {
        Intent intent = new Intent(packageContext, GradePagerActivity.class);
        intent.putExtra(EXTRA_DIBBIT_ID, dibbitId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dibbit_pager);
        UUID dibbitId = (UUID) getIntent().getSerializableExtra(EXTRA_DIBBIT_ID);



        mViewPager = (ViewPager) findViewById(R.id.activity_dibbit_pager_view_pager);

        mDibbits = GradeLab.get(this).getDibbits();
        FragmentManager fragmentManager = getSupportFragmentManager();



        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Grade dibbit = mDibbits.get(position);
                return GradeFragment.newInstance(dibbit.getId());
            }

            @Override
            public int getCount() {
                return mDibbits.size();
            }
        });

        for (int i = 0; i < mDibbits.size(); i++) {
            if (mDibbits.get(i).getId().equals(dibbitId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}





