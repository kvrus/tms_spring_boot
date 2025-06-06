package ru.moscow.tms.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.auth.controller.dto.RoleDto;
import ru.moscow.tms.auth.controller.dto.SignUpDto;
import ru.moscow.tms.auth.controller.dto.UserDto;
import ru.moscow.tms.auth.models.Role;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.service.AuthService;
import ru.moscow.tms.auth.service.UserService;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanDto;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanResponseDto;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanUpdateDto;
import ru.moscow.tms.tms.models.TCaseCategory;
import ru.moscow.tms.tms.models.TCasePriority;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.service.CaseServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FrontAdminController {

    final private UserService service;
    final private AuthService authService;
    final private CaseServiceImpl caseService;

    @GetMapping("/admin/users")
    public String getUsers(Model model,
        @RequestParam(defaultValue = "0") int page, // page starts at 0
        @RequestParam(defaultValue = "5") int size
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        Page<UserDto> pageResult = service.getAll(page, size);
        model.addAttribute("users",
                pageResult.get()
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
        return "admin/users";
    }

    @GetMapping("/admin/test/categories")
    public String getTestCategories(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        List<TCaseCategory> list = caseService.getAllCategories();
        model.addAttribute("list",
                list
        );
        return "admin/test_categories";
    }

    @GetMapping("/admin/test/priorities")
    public String getTestPriorities(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        List<TCasePriority> list = caseService.getAllPriorities();
        model.addAttribute("list",
                list
        );
        return "admin/test_priorities";
    }

    @GetMapping("/admin/users/add")
    public String addUserPage(Model model) {
        UserDto user = new UserDto();
        List<RoleDto> roles = service.getAllRoles().stream().map(r-> new RoleDto(r.getName(), r.getId(), r.getName().equals("USER"))).toList();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "admin/add_user";
    }

    @GetMapping("/admin/users/{userId}/edit")
    public String editPlanPage(@PathVariable("userId") String userId, Model model) {
        UserEntity user = service.getUser(Long.parseLong(userId));
        List<String> rolesString = user.getRoles().stream().map(Role::getName).toList();
        List<RoleDto> roles = service.getAllRoles().stream().map(r-> new RoleDto(r.getName(), r.getId(), rolesString.contains(r.getName()))).toList();
        model.addAttribute("user", new UserDto(user.getUsername(), user.getId(), user.getRoles().stream().map(Role::getName).toList()));
        model.addAttribute("roles", roles);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "admin/add_user";
    }

    @GetMapping("/admin/test/priorities/add")
    public String addPriorityPage(Model model) {
        TCasePriority priority = new TCasePriority();
        model.addAttribute("priority", priority);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "admin/add_priority";
    }

    @GetMapping("/admin/test/categories/add")
    public String addCategoryPage(Model model) {
        TCaseCategory category = new TCaseCategory();
        model.addAttribute("category", category);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "admin/add_category";
    }

    @GetMapping("/admin/test/priorities/{id}/edit")
    public String updatePriorityPage(@PathVariable("id") String id, Model model) {
        TCasePriority priority = caseService.getPriority(Long.parseLong(id));
        model.addAttribute("priority", priority);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "admin/add_priority";
    }

    @GetMapping("/admin/test/categories/{id}/edit")
    public String updateCategoryPage(@PathVariable("id") String id, Model model) {
        TCaseCategory category = caseService.getCategory(Long.parseLong(id));
        model.addAttribute("category", category);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "admin/add_category";
    }

    @PostMapping("/admin/users/add")
    public String saveUser(@ModelAttribute UserDto user, @AuthenticationPrincipal UserDetails userDetails) {
        authService.signUp(new SignUpDto(user.getName(), "123456"));
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/edit")
    public String editUser(@ModelAttribute UserDto user,  @AuthenticationPrincipal UserDetails userDetails) {
        service.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{userId}/delete")
    public String deleteUser(@PathVariable("planId") String userId, Model model) {
        service.markAsDeleted(Long.parseLong(userId));
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/test/categories/add")
    public String saveCategory(@ModelAttribute TCaseCategory category, @AuthenticationPrincipal UserDetails userDetails) {
        caseService.saveNewCategory(category);
        return "redirect:/admin/test/categories";
    }

    @PostMapping("/admin/test/categories/edit")
    public String editCategory(@ModelAttribute TCaseCategory category,  @AuthenticationPrincipal UserDetails userDetails) {
        caseService.updateCategory(category);
        return "redirect:/admin/test/categories";
    }

    @PostMapping("/admin/test/priorities/add")
    public String savePriority(@ModelAttribute TCasePriority priority, @AuthenticationPrincipal UserDetails userDetails) {
        caseService.saveNewPriority(priority);
        return "redirect:/admin/test/priorities";
    }

    @PostMapping("/admin/test/priorities/edit")
    public String editPriority(@ModelAttribute TCasePriority priority,  @AuthenticationPrincipal UserDetails userDetails) {
        caseService.updatePriority(priority);
        return "redirect:/admin/test/priorities";
    }
}
