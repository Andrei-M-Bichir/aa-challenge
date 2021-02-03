package ro.scoala.mytodolist.data.source;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ro.scoala.mytodolist.data.TodoListItemDto;
import ro.scoala.mytodolist.domain.TodoListRepository;

public class DiskDataSource implements TodoListRepository {
    private static final String ENTRY_NAME_TAG = "items.json";
    private final Gson gson;
    private final SharedPreferences sharedPreferences;

    public DiskDataSource(Context context) {
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences(ENTRY_NAME_TAG, Context.MODE_PRIVATE);
    }

    @Override
    public List<TodoListItemDto> getItems() {
        List<TodoListItemDto> items = new ArrayList<>();
        Map<String, ?> jsons = sharedPreferences.getAll();
        for (Object json : jsons.values()) {
            TodoListItemDto value = gson.fromJson(((String) json), TodoListItemDto.class);
            items.add(value);
        }
        return items;
    }

    @Override
    public void addItem(TodoListItemDto item) {
        String json = gson.toJson(item);
        sharedPreferences.edit().putString(item.id + "", json).apply();
    }

    @Override
    public void updateItem(TodoListItemDto item) {
        String json = gson.toJson(item);
        sharedPreferences.edit().putString(item.id + "", json).apply();
    }

    @Override
    public void removeItemById(int id) {
        sharedPreferences.edit().remove(id + "").apply();
    }
}
