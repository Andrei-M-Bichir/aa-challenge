package ro.scoala.mytodolist.data.sync;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import java.util.List;

import ro.scoala.mytodolist.domain.TodoListItem;
import ro.scoala.mytodolist.domain.TodoListSourceMediator;

public class TodoListObserverService extends Service {
    private final IBinder binder = new LocalBinder();

    public void startSynchronizing(LifecycleOwner owner, TodoListSourceMediator mediator) {
        LiveData<List<TodoListItem>> items = mediator.getItems();
        items.observe(owner, todoListItems -> {
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        TodoListObserverService getService() {
            return TodoListObserverService.this;
        }
    }
}

