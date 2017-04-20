package com.codekul.fragments;


import android.graphics.Color;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Click click = new Click();
        final View view = inflater.inflate(R.layout.fragment_one, container, false);
        view.findViewById(R.id.btnRed).setOnClickListener(click);
        view.findViewById(R.id.btnGreen).setOnClickListener(click);
        view.findViewById(R.id.btnBlue).setOnClickListener(click);
        view.findViewById(R.id.btnLogin).setOnClickListener(click);
        return view;
    }

    private class Click implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnRed) {
                ((MainActivity)getActivity()).loadFragment(TwoFragment.getInstance(Color.RED));
            }
            else if(v.getId() == R.id.btnGreen) {
                ((MainActivity)getActivity()).loadFragment(TwoFragment.getInstance(Color.GREEN));
            }
            else if(v.getId() == R.id.btnBlue){
                ((MainActivity)getActivity()).loadFragment(TwoFragment.getInstance(Color.BLUE));
            }
            else {
                ((MainActivity)getActivity()).loadFragment(new LoginFragment());
            }
        }
    }
}
