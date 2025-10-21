package com.enigmacamp.recomcinema.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_profiles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users extends BaseEntity{
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ElementCollection
    @CollectionTable(
            name = "user_hobbies",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "hobby")
    private List<String> hobbies;

    @Column(columnDefinition = "TEXT")
    private String personalities;

    private boolean isDeleted = false;

    @Column(columnDefinition = "text")
    private String recommendations;// e.g. "La La Land, Mad Max..."

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendations> recommendationEntities = new ArrayList<>();
}
