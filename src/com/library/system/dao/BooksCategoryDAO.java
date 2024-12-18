package com.library.system.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface BooksCategoryDAO {

    void addBookCategoryRelation(int bookId, int categoryId, Connection connection) throws SQLException;

}
