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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import org.gwtbootstrap3.client.ui.Button;
import org.jboss.errai.security.shared.api.identity.User;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.kie.uberfire.social.activities.client.widgets.timeline.regular.SocialTimelineWidget;
import org.kie.uberfire.social.activities.client.widgets.timeline.regular.model.SocialTimelineWidgetModel;
import org.kie.uberfire.social.activities.model.DefaultTypes;
import org.kie.uberfire.social.activities.model.SocialActivitiesEvent;
import org.kie.uberfire.social.activities.model.SocialUser;
import org.kie.uberfire.social.activities.showcase.shared.ShowcaseSocialUserEvent;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.mvp.PlaceManager;

@WorkbenchScreen( identifier = "TimelineScreen" )
@Dependent
@Templated
public class TimelineScreen extends Composite {

    @DataField
    @Inject
    SimplePanel timeline;

    @DataField
    @Inject
    Button newevent;

    @Inject
    private PlaceManager placeManager;

    @Inject
    private User loggedUser;

    @Inject
    private Event<ShowcaseSocialUserEvent> socialUserEvent;

    private SocialUser currentUser;

    @PostConstruct
    public void setup() {
        currentUser = new SocialUser( loggedUser.getIdentifier() );
        updateTimeline();
    }

    @EventHandler( "newevent" )
    protected void onNewEvent( final ClickEvent event ) {
        socialUserEvent.fire( new ShowcaseSocialUserEvent( loggedUser.getIdentifier() ) );
        newevent.setFocus( false );
    }

    protected void onShowcaseSocialUserEvent( @Observes SocialActivitiesEvent event ) {
        updateTimeline();
    }

    private void updateTimeline() {
        timeline.clear();
        final SocialTimelineWidgetModel model = new SocialTimelineWidgetModel( DefaultTypes.DUMMY_EVENT, currentUser, placeManager ).droolsQuery( null, "anyrule", null );
        final SocialTimelineWidget socialTimelineWidget = new SocialTimelineWidget();
        socialTimelineWidget.init( model );
        timeline.add( socialTimelineWidget );
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Social Timeline";
    }

}