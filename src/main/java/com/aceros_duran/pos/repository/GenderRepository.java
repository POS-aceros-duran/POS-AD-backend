package com.aceros_duran.pos.repository;

import com.aceros_duran.pos.model.GenderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<GenderModel,Long> {
}
