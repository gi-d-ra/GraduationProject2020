package ru.eviilcass.repository.datajpa.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.eviilcass.model.User;

import java.util.List;

@Repository
public class DataJpaUserRepository {

    @Autowired
    private CrudUserRepository crudRepository;

    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    public User save(User user) {
        return crudRepository.save(user);
    }

    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }
}
