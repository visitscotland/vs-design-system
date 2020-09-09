package com.visitscotland.brmx.translation.difference;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class FieldDifference {
    private String property;
    private String caption;
    private Field previous;
    private Field latest;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Field getPrevious() {
        return previous;
    }

    public void setPrevious(Field previous) {
        this.previous = previous;
    }

    public Field getLatest() {
        return latest;
    }

    public void setLatest(Field latest) {
        this.latest = latest;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(property)
                .append(caption)
                .append(previous)
                .append(latest)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj instanceof FieldDifference) {
            FieldDifference rhs = (FieldDifference) obj;
            return new EqualsBuilder()
                    .append(property, rhs.property)
                    .append(caption, rhs.caption)
                    .append(previous, rhs.previous)
                    .append(latest, rhs.latest)
                    .isEquals();
        } else {
            return false;
        }
    }
}
