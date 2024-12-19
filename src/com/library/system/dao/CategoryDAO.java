package com.library.system.dao;

import com.library.system.model.Category;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {
    int getOrCreateCategory(String categoryName, Connection connection) throws SQLException;

    List<Category> getAllCategories(Connection connection) throws SQLException;
}
