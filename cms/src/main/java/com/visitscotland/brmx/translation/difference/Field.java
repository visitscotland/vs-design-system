package com.visitscotland.brmx.translation.difference;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "objectType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleField.class, name="multiple"),
        @JsonSubTypes.Type(value = SingleField.class, name="single")
})
public abstract class Field {
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(displayName)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj instanceof Field) {
            Field rhs = (Field) obj;
            return new EqualsBuilder()
                    .append(displayName, rhs.displayName)
                    .isEquals();
        } else {
            return false;
        }
    }
}
