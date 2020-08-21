package ru.eviilcass.to;

import org.springframework.format.annotation.DateTimeFormat;
import ru.eviilcass.util.DateTimeUtil;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Objects;

public class DishTo {
    private Integer id;

    @NotBlank
    private String name;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    @NotBlank
    private LocalDate date;

    @NotBlank
    private int price;

    public DishTo(Integer id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public DishTo(Integer id, String name, LocalDate date, int price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
    }

    public DishTo(String name, LocalDate date, int price) {
        this.name = name;
        this.date = date;
        this.price = price;
    }

    public DishTo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishTo dishTo = (DishTo) o;
        return price == dishTo.price &&
                Objects.equals(id, dishTo.id) &&
                Objects.equals(name, dishTo.name) &&
                Objects.equals(date, dishTo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, price);
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
