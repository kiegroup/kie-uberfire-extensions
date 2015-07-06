package org.kie.uberfire.social.activities.client.user;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.Image;
import org.kie.uberfire.social.activities.client.gravatar.GravatarBuilder;
import org.kie.uberfire.social.activities.model.SocialUser;
import org.kie.uberfire.social.activities.service.SocialUserImageRepositoryAPI;
import org.kie.uberfire.social.activities.service.SocialUserImageRepositoryAPI.ImageSize;

/**
 * Created by Cristiano Nicolai.
 */
@ApplicationScoped
public class SocialUserImageProviderImpl implements SocialUserImageProvider {

    @Inject
    Instance<SocialUserImageRepositoryAPI> imageRepository;


    @Override
    public Image getImageForSocialUser( final SocialUser socialUser, final ImageSize imageSize ) {
        if ( imageRepository.isUnsatisfied() ) {
            return createDefaultImage( socialUser, imageSize );
        } else {
            final String url = imageRepository.get().imageUrlForSocialUser( socialUser, imageSize );
            if ( url == null ) {
                return createDefaultImage( socialUser, imageSize );
            } else {
                return new Image( url );
            }
        }
    }

    private Image createDefaultImage( final SocialUser socialUser, final ImageSize imageSize ) {
        return GravatarBuilder.generate( socialUser, imageSize );
    }

}
