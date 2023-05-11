package com.cmpe202.HealthClubManagement.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import jakarta.persistence.*;
import javax.persistence.*;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "membershipTypeId")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int membershipTypeId;
    @OneToOne
    private HealthClub healthClub;

    private int duration;
    private int price;

    public Membership() {
    }

    public Membership(HealthClub healthClub, int duration, int price) {
        this.healthClub = healthClub;
        this.duration = duration;
        this.price = price;
    }

    public int getMembershipTypeId() {
        return membershipTypeId;
    }

    public void setMembershipTypeId(int membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
    }

    public HealthClub getHealthClub() {
        return healthClub;
    }

    public void setHealthClub(HealthClub healthClub) {
        this.healthClub = healthClub;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
