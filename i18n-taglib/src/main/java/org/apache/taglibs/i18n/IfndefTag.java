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

import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 *  This class implements body tag that allows you to use a resource bundle
 *  to internationalize content in a web page. If a value is found in the
 *  resource bundle for the required "key" attribute, then the enclosed JSP
 *  is evaluated, otherwise, it is skipped.
 *  <P>
 *  The ifdef and ifndef tags allow the JSP author to conditionally evaluate
 *  sections of a JSP based on whether or not a value is provided for the
 *  given key.
 *  <P>
 *  <H2>Examples</H2>
 *  <PRE>
 *  &lt;i18n:bundle baseName="test"/&gt;
 *  &lt;i18n:ifndef key="test"&gt;
 *  misc html and jsp
 *  &lt;/i18n:ifndef&gt;
 *  etc...
 *  </PRE>
 *  <P>
 *
 *  @author <a href="mailto:tdawson@wamnet.com">Tim Dawson</a>
 *
 */
public class IfndefTag extends ConditionalTagSupport
{

    protected static final String _tagname = "i18n:ifndef";

    /**
     *  locates the bundle and tests whether the key has a value
     */
    public boolean shouldEvaluate() throws JspException
    {
        String value = this.getValue();
        if ( value == null || value.length() == 0 ) {
            return true;
        } else {
            return false;
        }
    }

}
