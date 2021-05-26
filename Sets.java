package com.example.spy;

public class Sets {
    private String[] cities;
    private String[] places;
    private String[] animals;
    private String[] eat;
    private String[] clothe;
    private String[] myWords;

    public Sets() {

        this.cities = new String[]{"Вашингтон", "Москва", "Екатеринбург", "Лондон", "Нью-Йорк", "Самара", "Казань", "Санкт-Петербург"};
        this.places = new String[]{"Плащадка", "Школа", "Кофейня", "Подвал", "Сад", "Пляж", "Луна", "Пешера", "Остров", "Озеро", "Баня"};
        this.animals = new String[]{"Зоопарк", "Лес", "Пустыня", "Джунгли", "Океан", "Море", "Нора", "Скалы"};
        this.eat = new String[]{"Кофейня", "Ресторан", "Бургерная", "Кафе", "Каша", "Суп", "Пицца", "Салат"};
        this.clothe = new String[]{"Пальто", "Кардиган", "Шорты", "Футболка", "Рубашка", "Брьки", "Костюм", "Шарф", "Шапка"};
        this.myWords = new String[]{};
    }

    public String[] getCities() {
        return cities;
    }

    public String[] getPlaces() {
        return places;
    }

    public String[] getAnimals() {
        return animals;
    }

    public String[] getEat() {
        return eat;
    }

    public String[] getClothe() {
        return clothe;
    }

    public String[] getMyWords() {
        return myWords;
    }
}
