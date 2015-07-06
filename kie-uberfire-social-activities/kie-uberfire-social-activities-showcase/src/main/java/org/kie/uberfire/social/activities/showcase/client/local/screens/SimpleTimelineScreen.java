/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.uberfire.social.activities.showcase.client.local.screens;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import org.gwtbootstrap3.client.ui.Button;
import org.jboss.errai.security.shared.api.identity.User;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.kie.uberfire.social.activities.client.widgets.item.model.LinkCommandParams;
import org.kie.uberfire.social.activities.client.widgets.pagination.Next;
import org.kie.uberfire.social.activities.client.widgets.pagination.Previous;
import org.kie.uberfire.social.activities.client.widgets.timeline.simple.SimpleSocialTimelineWidget;
import org.kie.uberfire.social.activities.client.widgets.timeline.simple.model.SimpleSocialTimelineWidgetModel;
import org.kie.uberfire.social.activities.model.DefaultTypes;
import org.kie.uberfire.social.activities.model.SocialActivitiesEvent;
import org.kie.uberfire.social.activities.model.SocialPaged;
import org.kie.uberfire.social.activities.showcase.shared.ShowcaseSocialUserEvent;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.mvp.ParameterizedCommand;

@WorkbenchScreen( identifier = "SimpleTimelineScreen" )
@Dependent
@Templated
public class SimpleTimelineScreen extends Composite {

    @DataField
    @Inject
    FlowPanel timeline;

    @DataField
    @Inject
    Button newevent;

    @Inject
    private PlaceManager placeManager;

    @Inject
    private User loggedUser;

    @Inject
    private Event<ShowcaseSocialUserEvent> event;

    @PostConstruct
    public void setup() {
        updateTimeline();
    }

    @EventHandler( "newevent" )
    protected void onNewEvent( final ClickEvent clickEvent ) {
        event.fire( new ShowcaseSocialUserEvent( loggedUser.getIdentifier() ) );
        newevent.setFocus( false );
    }

    protected void onShowcaseSocialUserEvent( @Observes SocialActivitiesEvent event ) {
        updateTimeline();
    }

    private void updateTimeline() {
        timeline.clear();
        final SimpleSocialTimelineWidgetModel model = new SimpleSocialTimelineWidgetModel( DefaultTypes.DUMMY_EVENT, null, placeManager, new SocialPaged( 5 ) );
        model.withPagination( new Previous(), new Next() );
        model.withLinkCommand( new ParameterizedCommand<LinkCommandParams>() {
            @Override
            public void execute( LinkCommandParams parameter ) {
                Window.alert( "Link command!" );
            }
        } );
        final SimpleSocialTimelineWidget socialTimelineWidget = new SimpleSocialTimelineWidget( model );
        timeline.add( socialTimelineWidget );
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Social Simple Timeline";
    }

}