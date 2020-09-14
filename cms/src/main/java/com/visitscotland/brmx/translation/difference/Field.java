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

}
