package service;

import com.google.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;
import repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String password) {
        if (userRepository.userExists(username)) {
            System.out.println("Użytkownik już istnieje!");
            return;
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userRepository.saveUser(username, hashedPassword);
        System.out.println("Zarejestrowano pomyślnie.");
    }

    public boolean loginUser(String username, String password) {
        String storedHash = userRepository.getPasswordHash(username);
        if (storedHash != null && BCrypt.checkpw(password, storedHash)) {
            System.out.println("Zalogowano pomyślnie!");
            return true;
        } else {
            System.out.println("Nieprawidłowe dane logowania.");
            return false;
        }
    }
}
