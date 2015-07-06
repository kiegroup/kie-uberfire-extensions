package org.kie.uberfire.social.activities.showcase.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.kie.uberfire.social.activities.model.DefaultTypes;
import org.kie.uberfire.social.activities.model.SocialActivitiesEvent;
import org.kie.uberfire.social.activities.model.SocialEventType;
import org.kie.uberfire.social.activities.model.SocialUser;
import org.kie.uberfire.social.activities.service.SocialAdapter;
import org.kie.uberfire.social.activities.service.SocialCommandTypeFilter;
import org.kie.uberfire.social.activities.service.SocialUserRepositoryAPI;
import org.kie.uberfire.social.activities.showcase.shared.ShowcaseSocialUserEvent;

@ApplicationScoped
public class ShowcaseSocialUserEventAdapter implements SocialAdapter<ShowcaseSocialUserEvent> {

    private AtomicLong counter = new AtomicLong();

    @Inject
    private SocialUserRepositoryAPI socialUserRepositoryAPI;

    @Override
    public Class<ShowcaseSocialUserEvent> eventToIntercept() {
        return ShowcaseSocialUserEvent.class;
    }

    @Override
    public SocialEventType socialEventType() {
        return DefaultTypes.DUMMY_EVENT;
    }

    @Override
    public boolean shouldInterceptThisEvent( Object event ) {
        return event.getClass().getCanonicalName().equals( eventToIntercept().getCanonicalName() ) ? true : false;
    }

    public void onEvent( @Observes ShowcaseSocialUserEvent event ) {
        //Include @Observes only so events are propagated to server side.
    }

    @Override
    public SocialActivitiesEvent toSocial( Object object ) {
        final ShowcaseSocialUserEvent event = (ShowcaseSocialUserEvent) object;
        SocialUser socialUser = null;
        try {
            socialUser = socialUserRepositoryAPI.findSocialUser( event.getUsername() );
        } catch ( ContextNotActiveException e ) {
            //clean repository
            socialUser = new SocialUser( "system" );
        }
        final String desc = String.format( "new social event (%d)", counter.incrementAndGet() );
        return new SocialActivitiesEvent( socialUser, DefaultTypes.DUMMY_EVENT, new Date() )
                .withAdicionalInfo( "edited" )
                .withDescription( desc )
                .withLink( String.format( "Main$%d.java", counter.get() ), "file", SocialActivitiesEvent.LINK_TYPE.CUSTOM )
                .withParam( "scheme", "http" );
    }

    @Override
    public List<SocialCommandTypeFilter> getTimelineFilters() {
        return new ArrayList<SocialCommandTypeFilter>();
    }

    @Override
    public List<String> getTimelineFiltersNames() {
        return new ArrayList<String>();
    }
}
