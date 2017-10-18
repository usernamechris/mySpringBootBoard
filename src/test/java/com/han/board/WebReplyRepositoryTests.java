package com.han.board;

import com.han.board.domain.WebBoard;
import com.han.board.domain.WebReply;
import com.han.board.persistence.WebReplayRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class WebReplyRepositoryTests {

    @Autowired
    WebReplayRepository repo;

    @Test
    public void testInsertReplies() {
        Long[] arr = {300L, 299L, 298L};

        Arrays.stream(arr).forEach( num -> {
            WebBoard board = new WebBoard();
            board.setBno(num);

            IntStream.range(0, 10).forEach(i -> {
                WebReply reply = new WebReply();
                reply.setReplyText("REPLY..." + i);
                reply.setReplyer("replayer" + i);
                reply.setBoard(board);

                repo.save(reply);
            });
        });
    }
}
