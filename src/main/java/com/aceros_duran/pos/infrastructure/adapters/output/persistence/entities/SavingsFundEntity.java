package com.aceros_duran.pos.infrastructure.adapters.output.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "savings_fund")
public class SavingsFundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Se mantiene INT para esta tabla
    
    @Column(name = "user_id", length = 36, nullable = false)
    private String userId; // Referencia a User (UUID)
    
    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private LocalDate date;
    
    @Column(name = "contribution", nullable = false)
    private BigDecimal contribution;
    
    // bonus es una columna computada (generada always as)
    @Column(name = "bonus", nullable = false, insertable = false, updatable = false)
    private BigDecimal bonus;
}
