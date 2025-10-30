import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public class MenuBuilder {
    private final ArrayList<String> items = new ArrayList<>();
    private final ArrayList<Runnable> callbacks = new ArrayList<>();
    private final ArrayList<BooleanSupplier> conditions = new ArrayList<>();
    private String header = "Please select an option.";
    private boolean singleShot = false;

    public MenuBuilder(String header, String exitItem) {
        this.header = header;
        items.add(exitItem);
    }

    public MenuBuilder(String exitItem) {
        items.add(exitItem);
    }

    public MenuBuilder() {
        items.add("Exit");
    }

    public MenuBuilder setHeader(String header) {
        this.header = header;
        return this;
    }

    public MenuBuilder setSingleShot(boolean singleShot) {
        this.singleShot = singleShot;
        return this;
    }

    public MenuBuilder addItem(String item, Runnable callback) {
        return addItem(item, callback, () -> true);
    }

    public MenuBuilder addItem(String item, Runnable callback, BooleanSupplier showCondition) {
        items.add(item);
        callbacks.add(callback);
        conditions.add(showCondition);
        return this;
    }

    private String[] itemSupplier() {
        ArrayList<String> filteredItems = new ArrayList<>();
        for (int i=0; i < items.size(); i++) {
            if(i<1 || conditions.get(i-1).getAsBoolean()) {
                filteredItems.add(items.get(i));
            }
        }
        return filteredItems.toArray(new String[0]);
    }

    private Runnable[] actionSupplier() {
        ArrayList<Runnable> filteredCallbacks = new ArrayList<>();
        for (int i=0; i < callbacks.size(); i++) {
            if(conditions.get(i).getAsBoolean()) {
                filteredCallbacks.add(callbacks.get(i));
            }
        }
        return filteredCallbacks.toArray(new Runnable[0]);
    }

    public void runMenu() {
        TextMenu.menuLoop(() -> header, this::itemSupplier, this::actionSupplier, singleShot);
    }

}
