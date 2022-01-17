package com.visitscotland.brxm.validator;

import com.visitscotland.brxm.translation.SessionFactory;
import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.Validator;
import org.onehippo.cms.services.validation.api.Violation;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import java.util.Optional;

import static org.hippoecm.repository.api.HippoNodeType.HIPPO_DOCBASE;

/**
 * jcr:Name = visitscotland:link-validator

 */
public class LinkValidator implements Validator<Node> {

    public static final String EMPTY_DOCUMENT = "cafebabe-cafe-babe-cafe-babecafebabe";
    private SessionFactory sessionFactory;

    static final String DAY = "visitscotland:Day";
    static final String VIDEO = "visitscotland:VideoLink";

    public LinkValidator() {
        this.sessionFactory = new SessionFactory();
    }

    LinkValidator(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public Optional<Violation> validate(final ValidationContext context, final Node document) {
        try {
            String nodeId = document.getProperty(HIPPO_DOCBASE).getValue().getString();
             if(!nodeId.equals(EMPTY_DOCUMENT)) {
                 Node childNode =  sessionFactory.getHippoNodeByIdentifier(nodeId);
                 if (!document.getPath().split("/")[3].equals(childNode.getPath().split("/")[3])) {
                     return Optional.of(context.createViolation("channel"));
                 }else {
                     return checkAllowedDocuments(context, document, childNode);
                 }
             } else {
                 return Optional.of(context.createViolation("emptyLink"));
             }
        } catch (PathNotFoundException e) {
            return Optional.of(context.createViolation("translation"));
        } catch (RepositoryException e) {
            return Optional.of(context.createViolation());
        }
    }

    private Optional<Violation> checkAllowedDocuments(final ValidationContext context, final Node document, final Node childNode) throws RepositoryException {
        if (document.getParent().isNodeType(DAY)) {
            if (!childNode.isNodeType("visitscotland:Stop")) {
                return Optional.of(context.createViolation("stop"));
            }
        } else if (document.getParent().isNodeType(VIDEO)) {
            if (!childNode.isNodeType("visitscotland:Video")){
                return Optional.of(context.createViolation("video"));
            }
        } else {
            if (!childNode.isNodeType("visitscotland:Page") && !childNode.isNodeType("visitscotland:SharedLink")){
                return Optional.of(context.createViolation());
            }
        }

        return Optional.empty();
    }


}

