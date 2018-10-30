package com.example.vladimir.remindme.Interfaces;

import android.view.View;

import com.example.vladimir.remindme.models.Item;

public interface RemindOnClickListener {
    void onEditItemAction(String itemId);
    void onDeleteItemAction(String itemId);
    void onShareItemAction(Item item);
}
