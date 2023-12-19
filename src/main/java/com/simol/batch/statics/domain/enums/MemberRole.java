package com.simol.batch.statics.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberRole {
    MEMBER("MEMBER", "MEMBER"),
    SELLER("SELLER", "MEMBER, SELLER"),
    ADMIN("ADMIN", "MEMBER, SELLER, ADMIN"),
    ;
    public final String roleName;
    public final String roleList;
}
