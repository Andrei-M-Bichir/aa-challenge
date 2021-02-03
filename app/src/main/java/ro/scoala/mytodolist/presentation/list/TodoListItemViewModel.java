package ro.scoala.mytodolist.presentation.list;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableLong;

public class TodoListItemViewModel {
    public final int id;
    public final ObservableField<String> name;
    public final ObservableField<String> details;
    public final ObservableBoolean isChecked;
    public final ObservableLong alertAtTimestampMillis;

    public TodoListItemViewModel(int id) {
        this.id = id;
        name = new ObservableField<>();
        details = new ObservableField<>();
        isChecked = new ObservableBoolean();
        alertAtTimestampMillis = new ObservableLong();
    }
}
