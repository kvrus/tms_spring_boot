package ru.moscow.tms.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.moscow.tms.auth.controller.dto.RoleDto;
import ru.moscow.tms.auth.controller.dto.SignUpDto;
import ru.moscow.tms.auth.controller.dto.UserDto;
import ru.moscow.tms.auth.models.Role;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.service.AuthService;
import ru.moscow.tms.auth.service.UserService;
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

}
