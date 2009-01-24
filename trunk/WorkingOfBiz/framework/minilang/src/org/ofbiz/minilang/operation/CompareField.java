/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.ofbiz.minilang.operation;

import java.util.*;
import org.w3c.dom.*;

/**
 * Compares the current field to another field
 */
public class CompareField extends BaseCompare {
    
    String compareName;

    public CompareField(Element element, SimpleMapProcess simpleMapProcess) {
        super(element, simpleMapProcess);
        this.compareName = element.getAttribute("field");
    }

    public void exec(Map inMap, Map results, List messages, Locale locale, ClassLoader loader) {
        Object compareValue = inMap.get(compareName);
        Object fieldValue = inMap.get(fieldName);

        doCompare(fieldValue, compareValue, messages, locale, loader, false);
    }
}