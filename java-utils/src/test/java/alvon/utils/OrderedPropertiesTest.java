package alvon.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import alvin.utils.OrderedProperties;

public class OrderedPropertiesTest {

	@Test
	public void testSingleElementProperty() throws Exception {
		
		String propString = "prop1=hello";
		OrderedProperties prop = new OrderedProperties();
		prop.load(makeStream(propString));
		
		Set<String> names = prop.stringPropertyNames();
		assertEquals(1, names.size());
		assertEquals("prop1", names.iterator().next());
		
		Enumeration<?> enums = prop.propertyNames();
		assertTrue(enums.hasMoreElements());
		assertEquals("prop1", enums.nextElement());
	}
	
	@Test
	public void testMultiElementProperty() throws Exception {
		
		String propString = "prop1=hello\nprop2=world\nprop3=again";
		OrderedProperties prop = new OrderedProperties();
		prop.load(makeStream(propString));
		
		Set<String> names = prop.stringPropertyNames();
		assertEquals(3, names.size());
		Iterator<String> namesItr = names.iterator();
		assertEquals("prop1", namesItr.next());
		assertEquals("prop2", namesItr.next());
		assertEquals("prop3", namesItr.next());
		
		Enumeration<?> enums = prop.propertyNames();
		assertTrue(enums.hasMoreElements());
		assertEquals("prop1", enums.nextElement());
		assertEquals("prop2", enums.nextElement());
		assertEquals("prop3", enums.nextElement());
	}
	
	@Test
	public void testMultiElementWithRemoval() throws Exception {
		
		String propString = "prop1=hello\nprop2=world\nprop3=again";
		OrderedProperties prop = new OrderedProperties();
		prop.load(makeStream(propString));
		
		prop.remove("prop2");
		
		Set<String> names = prop.stringPropertyNames();
		assertEquals(2, names.size());
		Iterator<String> namesItr = names.iterator();
		assertEquals("prop1", namesItr.next());
		assertEquals("prop3", namesItr.next());
		
		Enumeration<?> enums = prop.propertyNames();
		assertTrue(enums.hasMoreElements());
		assertEquals("prop1", enums.nextElement());
		assertEquals("prop3", enums.nextElement());
	}
	
	@Test
	public void testMultiElementWithAddition() throws Exception {
		
		String propString = "prop1=hello\nprop2=world\nprop3=again";
		OrderedProperties prop = new OrderedProperties();
		prop.load(makeStream(propString));
		
		prop.setProperty("prop4", "and again");
		
		Set<String> names = prop.stringPropertyNames();
		assertEquals(4, names.size());
		Iterator<String> namesItr = names.iterator();
		assertEquals("prop1", namesItr.next());
		assertEquals("prop2", namesItr.next());
		assertEquals("prop3", namesItr.next());
		assertEquals("prop4", namesItr.next());
		
		Enumeration<?> enums = prop.propertyNames();
		assertTrue(enums.hasMoreElements());
		assertEquals("prop1", enums.nextElement());
		assertEquals("prop2", enums.nextElement());
		assertEquals("prop3", enums.nextElement());
		assertEquals("prop4", enums.nextElement());
	}
	
	@Test
	public void testGetValuesIsOrdered() throws Exception {
		
		String propString = "prop1=hello\nprop2=world\nprop3=again";
		OrderedProperties prop = new OrderedProperties();
		prop.load(makeStream(propString));
		
		Collection<Object> values = prop.values();
		
		assertEquals(3, values.size());
		Iterator<Object> valuesItr = values.iterator();
		assertEquals("hello", valuesItr.next());
		assertEquals("world", valuesItr.next());
		assertEquals("again", valuesItr.next());
	}
	
	private InputStream makeStream(String prop) {
		
		return new ByteArrayInputStream(prop.getBytes());
	}
}
