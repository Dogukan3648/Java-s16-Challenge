package org.example.model;

import org.example.enums.MemberType;

import java.time.LocalDate;

public class MemberRecord {
    private long memberId;
    private MemberType type;
    private LocalDate dateOfMembership;
    private int noBooksIssued;
    private int maxBookLimit;
    private String name;
    private String address;
    private String phoneNo;

    public MemberRecord(long memberId,MemberType type, String name, String address,String phoneNo){
        this.memberId = memberId;
        this.type = type;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.dateOfMembership = LocalDate.now();
        this.noBooksIssued = 0;
        this.maxBookLimit = 5;
    }
    public long getMemberId(){
        return memberId;
    }
    public MemberType getType(){
        return type;
    }
    public LocalDate getDateOfMembership(){
        return dateOfMembership;
    }
    public int getNoBooksIssued(){
        return noBooksIssued;
    }
    public int getMaxBookLimit(){
        return maxBookLimit;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getAddress(){
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public void increaseBooksIssued(){
        if(noBooksIssued < maxBookLimit){
            noBooksIssued++;
        }
    }
    public void decreaseBooksIssued(){
        if (noBooksIssued > 0) {
            noBooksIssued--;
        }
    }
}
