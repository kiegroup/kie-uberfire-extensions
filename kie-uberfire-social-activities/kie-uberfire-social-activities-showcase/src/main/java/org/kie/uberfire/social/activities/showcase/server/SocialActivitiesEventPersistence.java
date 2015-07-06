package org.kie.uberfire.social.activities.showcase.server;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.kie.uberfire.social.activities.model.SocialActivitiesEvent;

/**
 * Created by Cristiano Nicolai.
 */
@ApplicationScoped
public class SocialActivitiesEventPersistence {

    private List<SocialActivitiesEvent> events = new ArrayList<SocialActivitiesEvent>();

    public void onEvent( @Observes SocialActivitiesEvent event ) {
        events.add( event );
    }

    public List<SocialActivitiesEvent> getEvents() {
        return events;
    }
}
