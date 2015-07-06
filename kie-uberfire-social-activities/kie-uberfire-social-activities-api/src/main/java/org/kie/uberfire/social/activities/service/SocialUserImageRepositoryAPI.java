package org.kie.uberfire.social.activities.service;

import org.kie.uberfire.social.activities.model.SocialUser;

public interface SocialUserImageRepositoryAPI {

    enum ImageSize {
        SMALL, BIG, MICRO
    }

    /**
     * Return the url containing either the location of the image or base 64 encoded image.
     * @param user
     * @param imageSize
     * @return
     */
    String imageUrlForSocialUser( SocialUser user, ImageSize imageSize );

}
