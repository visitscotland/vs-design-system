package com.visitscotland.brxm.mock;

import com.visitscotland.brxm.hippobeans.*;
import org.mockito.Answers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItineraryDayMockBuilder {

    Stop currentStop;
    DMSLink dmsLink;
    ItineraryExternalLink link;
    List<Stop> stops;
    Day day;

    public ItineraryDayMockBuilder(){
        day = mock(Day.class);
        stops = new ArrayList<>();
        when(day.getStops()).thenReturn(stops);
    }


    public ItineraryDayMockBuilder addStop() {
        currentStop = mock(Stop.class);
        dmsLink = null;
        link = null;

        stops.add(currentStop);

        //This mocked value is not representative of the real object.
        when(currentStop.getIdentifier()).thenReturn(String.valueOf(stops.size()));

        return this;
    }

    public ItineraryDayMockBuilder addDmsStop(String dmsId) {
        addStop();
        dmsLink = mock(DMSLink.class);

        when(currentStop.getStopItem()).thenReturn(dmsLink);
        when(dmsLink.getProduct()).thenReturn(dmsId);

        //This mocked value is not representative of the real object.
        when(currentStop.getIdentifier()).thenReturn(dmsId);

        return this;
    }

    public ItineraryDayMockBuilder addExternalStop(String url){
        addStop();
        link = mock(ItineraryExternalLink.class, Answers.RETURNS_DEEP_STUBS);

        when(currentStop.getStopItem()).thenReturn(link);
        when(link.getExternalLink().getLink()).thenReturn(url);

        //This mocked value is not representative of the real object.
        when(currentStop.getIdentifier()).thenReturn(url);

        when(currentStop.getImage()).thenReturn(mock(Image.class));

        return this;
    }

    public ItineraryDayMockBuilder subtitle(String subtitle){
        when(currentStop.getSubtitle()).thenReturn(subtitle);

        return this;
    }

    public ItineraryDayMockBuilder coordinates(Double lat, Double lon){
        if (link == null){
            //TODO MockException
            throw new RuntimeException("The Externalink hasn't been defined");
        }
        Coordinates coordinates = mock(Coordinates.class);
        when(coordinates.getLatitude()).thenReturn(lat);
        when(coordinates.getLongitude()).thenReturn(lon);
        when(link.getCoordinates()).thenReturn(coordinates);

        return this;
    }

    public ItineraryDayMockBuilder timeToExplore(String tte){
        if (link == null){
            //TODO MockException
            throw new RuntimeException("The Externalink hasn't been defined");
        }
        when(link.getTimeToExplore()).thenReturn(tte);
        return this;
    }

    public ItineraryDayMockBuilder ctaLabel(String label){
        if (link != null){
            when(link.getExternalLink().getLabel()).thenReturn(label);
        } else if (dmsLink != null){
            when(dmsLink.getLabel()).thenReturn(label);
        } else {
            //TODO MockException
            throw new RuntimeException("The Stop hasn't been properly defined");
        }
        return this;
    }

    public ItineraryDayMockBuilder addImage(){
        when(currentStop.getImage()).thenReturn(mock(Image.class));

        return this;
    }

    public ItineraryDayMockBuilder addDay() {
        day = mock(Day.class);
        return this;
    }

    public Day build(){
        return day;
    }

    public List<Day> buildAsList(){
        return Collections.singletonList(day);
    }


}
