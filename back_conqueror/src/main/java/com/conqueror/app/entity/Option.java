package com.conqueror.app.entity;

import javax.persistence.*;

/**
 * @author Bogdan Kaftanatiy
 */
@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "data", unique = true, nullable = false)
    private String data;

    @Column(name = "category", nullable = false)
    private String category;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (!data.equals(option.data)) return false;
        return category.equals(option.category);
    }

    @Override
    public int hashCode() {
        int result = data.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
