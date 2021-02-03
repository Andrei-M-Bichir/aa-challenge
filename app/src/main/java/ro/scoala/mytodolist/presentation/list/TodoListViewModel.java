package ro.scoala.mytodolist.presentation.list;

import android.text.TextUtils;

import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ro.scoala.mytodolist.domain.AddItemUseCase;
import ro.scoala.mytodolist.domain.LoadItemsUseCase;
import ro.scoala.mytodolist.domain.RemoveItemUseCase;
import ro.scoala.mytodolist.domain.TodoListItem;
import ro.scoala.mytodolist.domain.UpdateItemDoneStateUseCase;

public class TodoListViewModel extends ViewModel implements TodoListItemTapListener, LifecycleObserver {
    private final LoadItemsUseCase loadItemsUseCase;
    private final AddItemUseCase addItemUseCase;
    private final RemoveItemUseCase removeItemUseCase;
    private final UpdateItemDoneStateUseCase updateItemDoneStateUseCase;
    public LiveData<List<TodoListItemViewModel>> items;
    public ObservableField<String> newName = new ObservableField<>();
    public MutableLiveData<Integer> itemTappedLiveData = new MutableLiveData<>();

    public TodoListViewModel(LoadItemsUseCase loadItemsUseCase,
                             AddItemUseCase addItemUseCase,
                             RemoveItemUseCase removeItemUseCase,
                             UpdateItemDoneStateUseCase updateItemDoneStateUseCase) {
        this.loadItemsUseCase = loadItemsUseCase;
        this.addItemUseCase = addItemUseCase;
        this.removeItemUseCase = removeItemUseCase;
        this.updateItemDoneStateUseCase = updateItemDoneStateUseCase;
    }

    public void onCreated() {
        items = Transformations.map(loadItemsUseCase.getItems(), input -> {
            List<TodoListItemViewModel> data = new ArrayList<>();
            for (TodoListItem item : input) {
                TodoListItemViewModel viewModel = new TodoListItemViewModel(item.getId());
                viewModel.alertAtTimestampMillis.set(item.getAlertAtMillis());
                viewModel.isChecked.set(item.isDone());
                data.add(viewModel);
            }
            return data;
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        List<TodoListItemViewModel> listItemViewModels = items.getValue();
        if (listItemViewModels != null) {
            for (TodoListItemViewModel model : listItemViewModels) {
                updateItemDoneStateUseCase.execute(model.id, model.isChecked.get());
            }
        }
    }

    @Override
    public void onItemTap(TodoListItemViewModel item) {
        itemTappedLiveData.setValue(item.id);
    }

    public void onAddItem() {
        String name = newName.get();
        if (!TextUtils.isEmpty(name)) {
            addItemUseCase.addItem(name, 0);
            newName.set("");
        }
    }

    public void onClearAllCheckedTapped() {
        List<TodoListItemViewModel> value = items.getValue();
        if (value != null) {
            for (TodoListItemViewModel item : value) {
                if (item.isChecked.get()) {
                    removeItemUseCase.removeItemById(item.id);
                }
            }
        }
    }
}
