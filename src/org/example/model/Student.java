package org.example.model;

import org.example.enums.MemberType;

public class Student extends MemberRecord{
    public Student(long memberId, String name, String address, String phoneNo) {
        super(memberId, MemberType.STUDENT, name, address, phoneNo);
    }
}
