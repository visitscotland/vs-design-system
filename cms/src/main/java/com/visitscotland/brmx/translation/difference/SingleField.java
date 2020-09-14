package com.visitscotland.brmx.translation.difference;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleField extends Field {
    private FieldValue field;

    public FieldValue getField() {
        return field;
    }

    public void setField(FieldValue field) {
        this.field = field;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(field)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj instanceof SingleField) {
            SingleField rhs = (SingleField) obj;
            return new EqualsBuilder()
                    .append(field, rhs.field)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("field", field)
                .toString();
    }
}
