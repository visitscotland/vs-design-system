package com.visitscotland.brmx.validator;

import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.ValidationContextException;
import org.onehippo.cms.services.validation.api.Validator;
import org.onehippo.cms.services.validation.api.Violation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Optional;

/**
 * visitscotland:scotland-coordinates-validator
 */
public class CoordinatesValidator implements Validator<Node> {

    private static final String NORTH = "extreme.north";
    private static final String SOUTH = "extreme.south";
    private static final String EAST = "extreme.east";
    private static final String WEST = "extreme.west";

    private double north;
    private double south;
    private double east;
    private double west;

    public CoordinatesValidator(final Node config) {
        try {
            north = config.getProperty(NORTH).getDouble();
            south = config.getProperty(SOUTH).getDouble();
            east = config.getProperty(EAST).getDouble();
            west = config.getProperty(WEST).getDouble();
            if (north < south || east < west) {
                throw new ValidationContextException("Coordinates configuration is wrong. Verify the node " + config.getPath());
            }
        } catch (RepositoryException e) {
            try {
                throw new ValidationContextException("Cannot read required properties for the CoordinatesValidator. Verify the node " + config.getPath(), e);
            } catch (RepositoryException nodeException) {
                throw new ValidationContextException("Unexpected Error when loading CoordinatesValidator class", nodeException);
            }
        }
    }

    public Optional<Violation> validate(final ValidationContext context, final Node document) {
        try {
            if (document.hasProperty("visitscotland:latitude") || document.hasProperty("visitscotland:longitude")) {
                double lat = document.getProperty("visitscotland:latitude").getDouble();
                double lon = document.getProperty("visitscotland:longitude").getDouble();
                if (north < lat || lat < south || east < lon || lon < west) {
                    return Optional.of(context.createViolation());
                }
            }
        } catch (RepositoryException e) {
            return Optional.of(context.createViolation());
        }

        return Optional.empty();
    }
}
