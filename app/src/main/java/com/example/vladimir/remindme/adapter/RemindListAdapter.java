package com.example.vladimir.remindme.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.vladimir.remindme.Interfaces.RemindOnClickListener;
import com.example.vladimir.remindme.R;
import com.example.vladimir.remindme.models.Item;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class RemindListAdapter extends RealmRecyclerViewAdapter<Item, RemindListAdapter.RemindViewHolder> {

    private List<Item> listData;
    private Context mainContext;
    private RemindOnClickListener remindOnClickListener;


    public RemindListAdapter(Context context, List<Item> listData, RemindOnClickListener remindOnClickListener) {
        super((OrderedRealmCollection<Item>) listData, true);

        this.listData = listData;
        this.mainContext = context;
        this.remindOnClickListener = remindOnClickListener;
    }


    @Override
    public RemindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remind_item, parent, false);
        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemindViewHolder holder, final int position) {
        holder.title.setText(listData.get(position).getTitle());
        holder.content.setText(listData.get(position).getContent());
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
                                remindOnClickListener.onEditItemAction(listData.get(position).getId());
                                break;
                            case R.id.item_share:
                                remindOnClickListener.onShareItemAction(listData.get(position));
                                break;
                            case R.id.item_delete:
                                remindOnClickListener.onDeleteItemAction(listData.get(position).getId());
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    class RemindViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView title;
        TextView content;
        public ImageView subMenu;

        public RemindViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.itemContent);
            subMenu = (ImageView) itemView.findViewById(R.id.subMenuItem);
        }
    }
}
