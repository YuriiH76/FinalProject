package com.epam.web.db;

import java.sql.ResultSet;

public interface EntityMapper<T> {
    T mapRow(ResultSet rs);
}
