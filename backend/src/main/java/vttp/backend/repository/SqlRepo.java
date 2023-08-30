package vttp.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.backend.model.Posting;

@Repository
public class SqlRepo {

    @Autowired
    private JdbcTemplate jTemplate;

    public static final String SQL_INSERT_POSTING = "insert into postings values (?, ?, ?, ?, ?, ?, ?, ?)";

    public boolean confirmPost(Posting posting) {
        return jTemplate.update(SQL_INSERT_POSTING,
                posting.getPostingId(),
                posting.getPostingDate(),
                posting.getName(),
                posting.getEmail(),
                posting.getPhone(),
                posting.getTitle(),
                posting.getDescription(),
                posting.getImage()) > 0 ? true : false;
    }
}
