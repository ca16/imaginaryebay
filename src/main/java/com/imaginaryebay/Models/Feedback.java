package com.imaginaryebay.Models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Brian on 8/2/2016.
 */
@Entity
public class Feedback {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name="item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = false)
    private String content;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", item=" + item +
                ", timestamp=" + timestamp +
                ", content='" + content + '\'' +
                '}';
    }
}
