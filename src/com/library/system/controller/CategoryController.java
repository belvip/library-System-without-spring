package com.library.system.controller;

import com.library.system.model.Category;
import com.library.system.service.CategoryService;

import java.util.List;
import java.util.Scanner;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void addOrGetCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(" Entrez le nom de la catégorie : ");
        String categoryName = scanner.nextLine();

        int category = categoryService.getOrCreateCategory(categoryName);
        System.out.println("Catégorie récupérée ou créée : " + category);
    }

    public void listAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        System.out.println("Liste des catégories : ");
        categories.forEach(System.out::println);
    }
}
