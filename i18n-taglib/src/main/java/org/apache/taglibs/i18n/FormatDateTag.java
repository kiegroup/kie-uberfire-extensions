/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package org.apache.taglibs.i18n;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Formats a {@link java.util.Date} instance using a {@link java.util.Locale} and either a 
  * {@link java.text.DateFormat} or a pattern based {@link SimpleDateFormat}.
  *
  * @author James Strachan
  * @version $Revision: 216689 $
  */
public class FormatDateTag extends FormatDateTagSupport {
    
    protected static final String _tagname = "i18n:formatDate";

    private Format format;
    private String pattern;
    private int style = DateFormat.SHORT;


    // Tag interface
    //-------------------------------------------------------------------------
    public void release() {
        super.release();
        format = null;
        pattern = null;
    }
    
    // Properties
    //-------------------------------------------------------------------------
    
    /** If no {@link java.text.DateFormat} is configured then use a 
      * {@link SimpleDateFormat} instance if a pattern is configured else
      * use the default DateFormat for the {@link java.util.Locale}
      */
    public Format getFormat() {
        if ( format == null ) {
            String pattern = getPattern();
            if ( pattern != null ) {
                format = getPatternFormat( pattern );
            }
            if ( pattern == null ) {
                format = getDateFormat();
            }
        }
        return format;
    }

    public void setFormat( DateFormat format ) {
        this.format = format;
    }

    public String getPattern() {
        return pattern;
    }
    
    public void setPattern( String pattern ) {
        this.pattern = pattern;
    }

    public void setStyle( String style ) {
        this.style = getStyleCode( style );
    }
    
    
    // Implementation methods
    //-------------------------------------------------------------------------
    protected DateFormat getPatternFormat( String pattern ) {
        // XXXX: Use a Locale based cache for these objects as per Craig's 
        // XXXX: suggestion        
        return new SimpleDateFormat( pattern, getLocale() );
    }
    
    protected DateFormat getDateFormat() {
        return DateFormat.getDateInstance( style, getLocale() );
    }
    
}
