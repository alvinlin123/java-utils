package alvin.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * An extension of java.util.Properties class. Unlik Properties clas,s 
 * this will remembers the order properties were added in. Methods like
 * {@link #propertyNames()} will return property names in order they 
 * were added.
 *
 */
public class OrderedProperties extends Properties {

	private static final long serialVersionUID = -2603508716390714885L;
	
	private LinkedHashSet<String> _propIndex;
	
	{
		_propIndex = new LinkedHashSet<String>();
	}
	
	@Override
	public Enumeration<?> propertyNames() {
		
		final Iterator<String> properties = _propIndex.iterator();
		
		
		return new Enumeration<Object>() {
			
			public boolean hasMoreElements() {
				
				return properties.hasNext();
				
			}
			
			public Object nextElement() {
				
				return properties.next();
			}
			
		};
	}
	
	@Override
	public Set<String> stringPropertyNames() {
		
		return Collections.unmodifiableSet(_propIndex);
	}

	@Override
	public synchronized Object put(Object key, Object val) {
		_propIndex.add(key.toString());
		return super.put(key, val);		
	}

	@Override
	public synchronized Object remove(Object key) {
		
		_propIndex.remove(key);
		return super.remove(key);
	}

	@Override
	public Collection<Object> values() {
		
		ArrayList<Object> values = new ArrayList<Object>(this.size());
		
		for (String key : _propIndex) {
			values.add(get(key));
		}
		
		return Collections.unmodifiableCollection(values);
	}
}
