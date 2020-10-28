package com.visitscotland.brmx.validator;

import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.Validator;
import org.onehippo.cms.services.validation.api.Violation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Optional;

/**
 * jcr:Name = visitscotland:scotland-link-image-validator

 */

//TODO investigate if we can create unit test for this validator
public class LinkImageValidator implements Validator<Node> {

    private static final String EMPTY_IMAGE = "cafebabe-cafe-babe-cafe-babecafebabe";

    public Optional<Violation> validate(final ValidationContext context, final Node document) {
        try {
            Node node = document.hasNode("visitscotland:linkTypes")? document.getNode("visitscotland:linkTypes"): document.hasNode("visitscotland:products")? document.getNode("visitscotland:products"):document.getNode("visitscotland:product");
            if (node != null) {
                //Make sure that for Share links and stops, if the product is not dms, an image is provided or/and selected
                if (!node.hasProperty("visitscotland:product")) {
                    if (document.hasNode("visitscotland:image")) {
                        if(document.getNode("visitscotland:image").getProperty("hippo:docbase").getValue().getString().equals(EMPTY_IMAGE)) {
                            return Optional.of(context.createViolation());
                        }
                    }else
                        //Images for Listicle that allows CMS images but also Instagram images
                        if (document.hasNode("visitscotland:images")){
                            Node images = document.getNode("visitscotland:images");
                            if (!images.hasProperty("visitscotland:caption") && ((!images.hasProperty("hippo:docbase"))
                                    || (images.hasProperty("hippo:docbase") && images.getProperty("hippo:docbase").getValue().getString().equals(EMPTY_IMAGE)))) {
                                return Optional.of(context.createViolation());
                            }
                            }else{
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

