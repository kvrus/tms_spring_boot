package ru.moscow.tms.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseDto;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseUpdateDto;
import ru.moscow.tms.tms.service.CaseServiceImpl;
import ru.moscow.tms.tms.service.PlanServiceImpl;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseResponseDto;
import ru.moscow.tms.tms.controller.dto.plan.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:8080")
public class FrontTestPlansController {

    final private PlanServiceImpl service;
    final private CaseServiceImpl caseService;

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

    @GetMapping("/plans/add")
    public String addPlanPage(Model model) {
        TestPlanUpdateDto plan = new TestPlanUpdateDto();
        model.addAttribute("plan", plan);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "add_plan";
    }

    @PostMapping("/plans/add")
    public String savePlan(@ModelAttribute TestPlanDto plan,  @AuthenticationPrincipal UserDetails userDetails) {
        service.createTestPlan(plan, userDetails.getUsername());
        return "redirect:/plans";
    }

    @PostMapping("/plans/edit")
    public String editPlan(@ModelAttribute TestPlanResponseDto plan,  @AuthenticationPrincipal UserDetails userDetails) {
        service.update(new TestPlanUpdateDto(plan.getId(), plan.getName(), plan.getDescription(), plan.getTypeName()), userDetails.getUsername());
        return "redirect:/plans";
    }

    @GetMapping("/plans/{planId}/edit")
    public String editPlanPage(@PathVariable("planId") String planId, Model model) {
        TestPlanResponseDto plan = service.getPlanById(Long.parseLong(planId));
        model.addAttribute("plan", plan);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "add_plan";
    }

    @GetMapping("/plans/{planId}/delete")
    public String deletePlanPage(@PathVariable("planId") String planId, Model model) {
        service.markAsDeleted(Long.parseLong(planId));
        return "redirect:/plans";
    }

    @GetMapping("/plans/{planId}/cases")
    public String getAllCasesInPlan(@PathVariable("planId") String planId, Model model) {
        TestPlanResponseDto plan = service.getPlanById(Long.parseLong(planId));
        model.addAttribute("plan", plan);
        List<TestCaseResponseDto> list = service.getAllCasesInPlan(Long.parseLong(planId));
        model.addAttribute("testCases", list);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        model.addAttribute("testcase", new TestCaseDto());
        return "cases";
    }

    @GetMapping("/plans/{planId}/cases/add")
    public String addCaseToPlanPage(@PathVariable("planId") String planId, Model model) {
        TestPlanResponseDto plan = service.getPlanById(Long.parseLong(planId));
        model.addAttribute("plan", plan);
        TestCaseUpdateDto testCase = new TestCaseUpdateDto();
        model.addAttribute("testcase", testCase);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "add_case";
    }

    @GetMapping("/plans/{planId}/cases/{caseId}/edit")
    public String editCasePage(@PathVariable("planId") String planId, @PathVariable("caseId") String caseId, Model model) {
        TestPlanResponseDto plan = service.getPlanById(Long.parseLong(planId));
        model.addAttribute("plan", plan);
        TestCaseUpdateDto testCase = service.getCaseById(Long.parseLong(caseId));
        model.addAttribute("testcase", testCase);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "add_case";
    }

    @GetMapping("/plans/{planId}/cases/{caseId}/delete")
    public String deleteCasePage(@PathVariable("planId") String planId, @PathVariable("caseId") String caseId, Model model) {
        caseService.markAsDeleted(Long.parseLong(caseId));
        return "redirect:/plans/"+planId+"/cases";
    }


    @PostMapping("/plans/{planId}/cases/add")
    public String handleCaseFormSubmit(@ModelAttribute("testcase") TestCaseUpdateDto testcase, @PathVariable("planId") String planId, @AuthenticationPrincipal UserDetails userDetails){
        TestPlanResponseDto plan = service.getPlanById(Long.parseLong(planId));
        caseService.createTestCase(new TestCaseDto(
                testcase.getName(),
                testcase.getDescription(),
                plan.getName(),
                testcase.getCategory(),
                testcase.getPriority()
        ), userDetails.getUsername());
        return "redirect:/plans/"+planId+"/cases";
    }

    @PostMapping("/plans/{planId}/cases/{caseId}/edit")
    public String handleEditCaseFormSubmit(@ModelAttribute("testcase") TestCaseUpdateDto testcase, @PathVariable("planId") String planId, @PathVariable("caseId") String caseId, @AuthenticationPrincipal UserDetails userDetails){
        caseService.updateTestCase(testcase, userDetails.getUsername());
        return "redirect:/plans/"+planId+"/cases";
    }

}
