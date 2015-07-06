package org.kie.uberfire.social.activities.client.user;

import com.google.gwt.user.client.ui.Image;
import org.kie.uberfire.social.activities.model.SocialUser;
import org.kie.uberfire.social.activities.service.SocialUserImageRepositoryAPI.ImageSize;

/**
 * Created by Cristiano Nicolai.
 */
public interface SocialUserImageProvider {

    Image getImageForSocialUser( SocialUser socialUser, ImageSize imageSize );

}
