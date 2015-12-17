package models;

public class HighscoreModel<T> {

    private final int position;

    private final T model;

    private final int amount;

    public HighscoreModel(int position, T model, int amount) {
        this.position = position;
        this.model = model;
        this.amount = amount;
    }

    public T getModel() {
        return model;
    }

    public int getAmount() {
        return amount;
    }

    public int getPosition() {
        return position;
    }
}
