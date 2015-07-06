/*
 * Copyright 2012 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kie.uberfire.social.activities.showcase.client.local.perspectives;

import javax.enterprise.context.ApplicationScoped;

import org.uberfire.client.annotations.Perspective;
import org.uberfire.client.annotations.WorkbenchPerspective;
import org.uberfire.client.workbench.panels.impl.SimpleWorkbenchPanelPresenter;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.uberfire.workbench.model.CompassPosition;
import org.uberfire.workbench.model.PanelDefinition;
import org.uberfire.workbench.model.PerspectiveDefinition;
import org.uberfire.workbench.model.impl.PanelDefinitionImpl;
import org.uberfire.workbench.model.impl.PartDefinitionImpl;
import org.uberfire.workbench.model.impl.PerspectiveDefinitionImpl;

@ApplicationScoped
@WorkbenchPerspective( identifier = "Social", isDefault = true )
public class SocialPerspective {

    @Perspective
    public PerspectiveDefinition buildPerspective() {
        final PerspectiveDefinition p = new PerspectiveDefinitionImpl( SimpleWorkbenchPanelPresenter.class.getName() );
        p.setName( "Social" );
        p.getRoot().addPart( new PartDefinitionImpl( new DefaultPlaceRequest( "UserConnectionsScreen" ) ) );

        final PanelDefinition west = new PanelDefinitionImpl( SimpleWorkbenchPanelPresenter.class.getName() );
        west.setWidth( 350 );
        west.setMinWidth( 350 );
        west.addPart( new PartDefinitionImpl( new DefaultPlaceRequest( "SimpleTimelineScreen" ) ) );
        p.getRoot().insertChild( CompassPosition.WEST, west );

        final PanelDefinition east = new PanelDefinitionImpl( SimpleWorkbenchPanelPresenter.class.getName() );
        east.setWidth( 350 );
        east.setMinWidth( 350 );
        east.addPart( new PartDefinitionImpl( new DefaultPlaceRequest( "TimelineScreen" ) ) );
        p.getRoot().insertChild( CompassPosition.EAST, east );

        final PanelDefinition south = new PanelDefinitionImpl( SimpleWorkbenchPanelPresenter.class.getName() );
        south.setHeight( 300 );
        south.setMinHeight( 300 );
        south.addPart( new PartDefinitionImpl( new DefaultPlaceRequest( "SocialRelations" ) ) );
        p.getRoot().insertChild( CompassPosition.SOUTH, south );

        return p;
    }

}
