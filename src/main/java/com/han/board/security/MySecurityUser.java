package com.han.board.security;

import com.han.board.domain.Member;
import com.han.board.domain.MemberRole;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@Log
public class MySecurityUser extends User {

    private static final String ROLE_PREFIX = "ROLE_";

    private Member member;

    public MySecurityUser(Member member) {

        super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getRoles()));

        log.info("member* : " + member);


        this.member = member;
    }

    private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles) {

        List<GrantedAuthority> list = new ArrayList<>();

        roles.forEach(role-> list.add(
                new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())
        ));

        return list;
    }
}
