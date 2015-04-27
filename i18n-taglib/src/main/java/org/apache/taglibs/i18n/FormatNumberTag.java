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

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Locale;

/** Formats a {@link Number} instance using a {@link java.util.Locale} and the 
  * {@link NumberFormat} or a {@link DecimalFormat} if a pattern is specified.
  *
  * @author James Strachan
  * @version $Revision: 216689 $
  */
public class FormatNumberTag extends FormatTagSupport {
    
    protected static final String _tagname = "i18n:formatNumber";

    private NumberFormat format;
    private String pattern;


    // Tag interface
    //-------------------------------------------------------------------------
    public void release() {
        super.release();
        format = null;
        pattern = null;
    }
    
    // Properties
    //-------------------------------------------------------------------------
    
    /** If no {@link NumberFormat} is configured then use a 
      * {@link DecimalFormat} instance if a pattern is configured else
      * use the default NumberFormat for the {@link java.util.Locale}
      */
    public Format getFormat() {
        if ( format == null ) {
            String pattern = getPattern();
            if ( pattern != null ) {
                format = getPatternFormat( pattern );
            }
            if ( pattern == null ) {
                format = getNumberFormat();
            }
        }
        return format;
    }

    public void setFormat( NumberFormat format ) {
        this.format = format;
    }

    public String getPattern() {
        return pattern;
    }
    
    public void setPattern( String pattern ) {
        this.pattern = pattern;
    }

    // Implementation methods
    //-------------------------------------------------------------------------
    protected NumberFormat getPatternFormat( String pattern ) {
        // XXXX: Use a Locale based cache for these objects as per Craig's 
        // XXXX: suggestion        
        return new DecimalFormat( pattern, new DecimalFormatSymbols( getLocale() ) );
    }
    
    /** @return the default number formatter for the current Locale 
      */
    protected NumberFormat getNumberFormat() {
        return NumberFormat.getInstance( getLocale() );
        // XXXX: what's the difference with the following:=
        // return NumberFormat.getNumberInstance( getLocale() );
    }
    
}
