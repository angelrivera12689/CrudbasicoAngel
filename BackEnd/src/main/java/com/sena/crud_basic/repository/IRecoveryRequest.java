package com.sena.crud_basic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.crud_basic.model.RecoveryRequest;

public interface IRecoveryRequest extends JpaRepository<RecoveryRequest, Integer> {
    Optional<RecoveryRequest> findByToken(String token);
    List<RecoveryRequest> findByUserID_UserID(int userID);
    List<RecoveryRequest> findByIsUsed(boolean isUsed);
}