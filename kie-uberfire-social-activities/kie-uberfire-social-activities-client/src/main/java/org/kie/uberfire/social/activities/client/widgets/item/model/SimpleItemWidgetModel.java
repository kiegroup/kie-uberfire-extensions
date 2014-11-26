package org.kie.uberfire.social.activities.client.widgets.item.model;

import java.util.Date;
import java.util.List;

import org.kie.uberfire.social.activities.client.widgets.timeline.regular.model.SocialTimelineWidgetModel;
import org.kie.uberfire.social.activities.client.widgets.timeline.simple.model.SimpleSocialTimelineWidgetModel;
import org.kie.uberfire.social.activities.model.SocialUser;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.type.ClientResourceType;
import org.uberfire.mvp.Command;
import org.uberfire.mvp.ParameterizedCommand;

public class SimpleItemWidgetModel {

    private Date timestamp;
    private String linkText;
    private SocialUser socialUser;
    private String description;
    private PlaceManager placeManager;
    private Path linkPath;
    private String itemDescription;
    private String title;
    private boolean withFileIcon;
    private String eventType;
    private List<ClientResourceType> resourceTypes;
    private ParameterizedCommand<LinkCommandParams> linkCommand;

    public SimpleItemWidgetModel( SimpleSocialTimelineWidgetModel model,
                                  String eventType,
                                  Date timestamp,
                                  String linkText,
                                  Path linkPath,
                                  String itemDescription ) {

        this.socialUser = model.getSocialUser();
        this.resourceTypes = model.getResourceTypes();
        this.placeManager = model.getPlaceManager();
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.linkText = linkText;
        this.linkPath = linkPath;
        this.itemDescription = itemDescription;

    }

    public boolean shouldIPrintIcon() {
        return resourceTypes != null && getLinkText() != null;
    }

    public SimpleItemWidgetModel( SimpleSocialTimelineWidgetModel model,
                                  String eventType,
                                  Date timestamp,
                                  String description,
                                  String itemDescription ) {
        this.socialUser = model.getSocialUser();
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.description = description;
        this.itemDescription = itemDescription;

    }

    public SimpleItemWidgetModel( SocialTimelineWidgetModel model,
                                  String eventType,
                                  Date timestamp,
                                  String linkText,
                                  Path linkPath,
                                  String itemDescription ) {
        this.socialUser = model.getSocialUser();
        this.placeManager = model.getPlaceManager();
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.linkText = linkText;
        this.linkPath = linkPath;
        this.itemDescription = itemDescription;
    }

    public SimpleItemWidgetModel( SocialTimelineWidgetModel model,
                                  String eventType,
                                  Date timestamp,
                                  String description,
                                  String itemDescription ) {
        this.socialUser = model.getSocialUser();
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.description = description;
        this.itemDescription = itemDescription;

    }

    public String getDescription() {
        return description;
    }

    public String getLinkText() {
        return cleanLinkText( linkText );
    }

    private String cleanLinkText( String linkText ) {
        if ( hasExtension( linkText ) ) {
            return removeExtension( linkText );
        }
        return linkText;
    }

    private String removeExtension( String linkText ) {
        return linkText.substring( 0, linkText.indexOf( "." ) );
    }

    private boolean hasExtension( String linkText ) {
        return linkText.indexOf( '.' ) > 0;
    }

    public SocialUser getSocialUser() {
        return socialUser;
    }

    public PlaceManager getPlaceManager() {
        return placeManager;
    }

    public Path getLinkPath() {
        return linkPath;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public List<ClientResourceType> getResourceTypes() {
        return resourceTypes;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType( String eventType ) {
        this.eventType = eventType;
    }

    public SimpleItemWidgetModel withLinkCommand( ParameterizedCommand<LinkCommandParams> linkCommand ) {
        this.linkCommand = linkCommand;
        return this;
    }

    public ParameterizedCommand<LinkCommandParams> getLinkCommand() {
        if(linkCommand==null){
            return new ParameterizedCommand<LinkCommandParams>() {
                @Override
                public void execute( LinkCommandParams parameters ) {

                }
            };
        }
        return linkCommand;
    }

}
