package ro.scoala.mytodolist.presentation.detail;

import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import ro.scoala.mytodolist.domain.GetItemByIdUseCase;
import ro.scoala.mytodolist.domain.RemoveItemUseCase;
import ro.scoala.mytodolist.domain.TodoListItem;
import ro.scoala.mytodolist.domain.UpdateItemUseCase;
import ro.scoala.mytodolist.presentation.list.TodoListItemViewModel;

public class TodoItemViewModel extends ViewModel implements LifecycleObserver {
    private final GetItemByIdUseCase getItemByIdUseCase;
    private final RemoveItemUseCase removeItemUseCase;
    private final UpdateItemUseCase updateItemUseCase;
    private final int itemId;
    public ObservableField<TodoListItemViewModel> model;

    public TodoItemViewModel(GetItemByIdUseCase getItemByIdUseCase,
                             RemoveItemUseCase removeItemUseCase,
                             UpdateItemUseCase updateItemUseCase,
                             int itemId) {
        this.getItemByIdUseCase = getItemByIdUseCase;
        this.removeItemUseCase = removeItemUseCase;
        this.updateItemUseCase = updateItemUseCase;
        this.model = new ObservableField<>();
        this.itemId = itemId;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreated() {
        TodoListItem item = getItemByIdUseCase.get(itemId);
        TodoListItemViewModel viewModel = new TodoListItemViewModel(item.getId());
        viewModel.isChecked.set(item.isDone());
        viewModel.alertAtTimestampMillis.set(item.getAlertAtMillis());
        viewModel.name.set(item.getName());
        viewModel.details.set(item.getDetails());
        model.set(viewModel);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if(model!=null){
            TodoListItemViewModel vm = model.get();
            if (vm != null) {
                TodoListItem item = mapToDomain(vm);
                updateItemUseCase.updateItem(item);
            }
        }
    }

    public void onItemRemoveTapped() {
        removeItemUseCase.removeItemById(itemId);
    }

    public void onItemNotifyTapped() {

    }

    public void onMarkUndoneTapped() {
        model.get().isChecked.set(false);
        TodoListItemViewModel vm = model.get();
        if (vm != null) {
            TodoListItem item = mapToDomain(vm);
            updateItemUseCase.updateItem(item);
        }
    }

    public void onMarkDoneTapped() {
        model.get().isChecked.set(true);
        TodoListItemViewModel vm = model.get();
        if (vm != null) {
            TodoListItem item = mapToDomain(vm);
            updateItemUseCase.updateItem(item);
        }
    }

    private TodoListItem mapToDomain(TodoListItemViewModel vm) {
        TodoListItem item = new TodoListItem(vm.id);
        item.setDone(vm.isChecked.get());
        item.setAlertAtMillis(vm.alertAtTimestampMillis.get());
        item.setDetails(vm.details.get());
        item.setName(vm.name.get());
        return item;
    }
}
