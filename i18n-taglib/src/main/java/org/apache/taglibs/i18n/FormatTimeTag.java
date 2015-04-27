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

/** Formats a {@link java.util.Date} instance using a {@link java.util.Locale} and the 
  * default time format.
  *
  * @author James Strachan
  * @version $Revision: 216689 $
  */
public class FormatTimeTag extends FormatDateTagSupport  {

    protected static final String _tagname = "i18n:formatTime";

    private int style = DateFormat.SHORT;

    // Properties
    //-------------------------------------------------------------------------
    public void setStyle( String style ) {
        this.style = getStyleCode( style );
    }
    
    // Implementation methods
    //-------------------------------------------------------------------------
    protected Format getFormat() {
        return DateFormat.getTimeInstance( style, getLocale() );
    }  
}
 
