package com.aceros_duran.pos.infrastructure.adapters.output.persistence.entities;

import java.security.Timestamp;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "purchases")
public class PurchaseEntity {

    @Id
    @Column(length = 36)
    private String id; // UUID
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @Column(name = "purchase_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp purchaseDate;
    
    private Double total;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_type_id", nullable = false)
    private PurchaseTypeCatalogEntity purchaseType;
    
    @Column(name = "includes_VAT")
    private Boolean includesVAT;
    
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PurchaseDetailEntity> purchaseDetails;
}
