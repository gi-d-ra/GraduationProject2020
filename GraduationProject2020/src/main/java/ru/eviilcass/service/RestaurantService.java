package ru.eviilcass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.eviilcass.model.Dish;
import ru.eviilcass.model.Restaurant;
import ru.eviilcass.repository.restaurant.DataJpaRestaurantRepository;
import ru.eviilcass.repository.user.DataJpaUserRepository;
import ru.eviilcass.repository.vote.DataJpaVoteRepository;
import ru.eviilcass.to.RestaurantTo;
import ru.eviilcass.util.exception.ScoreAccessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.eviilcass.util.ValidationUtil.checkNotFoundWithId;
import static ru.eviilcass.util.restaurant.RestaurantUtil.*;

@Service
public class RestaurantService {

    @Autowired
    DataJpaUserRepository userRepo;

    @Autowired
    DataJpaRestaurantRepository restaurantRepo;

    @Autowired
    DataJpaVoteRepository voteRepo;


    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepo.get(id), id);
    }

    public Dish getDish(int id) {
        return checkNotFoundWithId(restaurantRepo.getDish(id), id);
    }

    public Restaurant getWithMenu(int id) {
        return checkNotFoundWithId(restaurantRepo.getWithMenu(id), id);
    }

    public RestaurantTo getWithMenuInDate(int id, LocalDate date) {
        Restaurant r = restaurantRepo.getWithMenu(id);
        checkNotFoundWithId(r, id);
        return getWithFilteredDishes(r, date);
    }

    public RestaurantTo getWithMenuAndVotesInDate(int id, LocalDate date) {
        Restaurant rest = restaurantRepo.getWithMenuAndVotes(id);
        checkNotFoundWithId(rest, id);
        return getWithFilteredDishesAndCountOfVotes(rest, date);
    }

    public List<RestaurantTo> getScoreForUser(int userId) throws ScoreAccessException {
        LocalDateTime now = LocalDateTime.now();
        if (voteRepo.getInDateByUser(userId, now.toLocalDate()) != null) {
            if (now.toLocalTime().isAfter(LocalTime.of(11, 0))) {
                return getAllWithVotes(now.toLocalDate());
            } else
                throw new ScoreAccessException("Results of voting are available after 11 a.m. Now is " + now.toLocalTime());
        } else throw new ScoreAccessException("Results are available only for voted users");
    }

    public RestaurantTo getWithVotes(int id, LocalDate date) {
        Restaurant rest = restaurantRepo.getWithVotes(id);
        checkNotFoundWithId(rest, id);
        return getWithFilteredCountOfVotes(rest, date);
    }

    public List<RestaurantTo> getAllWithVotes(LocalDate date) {
        List<Restaurant> list = restaurantRepo.getAllWithVotes();
        return getAllWithFilteredCountOfVotes(list, date);
    }

    public void update(Restaurant rest) {
        Assert.notNull(rest, "Restaurant must not be null");
        restaurantRepo.save(rest);
    }

    public Restaurant create(Restaurant rest) {
        Assert.notNull(rest, "Restaurant must not be null");
        return restaurantRepo.save(rest);
    }

    public List<Restaurant> getAll() {
        return restaurantRepo.getAll();
    }

    public List<RestaurantTo> getAllWithDishes() {
        return getAllToWithDishes(restaurantRepo.getAllWithDishes(), LocalDate.now());
    }

    public List<RestaurantTo> getAllToWithCountOfVotes() {
        return getAllWithFilteredCountOfVotesForAllTime(restaurantRepo.getAllWithVotes());
    }

    public void updateDish(Dish dish, int restId) {
        Assert.notNull(dish, "Dish must not bee null");
        checkNotFoundWithId(restaurantRepo.saveDish(dish, restId), restId);
    }

    public Dish createDish(Dish dish, int restId) {
        Assert.notNull(dish, "Dish must not bee null");
        return checkNotFoundWithId(restaurantRepo.saveDish(dish, restId), restId);
    }

    public void deleteDish(int dishId) {
        checkNotFoundWithId(restaurantRepo.deleteDish(dishId), dishId);
    }

    public void delete(int id) {
        checkNotFoundWithId(restaurantRepo.delete(id), id);
    }
}
