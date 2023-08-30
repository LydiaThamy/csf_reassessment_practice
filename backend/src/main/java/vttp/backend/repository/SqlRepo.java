package vttp.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SqlRepo {
    
    @Autowired
    private JdbcTemplate jTemplate;

    
}
