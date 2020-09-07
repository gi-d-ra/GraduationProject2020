package ru.eviilcass.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.eviilcass.model.Dish;
import ru.eviilcass.model.Restaurant;
import ru.eviilcass.service.RestaurantService;
import ru.eviilcass.to.DishTo;
import ru.eviilcass.to.RestaurantTo;
import ru.eviilcass.util.DateTimeUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.eviilcass.util.ValidationUtil.assureIdConsistent;
import static ru.eviilcass.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public final static String REST_URL = "/rest/admin/restaurants";

    @Autowired
    private RestaurantService service;

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("Admin getAll");
        return service.getAllWithDishes();
    }

    @GetMapping("/history")
    public List<RestaurantTo> getAllHistory(@RequestParam(required = false) String date) {
        if (date == null) {
            log.info("Admin getAllHistory without date");
            return service.getAllToWithCountOfVotes();
        } else {
            log.info("Admin getAllHistory at date of:{}", date);
            return service.getAllWithVotes(DateTimeUtil.parseLocalDate(date));
        }
    }

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id, @RequestParam(required = false) String date) {
        if (date == null) {
            log.info("Admin get Restaurant with id: {} at date of: {}", id, LocalDate.now());
            return service.getWithMenuAndVotesInDate(id, LocalDate.now());
        } else {
            log.info("Admin get Restaurant with id: {} at date of: {}", id, date);
            return service.getWithMenuAndVotesInDate(id, DateTimeUtil.parseLocalDate(date));
        }
    }

    @GetMapping("/dishes/{id}")
    public Dish get(@PathVariable int id) {
        log.info("Admin get dish with id: {}", id);
        return service.getDish(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Admin delete Restaurant with id: {}", id);
        service.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam String restName, @PathVariable int id) {
        log.info("Admin update Restaurant with id: {}", id);
        service.update(new Restaurant(id, restName));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant rest) {
        Restaurant created = service.create(rest);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        log.info("Admin createWithLocation with id: {}", created.getId());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping("/{restId}/dishes")
    public ResponseEntity<Dish> createDish(@RequestBody DishTo dishTo, @PathVariable int restId) {
        Dish dish = new Dish(dishTo);
        checkNew(dish);
        Dish created = service.createDish(dish, restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/dishes/{id}")
                .buildAndExpand(created.getId()).toUri();
        log.info("Admin createDish with id: {}", created.getId());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{restId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDish(@PathVariable int restId, @PathVariable int id, @RequestBody Dish dish) {
        assureIdConsistent(dish, id);
        log.info("Admin updateDish with id: {}", dish.getId());
        service.updateDish(dish, restId);
    }

    @DeleteMapping("/{restId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int restId, @PathVariable int id) {
        log.info("Admin deleteDish with id: {}", id);
        service.deleteDish(id);
    }
}
