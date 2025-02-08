package tn.esprit.aialgorithme.repository;

import tn.esprit.aialgorithme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
