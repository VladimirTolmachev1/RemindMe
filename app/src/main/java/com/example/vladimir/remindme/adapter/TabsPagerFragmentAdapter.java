package com.example.vladimir.remindme.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.vladimir.remindme.fragment.ExampleFragment;

/**
 * Created by vladimir on 11.02.2017.
 */

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);

        tabs = new String[]{
                "tab 1",
                "Notification",
                "tab 3"
        };
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return ExampleFragment.getInsatnce();

            case 1:
                return ExampleFragment.getInsatnce();

            case 2:
                return ExampleFragment.getInsatnce();

        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
