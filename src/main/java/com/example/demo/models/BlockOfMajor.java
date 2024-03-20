package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "BlockOfMajor")
@JsonIgnoreProperties(value = { "creditDetails" })
public class BlockOfMajor {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String majorBlockCode;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Timestamp createdAt;

    @ToString.Exclude
    @OneToMany(mappedBy = "majorBlock")
    @JsonManagedReference
    private Set<CreditDetail> creditDetails;

    @ToString.Exclude
    @OneToMany(mappedBy = "blockOfMajor")
    @JsonManagedReference
    private Set<Major> majors;
}
