package org.kie.uberfire.social.activities.client.widgets.item.model;

import java.util.HashMap;
import java.util.Map;

import org.kie.uberfire.social.activities.model.SocialActivitiesEvent.LINK_TYPE;

public class LinkCommandParams {

    private String eventType;

    private String link;

    private LINK_TYPE linkType = LINK_TYPE.VFS;

    private Map<String, String> linkParams = new HashMap<String, String>(  );

    public LinkCommandParams( String eventType, String link, LINK_TYPE linkType ) {
        this.eventType = eventType;
        this.link = link;
        this.linkType = linkType;
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

    public LINK_TYPE getLinkType() {
        return linkType;
    }

    public void setLinkType( LINK_TYPE linkType ) {
        this.linkType = linkType;
    }

    public boolean isVFSLink() {
        return linkType == LINK_TYPE.VFS;
    }

    public Map<String, String> getLinkParams() {
        return linkParams;
    }

    public LinkCommandParams withLinkParams( Map<String, String> linkParams ) {
        if ( linkParams != null ) this.linkParams.putAll( linkParams );
        return this;
    }
}
