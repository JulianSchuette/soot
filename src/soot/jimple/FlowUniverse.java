/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-1999 Raja Vallee-Rai
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */





package soot.jimple;

import soot.*;
import soot.util.*;
import java.util.*;

public class FlowUniverse
{
    Object[] indexToObject;
    Map objectToIndex;

    public FlowUniverse(Object[] objects)
    {
        objectToIndex = new HashMap(objects.length * 2 + 1, 0.7f);

        indexToObject = (Object[]) objects.clone();

        for(int i = 0; i < objects.length; i++)
            objectToIndex.put(objects[i], new Integer(i));
    }

    public int getSize()
    {
        return indexToObject.length;
    }

    public Object getObjectOf(int index)
    {
        return indexToObject[index];
    }

    public int getIndexOf(Object obj)
    {
        Integer index = (Integer) objectToIndex.get(obj);

        if(index == null)
            throw new RuntimeException("object not in universe");
        else
            return index.intValue();
    }
}
