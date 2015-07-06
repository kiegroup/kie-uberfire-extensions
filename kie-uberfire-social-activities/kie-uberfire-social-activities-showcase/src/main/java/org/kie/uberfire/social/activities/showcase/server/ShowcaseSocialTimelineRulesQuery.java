package org.kie.uberfire.social.activities.showcase.server;

import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.kie.uberfire.social.activities.model.SocialActivitiesEvent;
import org.kie.uberfire.social.activities.service.SocialTimelineRulesQueryAPI;

/**
 * Created by Cristiano Nicolai.
 */
@Service
@ApplicationScoped
public class ShowcaseSocialTimelineRulesQuery implements SocialTimelineRulesQueryAPI {

    @Inject
    private SocialActivitiesEventPersistence eventPersistence;

    @Override
    public List<SocialActivitiesEvent> executeAllRules() {
        return eventPersistence.getEvents();
    }

    @Override
    public List<SocialActivitiesEvent> executeSpecificRule( Map<String, String> globals, String drlName, String maxResults ) {
        return eventPersistence.getEvents();
    }

    @Override
    public List<SocialActivitiesEvent> getAllCached() {
        return eventPersistence.getEvents();
    }

    @Override
    public List<SocialActivitiesEvent> getTypeCached( String... typeNames ) {
        return eventPersistence.getEvents();
    }

    @Override
    public List<SocialActivitiesEvent> getNEventsFromEachType( int numberOfEvents, String... typeNames ) {
        return eventPersistence.getEvents();
    }
}
