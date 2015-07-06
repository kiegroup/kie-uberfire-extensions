package org.kie.uberfire.social.activities.client.gravatar;

import com.google.gwt.user.client.ui.Image;

public class GravatarImage extends Image {

    public GravatarImage( final String email,
                          final int size ) {
        setUrl( GravatarUrlBuilder.get().build( email, size ) );
    }

}
