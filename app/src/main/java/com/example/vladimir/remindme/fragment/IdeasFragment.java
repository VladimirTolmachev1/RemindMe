package com.example.vladimir.remindme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladimir.remindme.MainActivity;
import com.example.vladimir.remindme.R;
import com.example.vladimir.remindme.adapter.RemindListAdapter;
import com.example.vladimir.remindme.models.Item;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vladimir on 11.02.2017.
 */

public class IdeasFragment extends AbstractTabFragment implements MainActivity.FragmentListener{

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
        realmDb = Realm.getDefaultInstance();

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerViewIdeas);
        rv.setLayoutManager(new LinearLayoutManager(context));

        cardList = gelAllCards();
        listAdapter = new RemindListAdapter(getActivity(), cardList, this);

        rv.setAdapter(listAdapter);

        return view;
    }

    private List<Item> gelAllCards(){
        realmDb.beginTransaction();
        List<Item> resultList = realmDb.where(Item.class).equalTo("type", 0).findAll();

        realmDb.commitTransaction();

        return resultList;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public void updateFragmentList() {
        if(listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void deleteFragmentList() {
        final RealmResults<Item> result = realmDb.where(Item.class).findAll();

        realmDb.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                result.deleteAllFromRealm();
                listAdapter.notifyDataSetChanged();
            }
        });
    }
}
