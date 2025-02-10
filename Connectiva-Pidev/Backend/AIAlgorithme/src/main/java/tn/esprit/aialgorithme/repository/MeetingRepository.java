package tn.esprit.aialgorithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.aialgorithme.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
