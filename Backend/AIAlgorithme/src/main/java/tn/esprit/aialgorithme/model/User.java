package tn.esprit.aialgorithme.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @ElementCollection
    private List<String> interests = new ArrayList<>();

    @Column(nullable = false)
    private String domain;

    // Domain categorization logic
    private static final Map<String, List<String>> DOMAIN_KEYWORDS = Map.of(
            "technology", List.of("ai", "machine learning", "deep learning", "data science"),
            "sports", List.of("sport", "football", "basketball", "training")
    );

    @PrePersist
    @PreUpdate
    private void determineDomain() {
        this.domain = detectDomain();
    }

    private String detectDomain() {
        Map<String, Integer> domainScores = new java.util.HashMap<>(Map.of(
                "technology", 0,
                "sports", 0
        ));

        for (String interest : this.interests) {
            String lowerInterest = interest.toLowerCase();
            DOMAIN_KEYWORDS.forEach((domain, keywords) -> {
                if (keywords.stream().anyMatch(lowerInterest::contains)) {
                    domainScores.put(domain, domainScores.get(domain) + 1);
                }
            });
        }

        return domainScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .orElse("other");
    }
}