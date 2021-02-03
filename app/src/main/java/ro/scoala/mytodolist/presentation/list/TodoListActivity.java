package ro.scoala.mytodolist.presentation.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import ro.scoala.mytodolist.R;
import ro.scoala.mytodolist.data.sync.TodoListSyncConnection;
import ro.scoala.mytodolist.data.sync.TodoListObserverService;
import ro.scoala.mytodolist.databinding.ActivityListBinding;
import ro.scoala.mytodolist.di.TodoListDependencyProvider;
import ro.scoala.mytodolist.presentation.detail.TodoItemActivity;

public class TodoListActivity extends AppCompatActivity {
    private TodoListSyncConnection connection;
    private TodoListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = new TodoListSyncConnection(
                TodoListDependencyProvider.get(getApplicationContext()).provideMediator(),
                this
        );

        TodoListDependencyProvider dependencyProvider = TodoListDependencyProvider.get(getApplicationContext());
        viewModel = new ViewModelProvider(this,
                new ListViewModelFactory(
                        dependencyProvider.provideLoadItemsUseCase(),
                        dependencyProvider.provideAddItemUseCase(),
                        dependencyProvider.provideRemoveItemUseCase(),
                        dependencyProvider.provideUpdateItemDoneStateUseCase())
        ).get(TodoListViewModel.class);

        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.setLifecycleOwner(this);
        getLifecycle().addObserver(viewModel);
        binding.setViewModel(viewModel);
        viewModel.itemTappedLiveData.observe(this, itemId -> {
            Intent intent = new Intent(TodoListActivity.this, TodoItemActivity.class);
            intent.putExtra(TodoItemActivity.ITEM_ID_TAG, itemId);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.advanced_add) {
            return true;
        } else if (itemId == R.id.clear_all) {
            viewModel.onClearAllCheckedTapped();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TodoListObserverService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }
}