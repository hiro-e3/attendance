package com.example.attendance.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.attendance.model.User;
import com.example.attendance.model.WorkRecord;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long> {
    List<WorkRecord> findByUser(User user);
    Optional<WorkRecord> findByUserAndWorkDate(User user, LocalDate workDate);
}