package ru.eviilcass.util.restaurant;

import ru.eviilcass.model.Restaurant;
import ru.eviilcass.to.DishTo;
import ru.eviilcass.to.RestaurantTo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static RestaurantTo getWithFilteredDishes(Restaurant rest, LocalDate date) {
        RestaurantTo restTo = new RestaurantTo(rest.getId(), rest.getName());
        restTo.setDishes(rest.getDishes()
                .stream()
                .filter(dish -> dish.getDate().equals(date))
                .map(dish -> new DishTo(dish.getId(), dish.getName(), dish.getPrice()))
                .collect(Collectors.toList())
        );
        return restTo;
    }

    public static RestaurantTo getWithFilteredDishesAndCountOfVotes(Restaurant rest, LocalDate date) {
        RestaurantTo restTo = new RestaurantTo(rest.getId(), rest.getName());
        restTo.setDishes(rest.getDishes()
                .stream()
                .filter(dish -> dish.getDate().equals(date))
                .map(dish -> new DishTo(dish.getId(), dish.getName(), dish.getPrice()))
                .collect(Collectors.toList())
        );
        restTo.setCountOfVotes((int) rest.getVotes()
                .stream()
                .filter(vote -> vote.getDate().equals(date))
                .count()
        );
        return restTo;
    }

    public static RestaurantTo getWithFilteredCountOfVotes(Restaurant rest, LocalDate date) {
        RestaurantTo restTo = new RestaurantTo(rest.getId(), rest.getName());
        restTo.setCountOfVotes((int) rest.getVotes()
                .stream()
                .filter(vote -> vote.getDate().equals(date))
                .count()
        );
        return restTo;
    }

    public static List<RestaurantTo> getAllWithFilteredCountOfVotes(List<Restaurant> list, LocalDate date) {
        return list.stream()
                .map(rest -> {
                    RestaurantTo restTo = new RestaurantTo(rest.getId(), rest.getName());
                    restTo.setCountOfVotes((int) rest.getVotes()
                            .stream()
                            .filter(vote -> vote.getDate().equals(date))
                            .count()
                    );
                    return restTo;
                })
                .sorted(Comparator.comparingInt(RestaurantTo::getCountOfVotes).reversed())
                .collect(Collectors.toList());
    }

    public static List<RestaurantTo> getAllWithFilteredCountOfVotesForAllTime(List<Restaurant> list) {
        return list.stream()
                .map(rest -> {
                    RestaurantTo restTo = new RestaurantTo(rest.getId(), rest.getName());
                    restTo.setCountOfVotes(rest.getVotes().size());
                    return restTo;
                })
                .collect(Collectors.toList());
    }

    public static List<RestaurantTo> getAllToWithDishes(List<Restaurant> list, LocalDate date) {
        return list.stream()
                .map(rest -> getWithFilteredDishes(rest, date))
                .collect(Collectors.toList());
    }
}
