package com.visitscotland.brmx.translation.difference;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultipleField extends Field {
    private List<FieldValue> field;

    public List<FieldValue> getField() {
        return field;
    }

    public void setField(List<FieldValue> field) {
        this.field = field;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getDisplayName())
                .append(field)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        MultipleField rhs = (MultipleField) obj;
        return new EqualsBuilder()
                .append(getDisplayName(), rhs.getDisplayName())
                .append(field, rhs.field)
                .isEquals();
    }
}
