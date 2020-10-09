package com.visitscotland.brmx.mock;

import com.visitscotland.brmx.beans.TourismInformation;
import org.mockito.Mockito;

public class TouristInformationMockBuilder {

    private TourismInformation mock;

    public TouristInformationMockBuilder() {
        mock = Mockito.mock(TourismInformation.class);
    }

    public TourismInformation build() {
        return mock;
    }
}
