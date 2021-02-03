package ro.scoala.mytodolist.presentation.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ro.scoala.mytodolist.domain.AddItemUseCase;
import ro.scoala.mytodolist.domain.LoadItemsUseCase;
import ro.scoala.mytodolist.domain.RemoveItemUseCase;
import ro.scoala.mytodolist.domain.UpdateItemDoneStateUseCase;

@SuppressWarnings("unchecked")
public class ListViewModelFactory implements ViewModelProvider.Factory {
    private final LoadItemsUseCase loadItemsUseCase;
    private final AddItemUseCase addItemUseCase;
    private final RemoveItemUseCase removeItemUseCase;
    private final UpdateItemDoneStateUseCase updateItemDoneStateUseCase;

    public ListViewModelFactory(LoadItemsUseCase loadItemsUseCase,
                                AddItemUseCase addItemUseCase,
                                RemoveItemUseCase removeItemUseCase,
                                UpdateItemDoneStateUseCase updateItemDoneStateUseCase) {
        this.loadItemsUseCase = loadItemsUseCase;
        this.addItemUseCase = addItemUseCase;
        this.removeItemUseCase = removeItemUseCase;
        this.updateItemDoneStateUseCase = updateItemDoneStateUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TodoListViewModel(
                loadItemsUseCase,
                addItemUseCase,
                removeItemUseCase,
                updateItemDoneStateUseCase);
    }
}