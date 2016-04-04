package com.test.parsetest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.test.parsetest.model.Assignment;
import com.test.parsetest.model.Hints;

import java.util.List;

/**
 * Created by Morgan on 2/15/16.
 */
public class ScreenSlidePageFragment extends Fragment {

    private TextView mHintText;
    private CheckBox mhintCheck;
    private String mCategory;
    private String mHint;
    private Button mDone;
    private List<Hints> mHints;
    private static HintsLab sHintsLab;
    private static final String CATEGORY = "Category";
    private static final String HINT = "Hint";



    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;


    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        setHasOptionsMenu(true);
        if (bundle != null) {
            mCategory = bundle.getString(CATEGORY);
            mHint= bundle.getString(HINT);
            mPageNumber = bundle.getInt(ARG_PAGE);
        }
        mCategory = getArguments().getString(CATEGORY);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        HintsLab hintsLab = HintsLab.get(getActivity());
        mHints = hintsLab.getHints(mCategory);

    }

//    mHints.get(mPageNumber).getHintText()

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            setHasOptionsMenu(true);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_hints_slide_screen, container, false);
            if (mCategory.equals("Paper")){
                rootView.setBackgroundColor(getResources().getColor(R.color.papers));

            }
            else if(mCategory.equals("Project")){
                rootView.setBackgroundColor(getResources().getColor(R.color.projects));
            }
            else{
                rootView.setBackgroundColor(getResources().getColor(R.color.exams));
            }

                    // Set the title view to show the page number.
                    ((TextView) rootView.findViewById(android.R.id.text1)).setText(mHints.get(mPageNumber).getHintText());
        mhintCheck =  (CheckBox) rootView.findViewById(android.R.id.checkbox);
            mhintCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    if (mhintCheck.isChecked()) {
                        mHints.get(mPageNumber).incrementHelpful();
                    }
                    else{
                        mHints.get(mPageNumber).decrementHelpful();
                    }
                }
            });

            mDone = (Button) rootView.findViewById(android.R.id.button1);
            mDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();

                }
            });
        return rootView;
    }



    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

}



