package org.kie.uberfire.social.activities.showcase.client.local.screens;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.kie.uberfire.social.activities.client.widgets.relations.SocialRelationsWidget;
import org.kie.uberfire.social.activities.events.SocialUserFollowedEvent;
import org.kie.uberfire.social.activities.events.SocialUserUnFollowedEvent;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchScreen;

/**
 * Created by Cristiano Nicolai.
 */
@WorkbenchScreen( identifier = "SocialRelations" )
@Dependent
@Templated
public class SocialRelationsScreen extends Composite {

    @Inject
    private SocialRelationsWidget socialRelationsWidget;

    @DataField
    @Inject
    SimplePanel panel;

    @PostConstruct
    public void setup() {
        panel.add( socialRelationsWidget );
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Social Relations";
    }

    protected void onFollow( @Observes SocialUserFollowedEvent event ) {
        socialRelationsWidget.updateWidget();
    }

    protected void onUnFollow( @Observes SocialUserUnFollowedEvent event ) {
        socialRelationsWidget.updateWidget();
    }
}
