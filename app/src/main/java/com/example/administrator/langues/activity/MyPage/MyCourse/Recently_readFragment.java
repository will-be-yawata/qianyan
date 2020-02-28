package com.example.administrator.langues.activity.MyPage.MyCourse;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.langues.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Recently_readFragment extends Fragment {


    public Recently_readFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recently_read, container, false);
    }

}
