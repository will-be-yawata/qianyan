package com.example.administrator.langues.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.langues.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment {
    public FindFragment() {}
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }
}
