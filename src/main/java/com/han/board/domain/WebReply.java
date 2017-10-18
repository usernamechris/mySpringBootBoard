package com.han.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "tbl_webreplies")
@EqualsAndHashCode(of = "rno")
@ToString
public class WebReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String replyText;

    private String replyer;

    @CreationTimestamp
    private Timestamp regdate;
    @UpdateTimestamp
    private Timestamp updatedate;

    @JsonIgnore // 양방향 참조시 무한 참조되는것 방지. json 객체 생성시 board 객체는 json화 하지 않는다
    @ManyToOne(fetch = FetchType.LAZY)
    private WebBoard board;
}
