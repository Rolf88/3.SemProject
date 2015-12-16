package models;

public class HighscoreModel<T> {

    private final T model;

    private final int numbers;

    public HighscoreModel(T model, int numbers) {
        this.model = model;
        this.numbers = numbers;
    }

    public T getModel() {
        return model;
    }

    public int getNumbers() {
        return numbers;
    }

}
