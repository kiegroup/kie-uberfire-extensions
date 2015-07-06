package org.kie.uberfire.social.activities.showcase.shared;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * Created by Cristiano Nicolai.
 */
@Portable
public class ShowcaseSocialUserEvent {

    private String username;

    public ShowcaseSocialUserEvent( @MapsTo( "username" ) final String username ) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
