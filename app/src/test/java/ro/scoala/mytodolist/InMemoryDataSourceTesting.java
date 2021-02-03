package ro.scoala.mytodolist;

import org.junit.Test;

import java.util.List;

import ro.scoala.mytodolist.data.TodoListItemDto;
import ro.scoala.mytodolist.data.source.InMemoryDataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InMemoryDataSourceTesting {

    @Test
    public void when_getItemsInvoked_returnsCorrectResult() {
        InMemoryDataSource source = new InMemoryDataSource();
        List<TodoListItemDto> items = source.getItems();

        assertEquals(2, items.size());

        TodoListItemDto item1 = items.get(0);
        TodoListItemDto item2 = items.get(1);

        assertEquals("5", item1.name);
        assertEquals("d", item2.name);
    }

    @Test
    public void when_getItemsInvoked_returnsCorrectStatus() {
        InMemoryDataSource source = new InMemoryDataSource();
        List<TodoListItemDto> items = source.getItems();

        TodoListItemDto item1 = items.get(0);
        TodoListItemDto item2 = items.get(1);

        assertTrue(item1.isDone);
        assertFalse(item2.isDone);
    }

    @Test
    public void when_getItemsInvoked_returnsCorrectDetails() {
        InMemoryDataSource source = new InMemoryDataSource();
        List<TodoListItemDto> items = source.getItems();

        TodoListItemDto item1 = items.get(0);
        TodoListItemDto item2 = items.get(1);

        assertEquals("", item1.details);
        assertEquals("nimic special aici", item2.details);
    }

}
