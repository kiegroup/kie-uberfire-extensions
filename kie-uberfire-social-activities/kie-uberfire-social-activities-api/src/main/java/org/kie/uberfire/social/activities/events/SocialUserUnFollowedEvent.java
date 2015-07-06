package org.kie.uberfire.social.activities.events;

import java.io.Serializable;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.kie.uberfire.social.activities.model.SocialUser;

/**
 * Created by Cristiano Nicolai.
 */
@Portable
public class SocialUserUnFollowedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private final SocialUser user;
    private final SocialUser unfollow;

    public SocialUserUnFollowedEvent( @MapsTo( "user" ) final SocialUser user, @MapsTo( "unfollow" ) final SocialUser unfollow ) {
        this.user = user;
        this.unfollow = unfollow;
    }

    public SocialUser getUnfollow() {
        return unfollow;
    }

    public SocialUser getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "SocialUserUnFollowedEvent{" +
                "user=" + user +
                ", unfollow=" + unfollow +
                '}';
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        SocialUserUnFollowedEvent that = (SocialUserUnFollowedEvent) o;

        if ( user != null ? !user.equals( that.user ) : that.user != null ) return false;
        return !( unfollow != null ? !unfollow.equals( that.unfollow ) : that.unfollow != null );

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + ( unfollow != null ? unfollow.hashCode() : 0 );
        return result;
    }
}
