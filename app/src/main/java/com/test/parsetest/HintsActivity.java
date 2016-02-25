package com.test.parsetest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by Morgan on 12/8/15.
 */
public class HintsActivity extends SingleFragmentActivity {

    protected Fragment createFragment(){
        return new HintsFragment();

    }
}