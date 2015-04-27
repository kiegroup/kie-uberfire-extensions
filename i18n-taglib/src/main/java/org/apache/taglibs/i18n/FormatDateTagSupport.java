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
import java.util.Date;

/** Abstract base class which supports the defaulting of the value to 'now' 
  * if no other value is specified. 
  *
  * @author James Strachan
  * @version $Revision: 216689 $
  */
public abstract class FormatDateTagSupport extends FormatTagSupport  {

    private boolean initialised;
    
    public void release() {
        super.release();
        initialised = false;
    }
    
    // Properties
    //-------------------------------------------------------------------------
    public Object getValue() {
        if ( ! initialised ) {
            // default to 'now' if no value has been specified
            super.setValue( new Date() );
            initialised = true;
        }
        return super.getValue();
    }
    
    public void setValue( Object value ) {
        super.setValue( value );
        initialised = true;
    }
}
 
