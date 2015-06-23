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

package org.kie.uberfire.social.activities.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.uberfire.java.nio.base.AbstractPath;
import org.uberfire.java.nio.file.FileSystem;
import org.uberfire.java.nio.file.Path;

@ApplicationScoped
public class SocialUserServicesExtendedBackEndImpl {

    @Inject
    @Named("systemFS")
    private FileSystem fileSystem;

    public List<String> getAllBranches() {
        List<String> branches = new ArrayList<String>();
        for ( Iterator it = fileSystem.getRootDirectories().iterator(); it.hasNext(); ) {
            AbstractPath path = (AbstractPath) it.next();
            branches.add( path.getHost() );
        }
        return branches;
    }

    public Path buildPath( final String serviceType,
                           final String relativePath ) {
        if ( relativePath != null && !"".equals( relativePath ) ) {
            return fileSystem.getPath( "social", serviceType, relativePath );
        } else {
            return fileSystem.getPath( "social", serviceType );
        }
    }
}
