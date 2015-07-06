package org.kie.uberfire.social.activities.client.widgets.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.ImageAnchor;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.gwtbootstrap3.client.ui.html.Text;
import org.jboss.errai.ioc.client.container.IOC;
import org.kie.uberfire.social.activities.client.user.SocialUserImageProvider;
import org.kie.uberfire.social.activities.client.widgets.timeline.regular.model.UpdateItem;
import org.kie.uberfire.social.activities.model.SocialActivitiesEvent;
import org.kie.uberfire.social.activities.model.SocialUser;
import org.kie.uberfire.social.activities.service.SocialUserImageRepositoryAPI.ImageSize;

public class CommentRowWidget extends Composite {

    private final static DateTimeFormat FORMATTER = DateTimeFormat.getFormat( "dd/MM/yyyy HH:mm:ss" );

    private static MyUiBinder uiBinder = GWT.create( MyUiBinder.class );

    @UiField
    FlowPanel left;

    @UiField
    Paragraph desc;

    @UiField
    Heading heading;

    SocialUserImageProvider imageProvider;

    interface MyUiBinder extends UiBinder<Widget, CommentRowWidget> {

    }

    public CommentRowWidget() {
        imageProvider = IOC.getBeanManager().lookupBean( SocialUserImageProvider.class ).getInstance();
    }

    public void init( UpdateItem model ) {
        initWidget( uiBinder.createAndBindUi( this ) );
        createItem( model );
    }

    public void createItem( UpdateItem updateItem ) {
        createThumbNail( updateItem );
        createAdditionalInfo( updateItem.getEvent() );
        createUserInfo( updateItem.getEvent().getSocialUser() );
    }

    private void createUserInfo( final SocialUser socialUser ) {
        heading.setText( socialUser.getName() );
    }

    private void createAdditionalInfo( SocialActivitiesEvent event ) {
        final StringBuilder comment = new StringBuilder();
        comment.append( event.getAdicionalInfos() );
        comment.append( " " );
        comment.append( FORMATTER.format( event.getTimestamp() ) );
        comment.append( " " );
        if ( event.getDescription() != null && !event.getDescription().isEmpty() ) {
            comment.append( "\"" + event.getDescription() + "\"" );
        }
        desc.add( new Text( comment.toString() ) );
    }

    private void createThumbNail( UpdateItem updateItem ) {
        final SocialUser socialUser = updateItem.getEvent().getSocialUser();
        final Image userImage = imageProvider.getImageForSocialUser( socialUser, ImageSize.SMALL );
        final ImageAnchor newImage = new ImageAnchor();
        newImage.setUrl( userImage.getUrl() );
        newImage.setAsMediaObject( true );
        left.add( newImage );
    }

}
