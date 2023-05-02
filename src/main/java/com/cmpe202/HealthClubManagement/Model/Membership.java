package com.cmpe202.HealthClubManagement.Model;

import jakarta.persistence.*;

@Entity
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
