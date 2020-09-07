package ru.eviilcass.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.eviilcass.model.Vote;
import ru.eviilcass.service.RestaurantService;
import ru.eviilcass.service.VoteService;
import ru.eviilcass.to.RestaurantTo;
import ru.eviilcass.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

import static ru.eviilcass.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = UserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {
    public static final String REST_URL = "/rest/user/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService voteService;

    @Autowired
    private RestaurantService restService;

    @GetMapping()
    public List<RestaurantTo> getAll() {
        log.info("User getAll");
        return restService.getAllWithDishes();
    }

    @GetMapping("/score")
    public List<RestaurantTo> getScore() {
        log.info("User getScore");
        return restService.getScoreForUser(authUserId());
    }

    @GetMapping("/history")
    public List<VoteTo> getMyVotes() {
        log.info("User getMyVotes");
        return voteService.getVotesForUser(authUserId());
    }

    @GetMapping("/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id) {
        log.info("User getRestaurant with id: {}", id);
        return restService.getWithMenuInDate(id, LocalDate.now());
    }

    @PostMapping("/voter")
    public Vote createVote(@RequestParam int restId) {
        log.info("User createVote for Restaurant with id: {}", restId);
        return voteService.createVote(authUserId(), restId);
    }

    @PutMapping("/voter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@RequestParam int voteId, @RequestParam int restId) {
        log.info("User updateVote with id: {} for Restaurant with id: {} ", voteId, restId);
        voteService.updateVote(voteId, authUserId(), restId);
    }
}
