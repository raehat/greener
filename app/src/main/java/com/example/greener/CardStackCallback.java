package com.example.greener;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class CardStackCallback extends DiffUtil.Callback{
    private List<DataModel> old, baru;

    public CardStackCallback(List<DataModel> old, List<DataModel> baru) {
        this.old = old;
        this.baru = baru;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        //return old.get(oldItemPosition).getImage() == baru.get(newItemPosition).getImage();
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}

