package com.example.vladimir.remindme.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.vladimir.remindme.fragment.AbstractTabFragment;
import com.example.vladimir.remindme.fragment.BirthdaysFragment;
import com.example.vladimir.remindme.fragment.HistoryFragment;
import com.example.vladimir.remindme.fragment.IdeasFragment;
import com.example.vladimir.remindme.fragment.TodoFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladimir on 11.02.2017.
 */

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);

        this.context = context;
        initTabMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabMap(Context context) {
        tabs = new HashMap<>();

        tabs.put(0, HistoryFragment.getInsatnce(context));
        tabs.put(1, IdeasFragment.getInsatnce(context));
        tabs.put(2, TodoFragment.getInsatnce(context));
        tabs.put(3, BirthdaysFragment.getInsatnce(context));
    }
}
