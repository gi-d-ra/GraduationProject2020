package ru.eviilcass.repository.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.eviilcass.model.Dish;
import ru.eviilcass.model.Restaurant;
import ru.eviilcass.repository.vote.CrudVoteRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaRestaurantRepository {

    @Autowired
    CrudRestaurantRepository restaurantRepo;

    @Autowired
    CrudVoteRepository voteRepo;

    @Autowired
    CrudDishRepository dishRepo;

    public Dish getDish(int id) {
        return dishRepo.findById(id).orElse(null);
    }

    public Restaurant save(Restaurant r) {
        return restaurantRepo.save(r);
    }

    public Restaurant get(int id) {
        return restaurantRepo.findById(id).orElse(null);
    }

    public List<Restaurant> getAll() {
        return restaurantRepo.findAll();
    }

    public Dish saveDish(Dish dish, int restId) {
        if (!dish.isNew() && getDish(dish.getId()) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepo.getOne(restId));
        if (dish.getDate() == null) {
            dish.setDate(LocalDate.now());
        }
        return dishRepo.save(dish);
    }

    public boolean deleteDish(int dishId) {
        return dishRepo.delete(dishId) != 0;
    }

    public boolean delete(int id) {
        return restaurantRepo.delete(id) != 0;
    }

    public Restaurant getWithMenu(int id) {
        return restaurantRepo.getWithMenu(id);
    }

    public Restaurant getWithMenuAndVotes(int id) {
        return restaurantRepo.getWithMenuAndVotes(id);
    }

    public Restaurant getWithVotes(int id) {
        return restaurantRepo.getWithVotes(id);
    }

    public List<Restaurant> getAllWithVotes() {
        return restaurantRepo.getAllWithVotes();
    }

    public List<Restaurant> getAllWithDishes() {
        return restaurantRepo.getAllWithDishes();
    }
}
