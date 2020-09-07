package ru.eviilcass.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Dish> dishes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "elected")
    private Set<Vote> votes = new HashSet<>();

    public Restaurant(String rts) {
        name = rts;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public Restaurant() {

    }


    public Restaurant(Integer id, String name) {
        super(id, name);
    }

}
