package org.kie.uberfire.social.activities.utils;

import java.util.Comparator;

import org.kie.uberfire.social.activities.model.SocialUser;

/**
 * Created by Cristiano Nicolai.
 */
public class SocialUserNameComparator implements Comparator<SocialUser> {

    @Override
    public int compare( final SocialUser u1, final SocialUser u2 ) {
        return u1.getName().compareTo( u2.getName() );
    }

}
