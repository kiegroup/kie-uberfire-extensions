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

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;


/**
 * Defines the possible JSP beans available within the the body
 * of the iterator tag.
 */
public class BundleTEI extends TagExtraInfo
{

    /**
     * Returns two VariableInfo objects that define the following
     * body objects
     * <UL>
     * <LI>A variable that represents the current value of the iterator.
     * The name to reference this object is defined in the iterator tag
     * with the value "varName". The type of this object is defined by
     * the "varType" value passed in the iterator tag.
     * </UL>
     *
     * @param data a value of type 'TagData'
     * @return a value of type 'VariableInfo[]'
     */
    public VariableInfo[] getVariableInfo( TagData data )
    {
        String varName = data.getAttributeString("id");
        if ( varName == null ) {
            return new VariableInfo[] {
            };
        } else {
            return new VariableInfo[] {
                new VariableInfo( varName,
                                  "java.util.ResourceBundle",
                                  true,
                                  VariableInfo.AT_BEGIN ),
            };
        }
    }

}
