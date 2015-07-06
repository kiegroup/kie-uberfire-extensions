package org.kie.uberfire.social.activities.events;

import java.io.Serializable;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.kie.uberfire.social.activities.model.SocialUser;

/**
 * Created by Cristiano Nicolai.
 */
@Portable
public class SocialUserFollowedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private final SocialUser user;
    private final SocialUser follow;

    public SocialUserFollowedEvent( @MapsTo( "user" ) SocialUser user, @MapsTo( "follow" ) SocialUser follow ) {
        this.user = user;
        this.follow = follow;
    }

    public SocialUser getFollow() {
        return follow;
    }

    public SocialUser getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "SocialUserFollowedEvent{" +
                "user=" + user +
                ", follow=" + follow +
                '}';
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        SocialUserFollowedEvent that = (SocialUserFollowedEvent) o;

        if ( !user.equals( that.user ) ) return false;
        return follow.equals( that.follow );

    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + follow.hashCode();
        return result;
    }
}
