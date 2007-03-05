// PSP Dashboard - Data Automation Tool for PSP-like processes
// Copyright (C) 1999  United States Air Force
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// The author(s) may be contacted at:
// OO-ALC/TISHD
// Attn: PSP Dashboard Group
// 6137 Wardleigh Road
// Hill AFB, UT 84056-5843
//
// E-Mail POC:  ken.raisor@hill.af.mil

//FIXME THIS CLASS IS GPL! EXCHANGE FOR AN OWN IMPLEMENTATION BEFORE RELEASING!!!
package abc.tm.weaving.weaver.tmanalysis.ds;

import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/** Simple implementation of a most-recently-used cache.
 *
 * Works like a hashtable, but only retains a fixed number of
 * mappings. When too many key-value pairs are added, the least
 * recently used pairs are flushed to remain under the user-set
 * maximum capacity.
 */
public class MRUCache implements Map {

    protected Map cache;
    protected LinkedList mruList = new LinkedList();
    protected int maxCacheItems = 50;

    public MRUCache() {}
    public MRUCache(int maxCapacity, boolean compareOnIdentity) {
    	maxCacheItems = maxCapacity;
    	if(compareOnIdentity) {
    		cache = new IdentityHashMap();
    	} else {
    		cache = new HashMap();
    	}
    }


    protected void touchItem(Object key) {
        mruList.remove(key);
        mruList.addFirst(key);

        if (cache.size() > maxCacheItems)
            cache.remove(mruList.removeLast());
    }

    // implementation of java.util.Map interface

    public int hashCode() { return cache.hashCode(); }
    public boolean equals(Object obj) { return cache.equals(obj); }
    public int size() { return cache.size(); }
    public Collection values() { return cache.values(); }
    public Set keySet() { return cache.keySet(); }
    public Set entrySet() { return cache.entrySet(); }
    public boolean isEmpty() { return cache.isEmpty(); }
    public boolean containsValue(Object v) { return cache.containsValue(v); }
    public boolean containsKey(Object k) { return cache.containsKey(k); }


    public Object put(Object key, Object value) {
        touchItem(key);
        return cache.put(key, value);
    }
    public Object get(Object key) {
        touchItem(key);
        return cache.get(key);
    }
    public Object remove(Object key) {
        mruList.remove(key);
        return cache.remove(key);
    }
    public void clear() {
        mruList.clear();
        cache.clear();
    }
    public void putAll(Map m) {
        Iterator i = m.entrySet().iterator();
        Map.Entry e;
        while (i.hasNext()) {
            e = (Map.Entry) i.next();
            put(e.getKey(), e.getValue());
        }
    }
}