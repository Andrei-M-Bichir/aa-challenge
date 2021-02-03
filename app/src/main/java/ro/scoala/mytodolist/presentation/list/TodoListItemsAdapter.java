package ro.scoala.mytodolist.presentation.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.List;

import ro.scoala.mytodolist.R;
import ro.scoala.mytodolist.databinding.ListItemBinding;

public class TodoListItemsAdapter extends Adapter<TodoListItemsAdapter.TodoListItemViewHolder> {

    private final List<TodoListItemViewModel> items;
    private TodoListItemTapListener listItemTapListener;

    public TodoListItemsAdapter() {
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public TodoListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_item, parent, false);

        return new TodoListItemViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListItemViewHolder holder, int position) {
        TodoListItemViewModel item = items.get(position);
        holder.bind(item, listItemTapListener);
    }

    public void updateItems(List<TodoListItemViewModel> items, TodoListItemTapListener listItemTapListener) {
        this.listItemTapListener = listItemTapListener;
        this.items.clear();
        this.items.addAll(items);
    }

    static class TodoListItemViewHolder extends RecyclerView.ViewHolder {
        private final ListItemBinding binding;

        public TodoListItemViewHolder(@NonNull ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TodoListItemViewModel model, TodoListItemTapListener listItemTapListener) {
            binding.setModel(model);
            binding.setListener(listItemTapListener);
        }
    }
}
