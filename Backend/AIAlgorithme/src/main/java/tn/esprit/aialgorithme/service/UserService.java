package tn.esprit.aialgorithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.aialgorithme.model.User;
import tn.esprit.aialgorithme.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // Add "final"
    private final AIServiceClient aiServiceClient; // Add "final"

    public User createUser(User user) { // Ensure method signature matches
        return userRepository.save(user);
    }

    public List<User> findMatches(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> allUsers = userRepository.findAll();
        List<User> otherUsers = allUsers.stream()
                .filter(user -> !user.getId().equals(userId))
                .collect(Collectors.toList());

        // Debug: Check if otherUsers exist
        System.out.println("Other users count: " + otherUsers.size());

        List<User> matches = aiServiceClient.findMatches(currentUser, otherUsers);

        // Debug: Check AI service response
        System.out.println("Matches count: " + matches.size());

        return matches;
    }
}

