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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.ServletRequest;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 *  This class is used by the bundle and message tags for caching the
 *  ResourceBundle in the session and request.
 *
 *  @author <a href="mailto:tdawson@wamnet.com">Tim Dawson</a>
 *
 */
public class ResourceHelper
{
    public static final String BUNDLE_REQUEST_KEY =
          "org.apache.taglibs.i18n.ResourceHelper.Bundle";

    /**
     *  Retrieves the bundle cached in the page by a previous call to
     *  getBundle(PageContext,String).  This is used by the MessageTag since
     *  the bundle name is provided once, by the BundleTag.
     *  @return java.util.ResourceBundle the cached bundle
     */
    static public ResourceBundle getBundle(PageContext pageContext)
    {
        return (ResourceBundle)pageContext.findAttribute(BUNDLE_REQUEST_KEY);
    }

    /**
     *  Caches the Bundle in the request using the BUNDLE_REQUEST_KEY.
     */
    static public void setBundle(PageContext pageContext,
                                 ResourceBundle bundle,
                                 int scope)
    {
        // put this in the page so that the getMessage tag can get it quickly
        pageContext.setAttribute(BUNDLE_REQUEST_KEY,bundle,scope);
    }
  
}
