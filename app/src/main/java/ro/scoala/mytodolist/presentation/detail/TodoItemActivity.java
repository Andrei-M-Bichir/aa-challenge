package ro.scoala.mytodolist.presentation.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import ro.scoala.mytodolist.R;
import ro.scoala.mytodolist.databinding.ActivityListItemBinding;
import ro.scoala.mytodolist.di.TodoListDependencyProvider;
import ro.scoala.mytodolist.presentation.list.TodoListItemViewModel;

public class TodoItemActivity extends AppCompatActivity {
    public static final String ITEM_ID_TAG = "item_id";
    private TodoItemViewModel viewModel;
    private MenuItem undoneItem;
    private MenuItem doneItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityListItemBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list_item);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(ITEM_ID_TAG)) {
            finish();
        } else {
            int id = intent.getIntExtra(ITEM_ID_TAG, 0);

            TodoListDependencyProvider dependencyProvider = TodoListDependencyProvider.get(getApplicationContext());
            viewModel = new ViewModelProvider(this,
                    new ItemViewModelFactory(
                            dependencyProvider.provideGetItemByIdUseCase(),
                            dependencyProvider.provideRemoveItemUseCase(),
                            dependencyProvider.provideUpdateItemUseCase(),
                            id
                    )
            ).get(TodoItemViewModel.class);

            getLifecycle().addObserver(viewModel);
            binding.setModel(viewModel);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        undoneItem = menu.findItem(R.id.mark_undone);
        doneItem = menu.findItem(R.id.mark_done);
        updateDoneMenuVisibility();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.delete) {
            viewModel.onItemRemoveTapped();
            finish();
            return true;
        } else if (itemId == R.id.add_alarm) {
            viewModel.onItemNotifyTapped();
            return true;
        } else if (itemId == R.id.mark_done) {
            viewModel.onMarkDoneTapped();
            updateDoneMenuVisibility();
            return true;
        } else if (itemId == R.id.mark_undone) {
            viewModel.onMarkUndoneTapped();
            updateDoneMenuVisibility();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void updateDoneMenuVisibility() {
        TodoListItemViewModel viewModel = this.viewModel.model.get();
        if (viewModel != null && doneItem != null && undoneItem != null) {
            boolean isDone = viewModel.isChecked.get();
            doneItem.setVisible(!isDone);
            undoneItem.setVisible(isDone);
        }
    }

}