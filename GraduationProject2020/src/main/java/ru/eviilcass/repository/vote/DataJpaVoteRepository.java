package ru.eviilcass.repository.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.eviilcass.model.Vote;
import ru.eviilcass.repository.restaurant.CrudRestaurantRepository;
import ru.eviilcass.repository.user.CrudUserRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepository {
    @Autowired
    private CrudUserRepository userRepo;

    @Autowired
    private CrudRestaurantRepository restaurantRepo;

    @Autowired
    private CrudVoteRepository voteRepo;

    public Vote get(int id) {
        return voteRepo.findById(id).orElse(null);
    }

    public List<Vote> getInDate(LocalDate date) {
        return voteRepo.getInDate(date);
    }

    public boolean delete(int id) {
        return voteRepo.delete(id) != 0;
    }

    public Vote update(Vote vote, int restId) {
        if (vote.isNew()) {
            return null;
        }
        vote.setElected(restaurantRepo.getOne(restId));
        return voteRepo.save(vote);
    }

    public Vote save(Vote vote, int userId, int restId) {
        if (!vote.isNew()) {
            return null;
        }
        vote.setVoter(userRepo.getOne(userId));
        vote.setElected(restaurantRepo.getOne(restId));
        return voteRepo.save(vote);
    }

    public Vote getInDateByUser(int userId, LocalDate date) {
        return voteRepo.getInDateByUser(date, userId);
    }

    public List<Vote> getVotesByUser(int userId) {
        return voteRepo.getVotesByUser(userId);
    }
}
