package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.hippobeans.Stackla;
import com.visitscotland.brxm.mock.StacklaMockBuilder;
import com.visitscotland.brxm.model.StacklaModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StacklaFactoryTest {

    StacklaFactory stacklaFactory;

    @BeforeEach
    public void init() {
        this.stacklaFactory = new StacklaFactory();
    }

    @DisplayName("All information from stackla bean is passed into stackla module")
    @Test
    void stacklaModule() {
        Stackla stackla = new StacklaMockBuilder().title("title").copy("copy").dataHash("hash").dataId("id").build();
        StacklaModule module = stacklaFactory.getStacklaModule(stackla);
        Assertions.assertEquals("title", module.getTitle());
        Assertions.assertEquals("copy", module.getCopy().getContent());
        Assertions.assertEquals("hash", module.getDataHash());
        Assertions.assertEquals("id", module.getDataId());
    }


}
