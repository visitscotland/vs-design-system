package com.visitscotland.brmx.translation;

import org.hippoecm.frontend.types.IFieldDescriptor;
import org.hippoecm.frontend.types.ITypeDescriptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class IFieldDescriptorBuilder {
    String name;
    String path;
    Boolean isMultiple;
    Boolean isPrimary;
    ITypeDescriptor typeDescriptor;

    public IFieldDescriptorBuilder withTypeDescriptor(ITypeDescriptor typeDescriptor) {
        this.typeDescriptor = typeDescriptor;
        return this;
    }

    public IFieldDescriptorBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public IFieldDescriptorBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public IFieldDescriptorBuilder isMultiple(boolean isMultiple) {
        this.isMultiple = isMultiple;
        return this;
    }

    public IFieldDescriptorBuilder isPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
        return this;
    }

    public IFieldDescriptor build() {
        IFieldDescriptor mockField = mock(IFieldDescriptor.class);
        if (null != name) {
            when(mockField.getName()).thenReturn(name);
        }
        if (null != path) {
            when(mockField.getPath()).thenReturn(path);
        }
        if (null != isMultiple) {
            when(mockField.isMultiple()).thenReturn(isMultiple.booleanValue());
        }
        if (null != isPrimary) {
            when(mockField.isPrimary()).thenReturn(isPrimary.booleanValue());
        }
        if (null != typeDescriptor) {
            when(mockField.getTypeDescriptor()).thenReturn(typeDescriptor);
        }
        return mockField;
    }
}
