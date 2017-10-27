package com.han.board;

import com.han.board.persistence.MemberRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class MemberTests {

    @Autowired
    MemberRepository repo;

    @Autowired
    PasswordEncoder pwEncoder;

    @Test
    public void testUpdateOldMember() {
        List<String> ids = new ArrayList<>();

        for (int i = 0; i <=100; i++) {
            ids.add("user" + i);
        }

        repo.findAllById(ids).forEach(member -> {
            member.setUpw(pwEncoder.encode(member.getUpw()));
            repo.save(member);
        });
    }
}
