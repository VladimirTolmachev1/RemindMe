package com.example.vladimir.remindme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladimir.remindme.R;
import com.example.vladimir.remindme.adapter.RemindListAdapter;
import com.example.vladimir.remindme.dto.RemindDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladimir on 11.02.2017.
 */

public class IdeasFragment extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.fragment_ideas;

    public static IdeasFragment getInsatnce(Context context){
        Bundle args = new Bundle();
        IdeasFragment fragment = new IdeasFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_ideas));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerViewIdeas);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(new RemindListAdapter(createMockRemindListData()));

        return view;
    }

    private List<RemindDTO> createMockRemindListData() {
        List<RemindDTO> listData = new ArrayList<>();
        listData.add(new RemindDTO("Idea Item 1"));
        listData.add(new RemindDTO("Idea Item 2"));
        listData.add(new RemindDTO("Idea Item 3"));
        listData.add(new RemindDTO("Idea Item 4"));
        listData.add(new RemindDTO("Idea Item 5"));
        listData.add(new RemindDTO("Idea Item 6"));

        return listData;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
