package org.kie.uberfire.social.activities.client.widgets.pagination;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.html.Span;
import org.gwtbootstrap3.client.ui.html.Text;

/**
 * PatternFly pager next link style
 * See more https://www.patternfly.org/widgets/#pagination
 * Created by Cristiano Nicolai.
 */
public class Next extends ListItem {

    private final Anchor nextAnchor = new Anchor();
    private final Span nextIcon = new Span();

    public Next() {
        nextIcon.addStyleName( "i" );
        nextIcon.addStyleName( "fa" );
        nextIcon.addStyleName( "fa-angle-right" );
        setText( "Next" );
        add( nextAnchor );
        addStyleName( "next" );
    }

    public void setText( final String text ) {
        nextAnchor.clear();
        nextAnchor.add( new Text( text ) );
        nextAnchor.add( nextIcon );
    }
}
