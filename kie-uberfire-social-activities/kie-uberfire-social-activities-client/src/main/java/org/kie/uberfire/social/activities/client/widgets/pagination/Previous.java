package org.kie.uberfire.social.activities.client.widgets.pagination;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.html.Span;
import org.gwtbootstrap3.client.ui.html.Text;

/**
 * PatternFly pager previous link style
 * See more https://www.patternfly.org/widgets/#pagination
 * Created by Cristiano Nicolai.
 */
public class Previous extends ListItem {

    private final Anchor previousAnchor = new Anchor();
    private final Span previousIcon = new Span();

    public Previous() {
        add( previousAnchor );
        previousIcon.addStyleName( "i" );
        previousIcon.addStyleName( "fa" );
        previousIcon.addStyleName( "fa-angle-left" );
        setText( "Previous" );
        addStyleName( "previous" );
    }

    public void setText( final String text ) {
        previousAnchor.clear();
        previousAnchor.add( previousIcon );
        previousAnchor.add( new Text( text ) );
    }
}
