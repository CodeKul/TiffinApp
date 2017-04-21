package com.codekul.fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(TwoFragment.getInstance(Color.BLACK));
    }

    public void loadFragment(Fragment fragment) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction txn = manager.beginTransaction();
       // txn.add(R.id.frameFrag, fragment);
        txn.replace(R.id.frameFrag, fragment);
        txn.commit();
    }

    public void loadFragmentV2(Fragment fragment) {

        String backStateName = fragment.getClass().getName();
        Log.i("@codekul", "Back stack name - "+backStateName);

        int color = -1;
        Bundle bundle = fragment.getArguments();
        if(bundle != null){
            color = bundle.getInt("myColor");
        }
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        Log.i("@codekul", "fragmentPopped - "+fragmentPopped);
        if (!fragmentPopped ){ //fragment not in back stack, create it.
            if(backStateName.equals(TwoFragment.class.getName())) {

                int inColor = -2;
                Fragment frag = manager.findFragmentByTag(backStateName);
                if(frag != null) {
                    Bundle bundleFrag = frag.getArguments();
                    if (bundleFrag != null) {
                        inColor = bundleFrag.getInt("myColor");
                    }
                }
                if(color != inColor) {
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.replace(R.id.frameFrag, fragment, backStateName);
                    ft.addToBackStack(backStateName);
                    ft.commit();
                }
            }
            else {
                if(manager.findFragmentByTag(backStateName) == null) {
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.replace(R.id.frameFrag, fragment, backStateName);
                    ft.addToBackStack(backStateName);
                    ft.commit();
                }
            }
        }
    }
}
