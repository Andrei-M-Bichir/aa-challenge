package ro.scoala.mytodolist.presentation.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ro.scoala.mytodolist.domain.GetItemByIdUseCase;
import ro.scoala.mytodolist.domain.RemoveItemUseCase;
import ro.scoala.mytodolist.domain.UpdateItemUseCase;

@SuppressWarnings("unchecked")
public class ItemViewModelFactory implements ViewModelProvider.Factory {
    private final GetItemByIdUseCase getItemByIdUseCase;
    private final RemoveItemUseCase removeItemUseCase;
    private final UpdateItemUseCase updateItemUseCase;
    private final int itemId;

    public ItemViewModelFactory(GetItemByIdUseCase getItemByIdUseCase,
                                RemoveItemUseCase removeItemUseCase,
                                UpdateItemUseCase updateItemUseCase,
                                int itemId) {
        this.itemId = itemId;
        this.getItemByIdUseCase = getItemByIdUseCase;
        this.removeItemUseCase = removeItemUseCase;
        this.updateItemUseCase = updateItemUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TodoItemViewModel(
                getItemByIdUseCase,
                removeItemUseCase,
                updateItemUseCase,
                itemId);
    }
}