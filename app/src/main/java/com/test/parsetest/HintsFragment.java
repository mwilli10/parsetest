package com.test.parsetest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.test.parsetest.model.Assignment;

import java.util.List;

/**
 * Created by Morgan on 2/14/16.
 */
public class HintsFragment extends Fragment {

    Button mPapersButton, mProjectsButton ,mExamButton;
    String mCategory = null;
    private static final String CATEGORY = "Category";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hint_categories, container, false);

        mPapersButton = (Button) view.findViewById(R.id.btn_papers);
        mProjectsButton = (Button) view.findViewById(R.id.btn_projects);
        mExamButton = (Button) view.findViewById(R.id.btn_exams);


        mPapersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "Paper";
                Intent intent = new Intent(getActivity(), ScreenSlidePagerActivity.class);
                intent.putExtra(CATEGORY, mCategory);
                startActivity(intent);

            }
        });

        mProjectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;

    }
}
