package com.devexperts.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long source_id;
    private long target_id;
    private  Double amount;
    private java.sql.Timestamp transfer_time;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }



    public Timestamp getTransfer_time() {
        return transfer_time;
    }



    public void setTransfer_time(Timestamp transfer_time) {
        this.transfer_time = transfer_time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSource_id() {
        return source_id;
    }

    public void setSource_id(long source_id) {
        this.source_id = source_id;
    }

    public long getTarget_id() {
        return target_id;
    }

    public void setTarget_id(long target_id) {
        this.target_id = target_id;
    }

    public Transfer(long source_id, long target_id, Double amount, Timestamp transfer_time) {
        this.source_id = source_id;
        this.target_id = target_id;
        this.amount = amount;
        this.transfer_time = transfer_time;
    }


    public Transfer() {
    }
}
