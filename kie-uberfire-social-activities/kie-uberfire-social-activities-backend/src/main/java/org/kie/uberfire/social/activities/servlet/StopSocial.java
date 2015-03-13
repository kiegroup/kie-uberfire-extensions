package org.kie.uberfire.social.activities.servlet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.kie.uberfire.social.activities.service.SocialTimelinePersistenceAPI;

public class StopSocial implements ServletContextListener {

    @Inject
    @Named("socialTimelinePersistence")
    private SocialTimelinePersistenceAPI socialTimelinePersistence;

    @Override
    public void contextInitialized( ServletContextEvent ce ) {
    }

    @Override
    public void contextDestroyed( ServletContextEvent ce ) {
        try {
            socialTimelinePersistence.saveAllEvents();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
