package com.example.applicationfootjerem.Controllers.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applicationfootjerem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RootFragment extends Fragment {



    // 2 - Method that will create a new instance of CalendrierFragment.
    public static RootFragment newInstance() {
        return(new RootFragment());
    }



        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_root, container, false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        /*
         * When this container fragment is created, we fill it with our first
         * "real" fragment
         */
        transaction.replace(R.id.root_layout_fragment, new CompetitionsFragment());

        transaction.commit();

        return view;
    }

}
