package org.kie.uberfire.social.activities.client.widgets.item.model;

import org.kie.uberfire.social.activities.model.SocialEventType;

public class LinkCommandParams {

    private String eventType;

    private String link;

    public LinkCommandParams( String eventType, String link ) {
        this.eventType = eventType;
        this.link = link;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType( String eventType ) {
        this.eventType = eventType;
    }

    public String getLink() {
        return link;
    }

    public void setLink( String link ) {
        this.link = link;
    }
}
