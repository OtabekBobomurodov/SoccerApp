package com.example.soccerapp.respository;

import com.example.soccerapp.model.entity.Transfers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfers, Integer> {
}
