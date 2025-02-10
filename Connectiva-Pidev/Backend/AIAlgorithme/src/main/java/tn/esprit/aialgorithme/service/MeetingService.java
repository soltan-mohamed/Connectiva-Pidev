package tn.esprit.aialgorithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.aialgorithme.model.Meeting;
import tn.esprit.aialgorithme.repository.MeetingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository; // Add "final"
    private final AIServiceClient aiServiceClient; // Add "final"
}

