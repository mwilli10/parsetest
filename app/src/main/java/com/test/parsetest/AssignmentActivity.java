package com.test.parsetest;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class AssignmentActivity extends SingleFragmentActivity {

    private static final String EXTRA_DIBBIT_ID = "com.test.parsetest.dibbit_id";

    public static Intent newIntent(Context packageContext, UUID dibbitId) {
        Intent intent = new Intent(packageContext, AssignmentActivity.class);
        intent.putExtra(EXTRA_DIBBIT_ID, dibbitId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID dibbitId = (UUID) getIntent().getSerializableExtra(EXTRA_DIBBIT_ID);
        return AssignmentFragment.newInstance(dibbitId);
    }


}











