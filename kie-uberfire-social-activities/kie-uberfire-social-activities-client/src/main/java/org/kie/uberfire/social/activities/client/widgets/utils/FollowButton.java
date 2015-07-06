package org.kie.uberfire.social.activities.client.widgets.utils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.uberfire.mvp.Command;

/**
 * Created by Cristiano Nicolai.
 */
public class FollowButton extends Button {

    public enum FollowType {FOLLOW, UNFOLLOW}

    private final FollowType followType;

    public FollowButton( final FollowType followType, final Command command ) {
        this.followType = followType;
        setType( ButtonType.INFO );
        setSize( ButtonSize.SMALL );
        if ( command != null ) {
            addClickHandler( new ClickHandler() {
                @Override
                public void onClick( ClickEvent event ) {
                    command.execute();
                }
            } );
        }
        setText( followType == FollowType.FOLLOW ? "Follow" : "Unfollow" );
    }

}
