package org.kie.uberfire.social.activities.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.uberfire.backend.server.io.ConfigIOServiceProducer;
import org.uberfire.java.nio.base.AbstractPath;
import org.uberfire.java.nio.file.FileSystem;
import org.uberfire.java.nio.file.Path;

//this type can't be managed bean, otherwise WAS will fail
//https://bugzilla.redhat.com/show_bug.cgi?id=1266138
public class SocialUserServicesExtendedBackEndImpl {

    private FileSystem fileSystem;

    public SocialUserServicesExtendedBackEndImpl( final FileSystem fileSystem ) {
        this.fileSystem = fileSystem;
    }

    public List<String> getAllBranches() {
        final List<String> branches = new ArrayList<String>();
        FileSystem _fileSystem = ConfigIOServiceProducer.getInstance().configFileSystem();
        if ( _fileSystem == null ) {
            _fileSystem = fileSystem;
        }
        for ( Iterator it = _fileSystem.getRootDirectories().iterator(); it.hasNext(); ) {
            AbstractPath path = (AbstractPath) it.next();
            branches.add( path.getHost() );
        }
        return branches;
    }

    public Path buildPath( final String serviceType,
                           final String relativePath ) {
        FileSystem _fileSystem = ConfigIOServiceProducer.getInstance().configFileSystem();
        if ( _fileSystem == null ) {
            _fileSystem = fileSystem;
        }
        if ( relativePath != null && !"".equals( relativePath ) ) {
            return _fileSystem.getPath( "social", serviceType, relativePath );
        } else {
            return _fileSystem.getPath( "social", serviceType );
        }
    }
}
