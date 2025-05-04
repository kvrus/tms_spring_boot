package ru.moscow.tms.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseResponseDto;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanResponseDto;
import ru.moscow.tms.tms.controller.dto.run.TestRunResponseDto;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.models.TRun;
import ru.moscow.tms.tms.service.PlanServiceImpl;
import ru.moscow.tms.tms.service.TestRunServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:8080")
public class FrontTestRunsController {

    final private TestRunServiceImpl service;

    @GetMapping("/runs")
    public String runsPage(
            @RequestParam(defaultValue = "0") int page, // page starts at 0
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<TRun> pageResult = service.getRuns(page, size);
        model.addAttribute("testRuns",
                pageResult.get().map(item ->
                        new TestRunResponseDto(
                                item.getId(),
                                item.getName(),
                                item.getDescription(),
                                new TestPlanResponseDto(
                                        item.getPlan().getId(),
                                        item.getPlan().getName(),
                                        item.getPlan().getDescription(),
                                        item.getPlan().getPlanType().getName(),
                                        item.getPlan().getAuthor().getUsername(),
                                        item.getPlan().getCreationDate()
                                ),
                                item.getAuthor().getUsername(),
                                item.getCreationDate())).toList()
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
        return "runs";
    }
}
