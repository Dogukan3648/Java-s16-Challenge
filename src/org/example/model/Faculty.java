package org.example.model;

import org.example.enums.MemberType;

public class Faculty extends MemberRecord{
    public Faculty(long memberId, String name, String address, String phoneNo) {
        super(memberId, MemberType.FACULTY, name, address, phoneNo);
    }
}
