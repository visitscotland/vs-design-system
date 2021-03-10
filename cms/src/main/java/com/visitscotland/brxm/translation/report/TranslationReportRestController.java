package com.visitscotland.brxm.translation.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestScope
public class TranslationReportRestController {


    private TranslationReportService translationReportService;

    @Autowired
    public TranslationReportRestController(TranslationReportService translationReportService) {
        this.translationReportService = translationReportService;
    }

    @GetMapping("/translation/untranslated")
    public List<TranslationModel> untranslatedFiles(@RequestParam String locale) {
        if (!TranslationReportService.SUPPORTED_LOCALES.contains(locale)) return Collections.emptyList();
        return TranslationReportService.getUntranslatedDocuments(locale);
    }

    @PostMapping("/translation/{handleId}/priority")
    public void setTranslationPriority(@PathVariable String handleId, @RequestBody Map<String, Object> requestBody) {
        Object priorityString = requestBody.get("priority");
        if (priorityString != null) {
            translationReportService.setTranslationPriority(handleId, TranslationPriority.valueOf(priorityString.toString()));
            throw new ResponseStatusException(HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No priority provided");
    }

    @ExceptionHandler(TranslationReportException.class)
    public void translationReportExceptionHandler(TranslationReportException ex) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }


}
