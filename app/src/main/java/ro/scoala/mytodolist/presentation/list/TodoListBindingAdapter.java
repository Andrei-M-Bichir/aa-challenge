package ro.scoala.mytodolist.presentation.list;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoListBindingAdapter {

    @BindingAdapter(value = {"todoListItems", "tapListener"})
    public static void setItems(RecyclerView recyclerView,
                                LiveData<List<TodoListItemViewModel>> items,
                                TodoListItemTapListener tapListener) {
        RecyclerView.Adapter<?> adapter = recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new TodoListItemsAdapter();
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }
        if (items != null) {
            ((TodoListItemsAdapter) adapter).updateItems(items.getValue(), tapListener);
        }
    }

}
