package ro.scoala.mytodolist.data.sync;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.lifecycle.LifecycleOwner;

import ro.scoala.mytodolist.domain.TodoListSourceMediator;

public class TodoListSyncConnection implements ServiceConnection {
    private final TodoListSourceMediator mediator;
    private final LifecycleOwner owner;
    private TodoListObserverService service;

    public TodoListSyncConnection(TodoListSourceMediator mediator, LifecycleOwner owner) {
        this.owner = owner;
        this.mediator = mediator;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        this.service = ((TodoListObserverService.LocalBinder) binder).getService();
        this.service.startSynchronizing(owner, mediator);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        this.service = null;
    }
}
