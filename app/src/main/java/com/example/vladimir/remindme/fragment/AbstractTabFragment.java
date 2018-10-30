package com.example.vladimir.remindme.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.vladimir.remindme.EditActivity;
import com.example.vladimir.remindme.Interfaces.RemindOnClickListener;
import com.example.vladimir.remindme.MainActivity;
import com.example.vladimir.remindme.R;
import com.example.vladimir.remindme.adapter.RemindListAdapter;
import com.example.vladimir.remindme.models.Item;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.app.Activity.RESULT_OK;

/**
 * Created by vladimir on 19.02.2017.
 */

public class AbstractTabFragment extends Fragment  implements RemindOnClickListener{

    private String title;
    protected Context context;
    protected View view;

    protected Realm realmDb;
    protected List<Item> cardList;
    protected RemindListAdapter listAdapter;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public void onEditItemAction(String itemId) {
        Intent intent = new Intent(getActivity(), EditActivity.class);
        intent.putExtra("itemId", itemId);
        startActivityForResult(intent, 13);
    }

    @Override
    public void onDeleteItemAction(final String itemId) {
        realmDb.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item result = realm.where(Item.class).equalTo("id", itemId).findFirst();
                result.deleteFromRealm();

                if(cardList.contains(result)){
                    cardList.remove(result);
                    cardList.add(result);
                }

                listAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onShareItemAction(Item item) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Message fro RemindMe");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, item.getContent().toString());
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 13:
                if(resultCode == RESULT_OK){
                    listAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
