package com.example.attendance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.attendance.model.User;
import com.example.attendance.repository.UserRepository;
import com.example.attendance.service.WorkRecordService;

@Controller
@RequestMapping("/work")
public class WorkRecordController {

    private final WorkRecordService workRecordService;
    private final UserRepository userRepository;

    public WorkRecordController(WorkRecordService workRecordService, UserRepository userRepository) {
        this.workRecordService = workRecordService;
        this.userRepository = userRepository;
    }

    private User getLoginUser() {
        return userRepository.findByUsername("testuser")
                .orElseThrow(() -> new IllegalStateException("ユーザーが存在しません"));
    }

    @GetMapping
    public String showWorkStatus(Model model) {
        User user = getLoginUser();
        model.addAttribute("user", user);
        model.addAttribute("record", workRecordService.getTodayRecord(user).orElse(null));
        return "work/status";
    }

    @PostMapping("/clock-in")
    public String clockIn() {
        workRecordService.clockIn(getLoginUser());
        return "redirect:/work";
    }

    @PostMapping("/clock-out")
    public String clockOut() {
        workRecordService.clockOut(getLoginUser());
        return "redirect:/work";
    }
}