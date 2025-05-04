package ru.moscow.tms.front.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import ru.moscow.tms.auth.controller.dto.SignUpDto;
import ru.moscow.tms.tms.service.PlanServiceImpl;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseResponseDto;
import ru.moscow.tms.tms.controller.dto.plan.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:8080")
public class FrontTestPlansController {

    final private PlanServiceImpl service;

    @GetMapping("/plans")
    public String plansPage(
            @RequestParam(defaultValue = "0") int page, // page starts at 0
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<TPlan> pageResult = service.getPlans(page, size);
        model.addAttribute("testPlans",
                pageResult.get().map(item -> new TestPlanResponseDto(item.getId(), item.getName(), item.getDescription(), item.getPlanType().getName(), TestPlanResponseDto.getUserNameIfExists(item.getAuthor()), item.getCreationDate())).toList()
                );
        model.addAttribute("currentPage",
                pageResult.getNumber()
        );
        model.addAttribute("pageSize",
                pageResult.getSize()
        );
        model.addAttribute("hasNext",
                pageResult.hasNext()
        );
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "plans";
    }

    @GetMapping("/cases")
    public String casesPage(Model model) {
        List<TestCaseResponseDto> cases = service.getAllCasesInPlan(2L);
        model.addAttribute("testCases", cases);
        return "cases";
    }

/*
    @ModelAttribute("user")
    public User wrapFormToModel(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return user;
    }
 */

}
