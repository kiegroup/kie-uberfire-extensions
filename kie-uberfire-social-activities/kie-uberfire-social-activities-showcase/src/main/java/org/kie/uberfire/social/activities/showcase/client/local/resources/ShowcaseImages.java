package org.kie.uberfire.social.activities.showcase.client.local.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Created by Cristiano Nicolai.
 */
public interface ShowcaseImages extends ClientBundle {

    ShowcaseImages INSTANCE = GWT.create( ShowcaseImages.class );

    @Source( "images/shadow-man-small.png" )
    ImageResource shadowManSmall();

    @Source( "images/shadow-man.png" )
    ImageResource shadowMan();
}
