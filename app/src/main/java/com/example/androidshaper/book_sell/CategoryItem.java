package com.example.androidshaper.book_sell;

public class CategoryItem {

    String CategoryId;
    String CategoryName;

    public CategoryItem(String categoryId, String categoryName) {
        CategoryId = categoryId;
        CategoryName = categoryName;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }
}
