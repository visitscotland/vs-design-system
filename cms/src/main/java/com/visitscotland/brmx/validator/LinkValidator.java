package com.visitscotland.brmx.validator;

import com.visitscotland.brmx.translation.SessionFactory;
import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.Validator;
import org.onehippo.cms.services.validation.api.Violation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Optional;

import static org.hippoecm.repository.api.HippoNodeType.HIPPO_DOCBASE;

/**
 * jcr:Name = visitscotland:link-validator

 */
//TODO investigate if we can create unit test for this validator
public class LinkValidator implements Validator<Node> {

    private static final String EMPTY_DOCUMENT = "cafebabe-cafe-babe-cafe-babecafebabe";
    private SessionFactory sessionFactory;

    public LinkValidator() {
        this.sessionFactory = new SessionFactory();
    }

    public Optional<Violation> validate(final ValidationContext context, final Node document) {
        try {
            String nodeId = document.getProperty(HIPPO_DOCBASE).getValue().getString();
             if(!nodeId.equals(EMPTY_DOCUMENT)) {
//                 Node linkNode = sessionFactory.getJcrSession().getNodeByIdentifier(document.getProperty(HIPPO_DOCBASE).getString());
//                 Node childNode = linkNode.getNode(linkNode.getName());
                 Node childNode = sessionFactory.getHippoNodeByIdentifier(nodeId);
                 if (document.getParent().isNodeType("visitscotland:Day")) {
                     if (!childNode.isNodeType("visitscotland:Stop")) {
                         return Optional.of(context.createViolation("stop"));
                     }
                 }else{
                     if (!childNode.isNodeType("visitscotland:Page") && !childNode.isNodeType("visitscotland:SharedLink")){
                         return Optional.of(context.createViolation());
                     }
                 }
               }
        } catch (RepositoryException e) {
            return Optional.of(context.createViolation());
        }

        return Optional.empty();
    }
}

