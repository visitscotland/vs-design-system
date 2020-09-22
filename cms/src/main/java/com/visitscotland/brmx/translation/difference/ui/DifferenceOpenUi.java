package com.visitscotland.brmx.translation.difference.ui;

import com.visitscotland.brmx.translation.SessionFactory;
import com.visitscotland.brmx.translation.TranslationService;
import com.visitscotland.brmx.translation.difference.DifferenceGenerator;
import com.visitscotland.brmx.translation.difference.DocumentDifference;
import com.visitscotland.brmx.translation.plugin.JcrDocument;
import com.visitscotland.brmx.translation.plugin.JcrDocumentFactory;
import org.hippoecm.repository.api.WorkflowDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.annotation.RequestScope;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Controller
@RequestScope
public class DifferenceOpenUi {
    private TranslationService translationService;

    @Autowired
    public DifferenceOpenUi(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/vs-openui/diff")
    public String openUiComponent() {
        return "diffButtonWrapper";
    }

    @GetMapping("/vs-openui/diff/{nodeId}")
    public String openUiFragment(@PathVariable String nodeId, Model model) {
        try {
            JcrDocument document = translationService.getDocument(nodeId);
            model.addAttribute("nodeId", nodeId);
            if ( "en".equals(document.getLocaleName()) ) {
                model.addAttribute("hasTranslationPending", translationService.hasPendingTranslations(document));
                return "diffButtonContentEnglish";
            } else {
                return "diffButtonContent";
            }

        } catch(RepositoryException ex) {
            return "diffButtonError";
        }
    }
}
