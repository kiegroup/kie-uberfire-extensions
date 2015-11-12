package org.kie.uberfire.social.activities.persistence;

import org.junit.Before;
import org.junit.Test;
import org.uberfire.commons.lifecycle.PriorityDisposableRegistry;

import static org.junit.Assert.*;

public class SocialTimelineCacheInstancePersistenceTest {

    SocialTimelineCacheInstancePersistence.SocialCacheControl cacheControl;
    Integer threshold;

    @Before
    public void setup() {
        //default
        threshold = 100;
        final SocialTimelineCacheInstancePersistence cacheInstancePersistence = new SocialTimelineCacheInstancePersistenceUnitTestWrapper(  );
        cacheControl = cacheInstancePersistence.new SocialCacheControl();
        assertTrue( PriorityDisposableRegistry.getDisposables().contains( cacheInstancePersistence ) );
    }

    @Test
    public void socialCacheControlTest() {
        assertFalse( cacheControl.needToPersist() );
        registerEvents( threshold + 1 );
        assertTrue( cacheControl.needToPersist() );
        cacheControl.reset();
        assertFalse( cacheControl.needToPersist() );
    }

    private void registerEvents( int numberOfEvents ) {
        for ( int i = 0; i < numberOfEvents;i++){
            cacheControl.registerNewEvent();
            cacheControl.registerNewEvent();
            cacheControl.registerNewEvent();
        }
    }
}
