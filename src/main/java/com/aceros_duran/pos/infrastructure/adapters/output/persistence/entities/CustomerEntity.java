package com.aceros_duran.pos.infrastructure.adapters.output.persistence.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @Column(name = "id", columnDefinition = "char(36)", nullable = false, unique = true)
    private String id; // UUID
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String phone;
    private String address;
    private String associatedCompany;
    
    @Column(name = "registration_date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate registrationDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CustomerCategoryEntity category;
    
    private Long points;
    private LocalDate inactivityDate;
}
