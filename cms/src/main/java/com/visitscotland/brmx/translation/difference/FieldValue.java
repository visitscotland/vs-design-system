package com.visitscotland.brmx.translation.difference;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldValue {
    private String docBase;
    private String value;

    public FieldValue() {}

    public FieldValue(String docBase, String value) {
        this.docBase = docBase;
        this.value = value;
    }

    public FieldValue(String value) {
        this.value = value;
    }

    public String getDocBase() {
        return docBase;
    }

    public void setDocBase(String docBase) {
        this.docBase = docBase;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(docBase)
                .append(value)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj instanceof FieldValue) {
            FieldValue rhs = (FieldValue) obj;
            return new EqualsBuilder()
                    .append(docBase, rhs.docBase)
                    .append(value, rhs.value)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("docBase", docBase)
                .append("value", value)
                .toString();
    }
}
