package com.example.vladimir.remindme.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.vladimir.remindme.EditActivity;
import com.example.vladimir.remindme.R;
import com.example.vladimir.remindme.models.Item;

import java.util.List;

/**
 * Created by vladimir on 21.02.2017.
 */

public class RemindListAdapter extends RecyclerView.Adapter<RemindListAdapter.RemindViewHolder> {

    private List<Item> listData;
    private Context mainContext;
    public RemindListAdapter(Context context, List<Item> listData) {
        this.listData = listData;
        this.mainContext = context;
    }

    @Override
    public RemindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remind_item, parent, false);
        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemindViewHolder holder, final int position) {
        holder.title.setText(listData.get(position).getTitle());
        holder.subMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.item_sub_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_edit:
                                Intent intent = new Intent(mainContext, EditActivity.class);
                                ((Activity) mainContext).startActivityForResult(intent, 13);
                                break;
                            case R.id.item_share:
                                break;
                            case R.id.item_delete:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class RemindViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView title;
        public ImageView subMenu;

        public RemindViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            subMenu = (ImageView) itemView.findViewById(R.id.subMenuItem);
        }
    }



}
