package com.example.attendance.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.attendance.model.User;
import com.example.attendance.model.WorkRecord;
import com.example.attendance.repository.WorkRecordRepository;

@Service
public class WorkRecordService {
    private final WorkRecordRepository workRecordRepository;

    public WorkRecordService(WorkRecordRepository workRecordRepository) {
        this.workRecordRepository = workRecordRepository;
    }

    public WorkRecord clockIn(User user) {
        LocalDate today = LocalDate.now();
        Optional<WorkRecord> existing = workRecordRepository.findByUserAndWorkDate(user, today);

        if (existing.isPresent()) {
            throw new IllegalStateException("すでに出勤済みです");
        }

        WorkRecord record = new WorkRecord();
        record.setUser(user);
        record.setWorkDate(today);
        record.setClockIn(LocalDateTime.now());

        return workRecordRepository.save(record);
    }

    public WorkRecord clockOut(User user) {
        LocalDate today = LocalDate.now();
        WorkRecord record = workRecordRepository.findByUserAndWorkDate(user, today)
                .orElseThrow(() -> new IllegalStateException("出勤記録が見つかりません"));

        record.setClockOut(LocalDateTime.now());
        return workRecordRepository.save(record);
    }

    public Optional<WorkRecord> getTodayRecord(User user) {
        return workRecordRepository.findByUserAndWorkDate(user, LocalDate.now());
    }
}