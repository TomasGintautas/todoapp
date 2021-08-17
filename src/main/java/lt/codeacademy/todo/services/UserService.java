package lt.codeacademy.todo.services;

import lt.codeacademy.todo.entities.User;
import lt.codeacademy.todo.exceptions.FieldExistsException;
import lt.codeacademy.todo.exceptions.FieldNotFoundException;
import lt.codeacademy.todo.repositories.RoleRepository;
import lt.codeacademy.todo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", id.toString()));
    }

    public User createUser(User user) {
        if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new FieldExistsException("This username already exists: ", user.getUsername());
        }

        user.setRoles(Set.of(roleRepository.getRoleByName("USER").get()));
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (getUser(id) != null) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws FieldNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new FieldNotFoundException("User not found: ", username));

    }
}
