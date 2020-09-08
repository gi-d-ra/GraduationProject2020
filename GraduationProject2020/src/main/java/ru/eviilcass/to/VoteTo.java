package ru.eviilcass.to;

import ru.eviilcass.model.Vote;

import java.time.LocalDate;
import java.util.Objects;

public class VoteTo {
    private Integer id;

    private RestaurantTo elected;

    private LocalDate date;

    public VoteTo(int id, RestaurantTo elected, LocalDate date) {
        this.elected = elected;
        this.date = date;
        this.id = id;
    }

    public RestaurantTo getElected() {
        return elected;
    }

    public void setElected(RestaurantTo elected) {
        this.elected = elected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        Vote that = (Vote) obj;
        return that.getDate().equals(date)
                && that.getElected().getName().equals(elected.getName())
                && Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, elected.getName());
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", restaurant=" + elected.getName() +
                ", date=" + date +
                '}';
    }
}
