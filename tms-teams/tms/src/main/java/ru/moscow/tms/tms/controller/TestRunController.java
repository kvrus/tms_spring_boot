package ru.moscow.tms.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.tms.controller.dto.CustomPage;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanResponseDto;
import ru.moscow.tms.tms.controller.dto.run.TestRunDto;
import ru.moscow.tms.tms.controller.dto.run.TestRunResponseDto;
import ru.moscow.tms.tms.controller.dto.run.TestRunUpdateDto;
import ru.moscow.tms.tms.models.TRun;
import ru.moscow.tms.tms.service.TestRunServiceImpl;

@RestController
@RequestMapping("/api/test/run")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:8080")
public class TestRunController {
    final private TestRunServiceImpl service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CustomPage<TestRunResponseDto> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<TRun> pageResult = service.getRuns(page, size);
        return new CustomPage<>(pageResult.getNumber(), pageResult.getSize(), pageResult.hasNext(), pageResult.get().map(item ->
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
                        item.getCreationDate())).toList());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTestExecution(@RequestBody TestRunDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        service.createTestRun(dto, userDetails.getUsername());
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateTestRun(@RequestBody TestRunUpdateDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        service.update(dto, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void markAsDeletedTestRun(@PathVariable("id") String caseId) {
        service.markAsDeleted(Long.parseLong(caseId));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void unmarkAsDeletedTestRun(@PathVariable("id") String caseId) {
        service.unmarkAsDeleted(Long.parseLong(caseId));
    }

}
