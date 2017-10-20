package com.han.board.persistence;

import com.han.board.domain.WebBoard;
import com.han.board.domain.WebReply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WebReplayRepository extends CrudRepository<WebReply, Long> {

    @Query("select r from WebReply r where r.board = ?1 " +
            " and r.rno > 0 order by r.rno asc ")
    public List<WebReply> getRepliesOfBoard(WebBoard board);
}
