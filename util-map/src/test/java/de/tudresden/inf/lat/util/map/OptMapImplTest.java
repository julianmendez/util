
package de.tudresden.inf.lat.util.map;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link OptMapImpl}.
 * 
 * @see OptMapImpl
 * 
 * @author Julian Mendez
 * 
 */
public class OptMapImplTest {

	static final int FIBO_SIZE = 92;
	static final int POW_SIZE = 63;

	OptMapImpl<Integer, String> newEmptyMap() {
		return new OptMapImpl<>();
	}

	OptMapImpl<Integer, String> newFiboMap(int size) {
		OptMapImpl<Integer, String> ret = new OptMapImpl<>();
		long[] a = new long[2];
		a[0] = 0;
		a[1] = 1;
		IntStream.range(0, size).forEach(index -> {
			ret.put(index, "" + a[0]);
			long t = a[0] + a[1];
			a[0] = a[1];
			a[1] = t;
		});
		return ret;
	}

	@Test
	public void testSize() {
		Assert.assertEquals(0, newEmptyMap().size());
		Assert.assertEquals(FIBO_SIZE, newFiboMap(FIBO_SIZE).size());
	}

	@Test
	public void testIsEmpty() {
		Assert.assertEquals(true, newEmptyMap().isEmpty());
		Assert.assertEquals(false, newFiboMap(FIBO_SIZE).isEmpty());
	}

	@Test
	public void testContainsKey() {
		Assert.assertEquals(false, newEmptyMap().containsKey(0));
		OptMapImpl<Integer, String> map = newFiboMap(FIBO_SIZE);
		Assert.assertEquals(true, map.containsKey(0));
		Assert.assertEquals(false, map.containsKey(FIBO_SIZE));
		Assert.assertEquals(true, map.containsKey(0));
		Assert.assertEquals(false, map.containsKey(FIBO_SIZE));
	}

	@Test
	public void testContainsValue() {
		Assert.assertEquals(false, newEmptyMap().containsValue("" + 0));
		OptMapImpl<Integer, String> map = newFiboMap(FIBO_SIZE);
		Assert.assertEquals(true, map.containsValue("" + 0));
		Assert.assertEquals(false, map.containsValue("" + 33));
	}

	@Test
	public void testGet() {
		Assert.assertEquals(Optional.empty(), newEmptyMap().get(0));
		OptMapImpl<Integer, String> map = newFiboMap(FIBO_SIZE);
		Assert.assertEquals(Optional.of("" + 0), map.get(0));
		Assert.assertEquals(Optional.of("" + 1), map.get(1));
		Assert.assertEquals(Optional.empty(), map.get(-1));
		Assert.assertEquals(Optional.of("" + 144), map.get(12));
		Assert.assertEquals(Optional.empty(), map.get(FIBO_SIZE));
		Assert.assertEquals(Optional.of("" + 89), map.get(11));
	}

	@Test
	public void testPut() {
		OptMapImpl<Integer, String> map = newEmptyMap();
		long[] a = new long[1];

		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			Assert.assertEquals(Optional.empty(), map.put(index, "" + a[0]));
			a[0] *= 2;
		});

		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			Assert.assertEquals(Optional.of("" + a[0]), map.put(index, "" + (-a[0])));
			a[0] *= 2;
		});

		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			Assert.assertEquals(Optional.of("" + (-a[0])), map.put(index, ""));
			a[0] *= 2;
		});

	}

	@Test
	public void testRemove() {
		OptMapImpl<Integer, String> map = newEmptyMap();
		Assert.assertEquals(Optional.empty(), map.remove(0));
		Assert.assertEquals(Optional.empty(), map.put(12, "" + 144));
		Assert.assertEquals(Optional.empty(), map.remove(0));
		Assert.assertEquals(Optional.of("" + 144), map.remove(12));
		Assert.assertEquals(Optional.empty(), map.remove(12));
	}

	@Test
	public void testClear() {
		OptMapImpl<Integer, String> map = newEmptyMap();
		Assert.assertEquals(0, map.size());
		map = newFiboMap(FIBO_SIZE);
		Assert.assertEquals(FIBO_SIZE, map.size());
		Assert.assertEquals(false, map.isEmpty());
		map.clear();
		Assert.assertEquals(0, map.size());
		Assert.assertEquals(true, map.isEmpty());
	}

	@Test
	public void testPutAll() {
		Map<Integer, String> hashMap = new HashMap<>();
		OptMapImpl<Integer, String> map = new OptMapImpl<>();
		long[] a = new long[1];
		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			hashMap.put(index, "" + a[0]);
			a[0] *= 2;
		});
		map.put(0, "1");
		map.put(-1, "1/2");
		map.put(-2, "1/4");
		Assert.assertEquals(3, map.size());
		map.putAll(hashMap);
		Assert.assertEquals(POW_SIZE + 2, map.size());
		map.putAll(hashMap);
		Assert.assertEquals(POW_SIZE + 2, map.size());
	}

	@Test
	public void testKeySet() {
		Assert.assertEquals(Collections.emptySet(), newEmptyMap().keySet());
		Set<Integer> set = new TreeSet<>();
		IntStream.range(0, FIBO_SIZE).forEach(index -> {
			set.add(index);
		});
		Assert.assertEquals(set, newFiboMap(FIBO_SIZE).keySet());
	}

	@Test
	public void testValues() {
		Assert.assertEquals(0, newEmptyMap().values().size());
		OptMapImpl<Integer, String> map = newEmptyMap();
		Set<String> expected = new HashSet<>();
		long[] a = new long[1];
		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			map.put(index, "" + a[0]);
			expected.add("" + a[0]);
			a[0] *= 2;
		});

		Collection<String> actual = map.values();
		Assert.assertEquals(expected.size(), map.values().size());
		expected.forEach(elem -> Assert.assertTrue(actual.contains(elem)));

		IntStream.range(0, POW_SIZE).forEach(index -> {
			map.put(index, "" + 0);
		});

		map.values().forEach(elem -> Assert.assertEquals("" + 0, elem));
	}

	@Test
	public void testEntrySet() {
		Set<Entry<Integer, String>> entrySet = new HashSet<>();
		OptMapImpl<Integer, String> map = newEmptyMap();
		long[] a = new long[1];
		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			map.put(index, "" + a[0]);
			entrySet.add(new AbstractMap.SimpleEntry<>(index, "" + a[0]));
			a[0] *= 2;
		});
		Assert.assertEquals(entrySet, map.entrySet());
	}

	@Test
	public void testAsMap() {
		Assert.assertEquals(Collections.emptyMap(), newEmptyMap().asMap());

		Map<String, Integer> treeMap = new TreeMap<>();

		long[] a = new long[1];
		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			treeMap.put("" + a[0], index);
			a[0] *= 2;
		});

		OptMapImpl<String, Integer> map = new OptMapImpl<>(treeMap);
		Assert.assertEquals(Optional.of(8), map.get("" + 256));
		Assert.assertEquals(Optional.of(16), map.get("" + 65536));
		Assert.assertEquals(treeMap, map.asMap());
	}

	@Test
	public void testEquals() {
		OptMapImpl<Integer, String> map = newEmptyMap();
		Assert.assertEquals(true, map.equals(new OptMapImpl<Integer, String>(new TreeMap<>())));
		Assert.assertEquals(false, map.equals(null));

		map = newFiboMap(FIBO_SIZE);
		OptMapImpl<Integer, String> otherMap = newFiboMap(FIBO_SIZE);
		Assert.assertEquals(map, map);
		Assert.assertEquals(map.hashCode(), otherMap.hashCode());
		map.put(-1, "1/2");
		Assert.assertNotEquals(map, otherMap);
		map.remove(-1);
		Assert.assertEquals(map, otherMap);
		Assert.assertEquals(map.hashCode(), otherMap.hashCode());
		map.put(0, "");
		Assert.assertNotEquals(map, otherMap);
		otherMap.put(0, "");
		Assert.assertEquals(map, otherMap);
		Assert.assertEquals(map.hashCode(), otherMap.hashCode());
	}

	@Test
	public void testHashCode() {
		OptMapImpl<Integer, String> map = newFiboMap(FIBO_SIZE);
		int hashCode = map.hashCode();
		OptMapImpl<Integer, String> otherMap = newFiboMap(FIBO_SIZE);
		Assert.assertEquals(map.hashCode(), otherMap.hashCode());
		Assert.assertEquals(hashCode, map.hashCode());
	}

	@Test
	public void testToString() {
		Assert.assertEquals((new HashMap<Integer, String>()).toString(), newEmptyMap().toString());

		Map<Integer, String> treeMap = new TreeMap<>();
		treeMap.putAll(newFiboMap(FIBO_SIZE).asMap());

		Map<Integer, String> otherTreeMap = new TreeMap<>();
		otherTreeMap.putAll(newFiboMap(FIBO_SIZE).asMap());

		OptMapImpl<Integer, String> map = new OptMapImpl<>(otherTreeMap);

		Assert.assertEquals(treeMap.toString(), map.toString());
	}

}
