package com.library.system.service;

import com.library.system.model.Category;
import java.util.List;

public interface CategoryService {
    int getOrCreateCategory(String categoryName);

    // Déclaration de la méthode pour récupérer toutes les catégories
    List<Category> getAllCategories();
}
