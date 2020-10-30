package com.visitscotland.brmx.validator;

import com.visitscotland.brmx.beans.InstagramImage;
import com.visitscotland.brmx.beans.ListicleItem;
import com.visitscotland.brmx.beans.SharedLink;
import com.visitscotland.brmx.beans.Stop;
import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.Validator;
import org.onehippo.cms.services.validation.api.Violation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Optional;
import static org.hippoecm.repository.api.HippoNodeType.HIPPO_DOCBASE;
/**
 * jcr:Name = visitscotland:link-image-validator

 */

//TODO investigate if we can create unit test for this validator
public class LinkImageValidator implements Validator<Node> {

    private static final String EMPTY_IMAGE = "cafebabe-cafe-babe-cafe-babecafebabe";

    public Optional<Violation> validate(final ValidationContext context, final Node document) {
        try {
            Node node = null;
            if (document.hasNode(SharedLink.LINK_TYPES)){
                node = document.getNode(SharedLink.LINK_TYPES);
            }else if (document.hasNode(Stop.PRODUCTS)){
                node = document.getNode(Stop.PRODUCTS);
                }else if (document.hasNode(ListicleItem.PRODUCT)){
                    node = document.getNode(ListicleItem.PRODUCT);
                }
            if (node != null) {
                //Make sure that for Share links and stops, if the product is not dms, an image is provided or/and selected
                if (!node.hasProperty("visitscotland:product")) {
                    if (document.hasNode("visitscotland:image")) {
                        if(document.getNode("visitscotland:image").getProperty(HIPPO_DOCBASE).getValue().getString().equals(EMPTY_IMAGE)) {
                            return Optional.of(context.createViolation());
                        }
                    }else
                        //Images for Listicle that allows CMS images but also Instagram images
                        if (document.hasNode(ListicleItem.IMAGES)){
                            Node images = document.getNode(ListicleItem.IMAGES);
                            if (!images.hasProperty(InstagramImage.CAPTION) && ((!images.hasProperty(HIPPO_DOCBASE))
                                    || (images.hasProperty(HIPPO_DOCBASE) && images.getProperty(HIPPO_DOCBASE).getValue().getString().equals(EMPTY_IMAGE)))) {
                                Violation violation = context.createViolation();
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

