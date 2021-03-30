package com.visitscotland.brxm.translation.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestScope
public class TranslationReportRestController {


    private final TranslationReportService translationReportService;

    @Autowired
    public TranslationReportRestController(TranslationReportService translationReportService) {
        this.translationReportService = translationReportService;
    }

    @GetMapping("/translation/untranslated")
    public RestListContainer<TranslationModel> untranslatedFiles(@RequestParam String locale) {
        if (!translationReportService.isLocaleSupported(locale)) {
            return new RestListContainer<>(Collections.emptyList());
        }
        return new RestListContainer<>(translationReportService.getUntranslatedDocuments(locale));
    }

    @PostMapping("/translation/{handleId}/priority")
    public void setTranslationPriority(@PathVariable String handleId, @RequestBody Map<String, Object> requestBody) {
        Object priorityString = requestBody.get("priority");
        if (priorityString != null) {
            translationReportService.setTranslationPriority(handleId, TranslationPriority.valueOf(priorityString.toString()));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No priority provided");
    }

    @GetMapping("/translation/priority")
    public List<List<Object>> getTranslationPriorityOptions() {
        return Arrays.stream(TranslationPriority.values()).map(priority -> {
            return Arrays.asList(priority.toString(),(Object)priority.name,  priority.sortOrder);
        }).collect(Collectors.toList());
    }

    @GetMapping("/translation/pages")
    public Set<String> getPageTypes() {
        return translationReportService.getPageTypes();
    }

    @GetMapping("/translation/modules")
    public Set<String> getModuleTypes() {
        return translationReportService.getModuleTypes();
    }

    @ExceptionHandler(TranslationReportException.class)
    public void translationReportExceptionHandler(TranslationReportException ex) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }


}
