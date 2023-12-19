package com.simol.batch.statics.domain.entity;

import com.simol.batch.statics.domain.enums.MemberRole;
import com.simol.batch.statics.domain.enums.MemberStatus;
import com.simol.batch.statics.domain.enums.SnsJoinType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "members")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String birth;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String snsId;

    @Enumerated(EnumType.STRING)
    private SnsJoinType snsType;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
