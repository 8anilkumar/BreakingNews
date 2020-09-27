package com.anilkumarnishad.breakingnews;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.anilkumarnishad.breakingnews.Adapters.TapnewsAdapter;

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    TapnewsAdapter mainAdapter;

    public SwipeHelper(TapnewsAdapter mainAdapter) {
        super( ItemTouchHelper.RIGHT | ItemTouchHelper.RIGHT,90);
        this.mainAdapter = mainAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mainAdapter.dismissItem(viewHolder.getAdapterPosition());

    }
}
