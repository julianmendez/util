
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
		Assertions.assertEquals(0, newEmptyMap().size());
		Assertions.assertEquals(FIBO_SIZE, newFiboMap(FIBO_SIZE).size());
	}

	@Test
	public void testIsEmpty() {
		Assertions.assertEquals(true, newEmptyMap().isEmpty());
		Assertions.assertEquals(false, newFiboMap(FIBO_SIZE).isEmpty());
	}

	@Test
	public void testContainsKey() {
		Assertions.assertEquals(false, newEmptyMap().containsKey(0));
		OptMapImpl<Integer, String> map = newFiboMap(FIBO_SIZE);
		Assertions.assertEquals(true, map.containsKey(0));
		Assertions.assertEquals(false, map.containsKey(FIBO_SIZE));
		Assertions.assertEquals(true, map.containsKey(0));
		Assertions.assertEquals(false, map.containsKey(FIBO_SIZE));
	}

	@Test
	public void testContainsValue() {
		Assertions.assertEquals(false, newEmptyMap().containsValue("" + 0));
		OptMapImpl<Integer, String> map = newFiboMap(FIBO_SIZE);
		Assertions.assertEquals(true, map.containsValue("" + 0));
		Assertions.assertEquals(false, map.containsValue("" + 33));
	}

	@Test
	public void testGet() {
		Assertions.assertEquals(Optional.empty(), newEmptyMap().get(0));
		OptMapImpl<Integer, String> map = newFiboMap(FIBO_SIZE);
		Assertions.assertEquals(Optional.of("" + 0), map.get(0));
		Assertions.assertEquals(Optional.of("" + 1), map.get(1));
		Assertions.assertEquals(Optional.empty(), map.get(-1));
		Assertions.assertEquals(Optional.of("" + 144), map.get(12));
		Assertions.assertEquals(Optional.empty(), map.get(FIBO_SIZE));
		Assertions.assertEquals(Optional.of("" + 89), map.get(11));
	}

	@Test
	public void testPut() {
		OptMapImpl<Integer, String> map = newEmptyMap();
		long[] a = new long[1];

		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			Assertions.assertEquals(Optional.empty(), map.put(index, "" + a[0]));
			a[0] *= 2;
		});

		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			Assertions.assertEquals(Optional.of("" + a[0]), map.put(index, "" + (-a[0])));
			a[0] *= 2;
		});

		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			Assertions.assertEquals(Optional.of("" + (-a[0])), map.put(index, ""));
			a[0] *= 2;
		});

	}

	@Test
	public void testRemove() {
		OptMapImpl<Integer, String> map = newEmptyMap();
		Assertions.assertEquals(Optional.empty(), map.remove(0));
		Assertions.assertEquals(Optional.empty(), map.put(12, "" + 144));
		Assertions.assertEquals(Optional.empty(), map.remove(0));
		Assertions.assertEquals(Optional.of("" + 144), map.remove(12));
		Assertions.assertEquals(Optional.empty(), map.remove(12));
	}

	@Test
	public void testClear() {
		OptMapImpl<Integer, String> map = newEmptyMap();
		Assertions.assertEquals(0, map.size());
		map = newFiboMap(FIBO_SIZE);
		Assertions.assertEquals(FIBO_SIZE, map.size());
		Assertions.assertEquals(false, map.isEmpty());
		map.clear();
		Assertions.assertEquals(0, map.size());
		Assertions.assertEquals(true, map.isEmpty());
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
		Assertions.assertEquals(3, map.size());
		map.putAll(hashMap);
		Assertions.assertEquals(POW_SIZE + 2, map.size());
		map.putAll(hashMap);
		Assertions.assertEquals(POW_SIZE + 2, map.size());
	}

	@Test
	public void testKeySet() {
		Assertions.assertEquals(Collections.emptySet(), newEmptyMap().keySet());
		Set<Integer> set = new TreeSet<>();
		IntStream.range(0, FIBO_SIZE).forEach(index -> {
			set.add(index);
		});
		Assertions.assertEquals(set, newFiboMap(FIBO_SIZE).keySet());
	}

	@Test
	public void testValues() {
		Assertions.assertEquals(0, newEmptyMap().values().size());
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
		Assertions.assertEquals(expected.size(), map.values().size());
		expected.forEach(elem -> Assertions.assertTrue(actual.contains(elem)));

		IntStream.range(0, POW_SIZE).forEach(index -> {
			map.put(index, "" + 0);
		});

		map.values().forEach(elem -> Assertions.assertEquals("" + 0, elem));
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
		Assertions.assertEquals(entrySet, map.entrySet());
	}

	@Test
	public void testAsMap() {
		Assertions.assertEquals(Collections.emptyMap(), newEmptyMap().asMap());

		Map<String, Integer> treeMap = new TreeMap<>();

		long[] a = new long[1];
		a[0] = 1;
		IntStream.range(0, POW_SIZE).forEach(index -> {
			treeMap.put("" + a[0], index);
			a[0] *= 2;
		});

		OptMapImpl<String, Integer> map = new OptMapImpl<>(treeMap);
		Assertions.assertEquals(Optional.of(8), map.get("" + 256));
		Assertions.assertEquals(Optional.of(16), map.get("" + 65536));
		Assertions.assertEquals(treeMap, map.asMap());
	}

	@Test
	public void testEquals() {
		OptMapImpl<Integer, String> map = newEmptyMap();
		Assertions.assertEquals(true, map.equals(new OptMapImpl<Integer, String>(new TreeMap<>())));
		Assertions.assertEquals(false, map.equals(null));

		map = newFiboMap(FIBO_SIZE);
		OptMapImpl<Integer, String> otherMap = newFiboMap(FIBO_SIZE);
		Assertions.assertEquals(map, map);
		Assertions.assertEquals(map.hashCode(), otherMap.hashCode());
		map.put(-1, "1/2");
		Assertions.assertNotEquals(map, otherMap);
		map.remove(-1);
		Assertions.assertEquals(map, otherMap);
		Assertions.assertEquals(map.hashCode(), otherMap.hashCode());
		map.put(0, "");
		Assertions.assertNotEquals(map, otherMap);
		otherMap.put(0, "");
		Assertions.assertEquals(map, otherMap);
		Assertions.assertEquals(map.hashCode(), otherMap.hashCode());
	}

	@Test
	public void testHashCode() {
		OptMapImpl<Integer, String> map = newFiboMap(FIBO_SIZE);
		int hashCode = map.hashCode();
		OptMapImpl<Integer, String> otherMap = newFiboMap(FIBO_SIZE);
		Assertions.assertEquals(map.hashCode(), otherMap.hashCode());
		Assertions.assertEquals(hashCode, map.hashCode());
	}

	@Test
	public void testToString() {
		Assertions.assertEquals((new HashMap<Integer, String>()).toString(), newEmptyMap().toString());

		Map<Integer, String> treeMap = new TreeMap<>();
		treeMap.putAll(newFiboMap(FIBO_SIZE).asMap());

		Map<Integer, String> otherTreeMap = new TreeMap<>();
		otherTreeMap.putAll(newFiboMap(FIBO_SIZE).asMap());

		OptMapImpl<Integer, String> map = new OptMapImpl<>(otherTreeMap);

		Assertions.assertEquals(treeMap.toString(), map.toString());
	}

}
