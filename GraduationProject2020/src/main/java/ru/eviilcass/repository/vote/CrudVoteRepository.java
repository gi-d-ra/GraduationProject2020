package ru.eviilcass.repository.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.eviilcass.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    Vote getById(int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT v FROM Vote v WHERE v.date=:date")
    List<Vote> getInDate(@Param("date") LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.date=:date AND v.voter.id=:userId")
    Vote getInDateByUser(@Param ("date") LocalDate date, @Param("userId") int id);

    @Query("SELECT v FROM Vote v WHERE v.voter.id=:userId")
    List<Vote> getVotesByUser(@Param("userId") int userId);
}
