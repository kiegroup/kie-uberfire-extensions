package org.kie.uberfire.social.activities.client.widgets.timeline.regular;

import java.util.List;
import java.util.Map;

import com.github.gwtbootstrap.client.ui.Container;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.kie.uberfire.social.activities.client.widgets.item.SimpleItemWidget;
import org.kie.uberfire.social.activities.client.widgets.item.SocialItemExpandedWidget;
import org.kie.uberfire.social.activities.client.widgets.item.model.SimpleItemWidgetModel;
import org.kie.uberfire.social.activities.client.widgets.item.model.SocialItemExpandedWidgetModel;
import org.kie.uberfire.social.activities.client.widgets.timeline.regular.model.RecentUpdatesModel;
import org.kie.uberfire.social.activities.client.widgets.timeline.regular.model.SocialTimelineWidgetModel;
import org.kie.uberfire.social.activities.client.widgets.timeline.regular.model.UpdateItem;
import org.kie.uberfire.social.activities.model.SocialActivitiesEvent;
import org.kie.uberfire.social.activities.service.SocialTimeLineRepositoryAPI;
import org.kie.uberfire.social.activities.service.SocialTimelineRulesQueryAPI;
import org.uberfire.backend.vfs.Path;
import org.uberfire.backend.vfs.VFSService;

public class SocialTimelineWidget extends Composite {

    @UiField
    Container itemsPanel;

    private SocialTimelineWidgetModel model;

    public void init( SocialTimelineWidgetModel model ) {
        this.model = model;
        initWidget( uiBinder.createAndBindUi( this ) );
        if ( model.isDroolsQuery() ) {
            createDroolsQuerySocialItemsWidget( model );
        } else {
            createRegularQuerySocialItemsWidget( model );
        }
    }

    private void createRegularQuerySocialItemsWidget( final SocialTimelineWidgetModel model ) {

        MessageBuilder.createCall( new RemoteCallback<List<SocialActivitiesEvent>>() {
            public void callback( List<SocialActivitiesEvent> events ) {

                if ( events.isEmpty() ) {
                    displayNoEvents();
                } else {
                    createEventsWidget( events, model );
                }
            }
        }, SocialTimeLineRepositoryAPI.class ).getLastEventTimeline( model.getSocialEventType().name() );
    }

    private void createEventsWidget( List<SocialActivitiesEvent> events,
                                     final SocialTimelineWidgetModel model ) {
        for ( final SocialActivitiesEvent event : events ) {
            if ( event.hasLink() ) {
                createSimpleWidgetWithLink( event );                 ;
            } else {
                createSimpleWidget( event );
            }
        }
    }

    private void createSimpleWidgetWithLink( final SocialActivitiesEvent event ) {

        final SimpleItemWidgetModel itemModel = new SimpleItemWidgetModel(model, event.getType(),
                event.getTimestamp(),
                event.getLinkLabel(),
                event.getLinkTarget(),
                event.getLinkType(),
                event.getDescription(),
                event.getAdicionalInfos(),
                model.getSocialUser())
                .withLinkCommand( model.getLinkCommand() )
                .withLinkParams( event.getLinkParams() );

        if ( event.isVFSLink() ) {
            MessageBuilder.createCall( new RemoteCallback<Path>() {
                public void callback( Path path ) {
                    itemModel.withLinkPath( path );
                    addItemWidget( itemModel );

                }
            }, VFSService.class ).get( event.getLinkTarget() );
        } else {
            addItemWidget( itemModel );
        }
    }

    private void createSimpleWidget( SocialActivitiesEvent event ) {
        SimpleItemWidgetModel rowModel = new SimpleItemWidgetModel( model, event.getType(),
                event.getTimestamp(),
                event.getDescription(),
                event.getAdicionalInfos() )
                .withLinkParams( event.getLinkParams() );

        addItemWidget( rowModel );
    }

    private void addItemWidget( SimpleItemWidgetModel model ) {
        SimpleItemWidget simple = GWT.create( SimpleItemWidget.class );
        simple.init( model );
        itemsPanel.add( simple );
    }

    private void createDroolsQuerySocialItemsWidget( final SocialTimelineWidgetModel model ) {
        MessageBuilder.createCall( new RemoteCallback<List<SocialActivitiesEvent>>() {
            public void callback( List<SocialActivitiesEvent> events ) {
                RecentUpdatesModel recentUpdatesModel = RecentUpdatesModel.generate( events );
                Map<String, List<UpdateItem>> updateItems = recentUpdatesModel.getUpdateItems();
                if ( updateItems.keySet().isEmpty() ) {
                    displayNoEvents();
                } else {
                    createExpandedItemsWidget( recentUpdatesModel, updateItems, model );
                }
            }
        }, SocialTimelineRulesQueryAPI.class ).executeSpecificRule( model.getGlobals(), model.getDrlName(), model.getMaxResults() );
    }

    private void createExpandedItemsWidget( RecentUpdatesModel recentUpdatesModel,
                                            Map<String, List<UpdateItem>> updateItems,
                                            SocialTimelineWidgetModel model ) {
        for ( final String fileName : updateItems.keySet() ) {
            SocialItemExpandedWidget widget = GWT.create( SocialItemExpandedWidget.class );
            widget.init( new SocialItemExpandedWidgetModel( fileName, recentUpdatesModel.getUpdateItems( fileName ), model ) );
            itemsPanel.add( widget );
        }
    }

    private void displayNoEvents() {
        itemsPanel.add( new Paragraph( "There are no social events...yet!" ) );
    }

    interface MyUiBinder extends UiBinder<Widget, SocialTimelineWidget> {

    }

    private static MyUiBinder uiBinder = GWT.create( MyUiBinder.class );

}
