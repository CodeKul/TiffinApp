package com.codekul.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {

    public static TwoFragment getInstance(int color) {
        TwoFragment frag = new TwoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("myColor", color);
        frag.setArguments(bundle);

        return frag;
    }

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();

        final View rootView = inflater.inflate(R.layout.fragment_two, container, false);
        rootView.setBackgroundColor(args.getInt("myColor"));

        return rootView;
    }

}
