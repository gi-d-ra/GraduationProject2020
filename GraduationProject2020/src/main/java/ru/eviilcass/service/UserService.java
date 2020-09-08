package ru.eviilcass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.eviilcass.AuthorizedUser;
import ru.eviilcass.model.User;
import ru.eviilcass.repository.user.DataJpaUserRepository;

import java.util.List;

import static ru.eviilcass.util.ValidationUtil.checkNotFound;
import static ru.eviilcass.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService implements UserDetailsService {

    private final DataJpaUserRepository userRepo;

    @Autowired
    public UserService(DataJpaUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepo.save(user);
    }

    public User get(int id) {
        return checkNotFoundWithId(userRepo.get(id), id);
    }

    public List<User> getAll() {
        return userRepo.getAll();
    }

    public void update(User user) {
        Assert.notNull(user, "User must not be null");
        checkNotFoundWithId(userRepo.save(user), user.getId());
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "Email must not be null");
        return checkNotFound(userRepo.getByEmail(email), "Email=" + email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }


}
