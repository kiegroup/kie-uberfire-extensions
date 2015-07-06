package org.kie.uberfire.social.activities.client.gravatar;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import org.kie.uberfire.social.activities.client.AppResource;
import org.kie.uberfire.social.activities.model.SocialUser;
import org.kie.uberfire.social.activities.service.SocialUserImageRepositoryAPI.ImageSize;

public class GravatarBuilder {

    public static Image generate( SocialUser socialUser,
                                  ImageSize imageSize ) {
        if ( socialUser.getEmail().isEmpty() ) {
            return generateDefaultImage( imageSize );
        } else {
            return generateGravatarImage( socialUser, imageSize );
        }
    }

    private static Image generateGravatarImage( SocialUser socialUser,

                                                ImageSize imageSize ) {
        Image gravatarImage;
        if ( imageSize == ImageSize.MICRO ) {
            gravatarImage = new Image( new GravatarImage( socialUser.getEmail(), 15 ).getUrl() );
        } else if ( imageSize == ImageSize.SMALL ) {
            gravatarImage = new Image( new GravatarImage( socialUser.getEmail(), 30 ).getUrl() );
        } else {
            gravatarImage = new Image( new GravatarImage( socialUser.getEmail(), 200 ).getUrl() );
        }

        return gravatarImage;
    }

    private static Image generateDefaultImage( ImageSize size ) {
        Image userImage;
        if ( size == ImageSize.MICRO ) {
            ImageResource imageResource = AppResource.INSTANCE.images().genericAvatar15px();
            userImage = new Image( imageResource );
        } else if ( size == ImageSize.SMALL ) {
            ImageResource imageResource = AppResource.INSTANCE.images().genericAvatar30px();
            userImage = new Image( imageResource );

        } else {
            ImageResource imageResource = AppResource.INSTANCE.images().genericAvatar();
            userImage = new Image( imageResource );
            return userImage;
        }
        return userImage;
    }
}
