package com.library.system.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface CategoryDAO {
    int getOrCreateCategory(String name, Connection connection) throws SQLException;
}

