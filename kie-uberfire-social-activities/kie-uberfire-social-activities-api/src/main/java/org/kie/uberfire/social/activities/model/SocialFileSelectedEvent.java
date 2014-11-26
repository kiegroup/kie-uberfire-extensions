package org.kie.uberfire.social.activities.model;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class SocialFileSelectedEvent {

    private String uri;

    private String eventType;

    public SocialFileSelectedEvent(){}

    public SocialFileSelectedEvent(String eventType, String uri){
        this.eventType = eventType;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public String getEventType() {
        return eventType;
    }
}
