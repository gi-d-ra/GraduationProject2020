package ru.eviilcass.service;

import org.springframework.stereotype.Service;
import ru.eviilcass.model.Vote;
import ru.eviilcass.repository.restaurant.DataJpaRestaurantRepository;
import ru.eviilcass.repository.user.DataJpaUserRepository;
import ru.eviilcass.repository.vote.DataJpaVoteRepository;
import ru.eviilcass.to.RestaurantTo;
import ru.eviilcass.to.VoteTo;
import ru.eviilcass.util.exception.VoteException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.eviilcass.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    final DataJpaRestaurantRepository restRepo;

    final DataJpaUserRepository userRepo;

    final DataJpaVoteRepository voteRepo;

    final RestaurantService restService;

    public VoteService(DataJpaRestaurantRepository restRepo, DataJpaUserRepository userRepo, DataJpaVoteRepository voteRepo, RestaurantService restService) {
        this.restRepo = restRepo;
        this.userRepo = userRepo;
        this.voteRepo = voteRepo;
        this.restService = restService;
    }

    public Vote get(int id) {
        return checkNotFoundWithId(voteRepo.get(id), id);
    }

    public Vote updateVote(int voteId, int userId, int restId) throws VoteException {
        Vote vote = voteRepo.get(voteId);
        LocalDate dateOfVote = vote.getDate();
        LocalDateTime now = LocalDateTime.now();
        if (
                vote.getVoter().getId() == userId &
                        now.toLocalDate().equals(dateOfVote) &
                        now.toLocalTime().isBefore(LocalTime.of(11, 0))
        ) {
            return checkNotFoundWithId(voteRepo.update(vote, restId), vote.getId());
        } else throw new VoteException("Now is " + now.toLocalTime() + ", it is too late to change your mind");
    }

    public Vote createVote(int userId, int restId) throws VoteException {
        LocalDateTime now = LocalDateTime.now();
        Vote v = new Vote();
        Vote v2 = voteRepo.getInDateByUser(userId, now.toLocalDate());
        if(v2!=null) throw new VoteException("User has already voted today");

        v.setDate(now.toLocalDate());
        return voteRepo.save(v, userId, restId);
    }

    public List<VoteTo> getVotesForUser(int userId) {
        List<Vote> list = voteRepo.getVotesByUser(userId);
        return list
                .stream()
                .map(vote -> {
                    RestaurantTo restTo = restService.getWithMenuInDate(vote.getElected().getId(), vote.getDate());
                    return new VoteTo(vote.getId(),restTo, vote.getDate());
                })
                .sorted((v1, v2) -> {
                    if (v1.getDate().isEqual(v2.getDate())) return 0;
                    if (v1.getDate().isBefore(v2.getDate())) return 1;
                    else return -1;
                })
                .collect(Collectors.toList());
    }

}

