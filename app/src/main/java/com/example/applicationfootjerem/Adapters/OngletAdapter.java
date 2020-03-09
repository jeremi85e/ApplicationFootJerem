package com.example.applicationfootjerem.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.applicationfootjerem.Controllers.Fragments.CalendrierFragment;
import com.example.applicationfootjerem.Controllers.Fragments.ClassementFragment;
import com.example.applicationfootjerem.Controllers.Fragments.CompetitionsFragment;
import com.example.applicationfootjerem.Controllers.Fragments.LiveFragment;
import com.example.applicationfootjerem.Controllers.Fragments.RootFragment;

public class OngletAdapter extends FragmentPagerAdapter {

    public OngletAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(3); // Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return LiveFragment.newInstance();
            case 1 :
                return RootFragment.newInstance();
            case 2 :
                return  ClassementFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Live Matchs";
            case 1 :
                return "Calendrier Résultats";
            case 2 :
                return  "Classement";
            default:
                return "Onglet";
        }
    }
}
