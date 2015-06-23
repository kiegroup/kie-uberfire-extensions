/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.kie.uberfire.social.activities.client.gravatar;

import com.github.gwtbootstrap.client.ui.Image;
import com.google.gwt.resources.client.ImageResource;
import org.kie.uberfire.social.activities.client.AppResource;
import org.kie.uberfire.social.activities.model.SocialUser;

public class GravatarBuilder {

    public enum SIZE {
        SMALL, BIG, MICRO
    }

    public static Image generate( SocialUser socialUser,
                                  SIZE size ) {
        if ( socialUser.getEmail().isEmpty() ) {
            return generateDefaultImage( size );
        } else {
            return generateGravatarImage( socialUser, size );
        }
    }

    private static Image generateGravatarImage( SocialUser socialUser,

                                                SIZE size ) {
        Image gravatarImage;
        if ( size == SIZE.MICRO ) {
            gravatarImage = new Image( new GravatarImage( socialUser.getEmail(), 15 ).getUrl() );
        } else if ( size == SIZE.SMALL ) {
            gravatarImage = new Image( new GravatarImage( socialUser.getEmail(), 30 ).getUrl() );
        } else   {
            gravatarImage = new Image( new GravatarImage( socialUser.getEmail(), 200 ).getUrl() );
        }

        return gravatarImage;
    }

    private static Image generateDefaultImage( SIZE size ) {
        Image userImage;
        if ( size == SIZE.MICRO ) {
            ImageResource imageResource = AppResource.INSTANCE.images().genericAvatar15px();
            userImage = new Image( imageResource );
        } else if ( size == SIZE.SMALL ) {
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
