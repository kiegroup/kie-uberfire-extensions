package org.kie.uberfire.social.activities.service;

import java.util.Map;

import org.jboss.errai.bus.server.annotations.Remote;
import org.kie.uberfire.social.activities.model.PagedSocialQuery;
import org.kie.uberfire.social.activities.model.SocialActivitiesEvent;
import org.kie.uberfire.social.activities.model.SocialPaged;

@Remote
public interface SocialTypeTimelinePagedRepositoryAPI {

    PagedSocialQuery getEventTimeline( String adapterName,
                                       SocialPaged socialPage );

    PagedSocialQuery getEventTimeline( SocialAdapter adapter,
                                       SocialPaged socialPaged );

    PagedSocialQuery getEventTimeline( String adapterName,
                                       SocialPaged socialPage,
                                       SocialPredicate<SocialActivitiesEvent> predicate );

    PagedSocialQuery getEventTimeline( String adapterName,
                                       SocialPaged socialPaged,
                                       Map commandsMap );

    PagedSocialQuery getEventTimeline( String adapterName,
                                       SocialPaged socialPaged,
                                       Map commandsMap,
                                       SocialPredicate<SocialActivitiesEvent> predicate );

    PagedSocialQuery getEventTimeline( SocialAdapter adapter,
                                       SocialPaged socialPaged,
                                       Map commandsMap,
                                       SocialPredicate<SocialActivitiesEvent> predicate );
}
