package org.kie.uberfire.social.activities.showcase.client.local.screens;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.common.client.api.VoidCallback;
import org.jboss.errai.security.shared.api.identity.User;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.kie.uberfire.social.activities.client.widgets.userbox.UserBoxView;
import org.kie.uberfire.social.activities.events.SocialUserFollowedEvent;
import org.kie.uberfire.social.activities.events.SocialUserUnFollowedEvent;
import org.kie.uberfire.social.activities.model.SocialUser;
import org.kie.uberfire.social.activities.service.SocialUserRepositoryAPI;
import org.kie.uberfire.social.activities.service.SocialUserServiceAPI;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.mvp.ParameterizedCommand;

@WorkbenchScreen( identifier = "UserConnectionsScreen" )
@Dependent
@Templated
public class UserConnectionsScreen extends Composite {

    private final AtomicLong count = new AtomicLong();

    @Inject
    private User loggedUser;

    @Inject
    @DataField
    FlowPanel friendsList;

    @Inject
    @DataField
    Button newconnection;

    @DataField
    Heading empty = new Heading( HeadingSize.H4 );

    private final ParameterizedCommand<String> onClick = new ParameterizedCommand<String>() {
        @Override
        public void execute( String parameter ) {
            Window.alert( "Say hello to " + parameter + "!" );
        }
    };

    private SocialUser currentUser;

    private Map<String, UserBoxView> follows = new HashMap<String, UserBoxView>();

    @PostConstruct
    public void init() {
        MessageBuilder.createCall( new RemoteCallback<SocialUser>() {
            public void callback( final SocialUser user ) {
                currentUser = user;
                loadConnections( user );
            }
        }, SocialUserRepositoryAPI.class ).findSocialUser( loggedUser.getIdentifier() );
    }

    @EventHandler( "newconnection" )
    protected void onNewConnection( final ClickEvent event ) {
        newConnection();
        newconnection.setFocus( false );
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Social Connections";
    }

    protected void loadConnections( final SocialUser user ) {
        for ( String follow : user.getFollowingName() ) {
            MessageBuilder.createCall( new RemoteCallback<SocialUser>() {
                public void callback( final SocialUser user ) {
                    newConnection( user );
                }
            }, SocialUserRepositoryAPI.class ).findSocialUser( follow );
        }
    }

    protected void onFollow( @Observes SocialUserFollowedEvent event ) {
        if ( event.getUser().getUserName().equals( currentUser.getUserName() ) ) {
            newConnection( event.getFollow() );
        }
    }

    protected void onUnFollow( @Observes SocialUserUnFollowedEvent event ) {
        if ( event.getUser().getUserName().equals( currentUser.getUserName() ) ) {
            removeUserView( event.getUnfollow().getUserName() );
        }
    }

    private void removeUserView( final String username ) {
        final UserBoxView userBoxView = follows.get( username );
        if ( userBoxView != null ) {
            userBoxView.removeFromParent();
            follows.remove( username );
            if ( friendsList.getWidgetCount() == 0 ) {
                empty.setVisible( true );
            }
        }
    }

    private void newConnection( final SocialUser socialUser ) {
        final ParameterizedCommand<String> followUnfollowCommand = new ParameterizedCommand<String>() {
            @Override
            public void execute( String parameter ) {
                MessageBuilder.createCall( new VoidCallback(), SocialUserServiceAPI.class ).userUnfollowAnotherUser( currentUser.getUserName(), parameter );
            }
        };
        newConnection( socialUser, followUnfollowCommand );
    }

    private void newConnection( final SocialUser socialUser, final ParameterizedCommand<String> followUnfollowCommand ) {
        final UserBoxView userBoxView = GWT.create( UserBoxView.class );
        userBoxView.init( socialUser, UserBoxView.RelationType.UNFOLLOW, onClick, followUnfollowCommand );
        if ( friendsList.getWidgetCount() == 0 ) {
            friendsList.add( userBoxView );
        } else {
            final Optional<Widget> find = Iterables.tryFind( friendsList, new Predicate<Widget>() {
                @Override
                public boolean apply( final Widget widget ) {
                    return socialUser.getUserName().compareToIgnoreCase( ( (UserBoxView) widget ).getUserName() ) < 0;
                }
            } );
            if ( find.isPresent() ) {
                friendsList.insert( userBoxView, friendsList.getWidgetIndex( find.get() ) );
            } else {
                friendsList.add( userBoxView );
            }
        }

        follows.put( socialUser.getUserName(), userBoxView );
        empty.setVisible( false );
    }

    private void newConnection() {
        final ParameterizedCommand<String> followUnfollowCommand = new ParameterizedCommand<String>() {
            @Override
            public void execute( String username ) {
                Window.alert( "Unfollow user " + username + "!" );
                removeUserView( username );
            }
        };
        newConnection( new SocialUser( "user" + count.incrementAndGet() ), followUnfollowCommand );
    }

}
