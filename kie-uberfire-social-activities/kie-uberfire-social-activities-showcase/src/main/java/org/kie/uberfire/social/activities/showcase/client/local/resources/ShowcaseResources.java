package org.kie.uberfire.social.activities.showcase.client.local.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

/**
 * Created by Cristiano Nicolai.
 */
public interface ShowcaseResources extends ClientBundle {

    ShowcaseResources INSTANCE = GWT.create( ShowcaseResources.class );

    ShowcaseImages images();
}
