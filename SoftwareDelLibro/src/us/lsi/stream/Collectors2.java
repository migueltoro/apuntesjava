package us.lsi.stream;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import us.lsi.common.Entry;
import us.lsi.common.MultiMaps2;
import us.lsi.common.Multisets2;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

public class Collectors2 {

	public static <E> Collector<E,?,Multiset<E>> toMultiset(){
		return new ToMultiset<E>();
	}
	
	public static <E> Collector<E,?,SortedSet<E>> toSortedSet(Comparator<E> cmp){
		return new ToSortedSet<E>(cmp);
	}
	
	public static <K,V> Collector<Entry<K,V>,?,ListMultimap<K,V>> toListMultimap(){
		return new ToListMultimap<K,V>();
	}
	
	public static <K,V> Collector<Entry<K,V>,?,SetMultimap<K,V>> toSetMultimap(){
		return new ToSetMultimap<K,V>();
	}
	
	
	private static class ToSortedSet<E> implements Collector<E,SortedSet<E>,SortedSet<E>>{

		private Supplier<SortedSet<E>> supplier;
		private BiConsumer<SortedSet<E>, E> accumulator;
		private BinaryOperator<SortedSet<E>> combiner;
		private Function<SortedSet<E>, SortedSet<E>> transformer;	
		
		public ToSortedSet(Comparator<E> cmp) {
			super(); 
			this.supplier = ()-> Sets.newTreeSet(cmp);
			this.accumulator = (SortedSet<E> m, E e)-> m.add(e);
			this.combiner = (SortedSet<E> m1, SortedSet<E> m2)->{ SortedSet<E> m = Sets.newTreeSet(cmp); m.addAll(m2); m.addAll(m2); return m;};
			this.transformer = Function.identity();
		}
		
		@Override
		public Set<java.util.stream.Collector.Characteristics> characteristics() {
			return Sets.newHashSet(Collector.Characteristics.UNORDERED, Collector.Characteristics.CONCURRENT);
		}



		@Override
		public Supplier<SortedSet<E>> supplier() {
			return supplier;
		}



		@Override
		public BiConsumer<SortedSet<E>, E> accumulator() {
			return accumulator;
		}



		@Override
		public BinaryOperator<SortedSet<E>> combiner() {
			return combiner;
		}



		@Override
		public Function<SortedSet<E>, SortedSet<E>> finisher() {
			return transformer;
		}
		
	}
	
	
	private static class ToMultiset<E> implements Collector<E,Multiset<E>,Multiset<E>>{

		private Supplier<Multiset<E>> supplier;
		private BiConsumer<Multiset<E>, E> accumulator;
		private BinaryOperator<Multiset<E>> combiner;
		private Function<Multiset<E>, Multiset<E>> transformer;	
		
		public ToMultiset() {
			super();
			this.supplier = ()-> Multisets2.newHashMultiset();
			this.accumulator = (Multiset<E> m, E e)-> m.add(e);
			this.combiner = (Multiset<E> m1, Multiset<E> m2)->{ Multiset<E> m = Multisets2.newHashMultiset(m1); m.addAll(m2); return m;};
			this.transformer = Function.identity();
		}
		
		@Override
		public Set<java.util.stream.Collector.Characteristics> characteristics() {
			return Sets.newHashSet(Collector.Characteristics.UNORDERED, Collector.Characteristics.CONCURRENT);
		}



		@Override
		public Supplier<Multiset<E>> supplier() {
			return supplier;
		}



		@Override
		public BiConsumer<Multiset<E>, E> accumulator() {
			return accumulator;
		}



		@Override
		public BinaryOperator<Multiset<E>> combiner() {
			return combiner;
		}



		@Override
		public Function<Multiset<E>, Multiset<E>> finisher() {
			return transformer;
		}
		
	}
	
	private static class ToListMultimap<K,V> implements Collector<Entry<K,V>,ListMultimap<K,V>,ListMultimap<K,V>>{

		private Supplier<ListMultimap<K,V>> supplier;
		private BiConsumer<ListMultimap<K,V>, Entry<K,V>> accumulator;
		private BinaryOperator<ListMultimap<K,V>> combiner;
		private Function<ListMultimap<K,V>, ListMultimap<K,V>> transformer;	
		
		public ToListMultimap() {
			super();
			this.supplier = ()-> MultiMaps2.newListMultimap();
			this.accumulator = (ListMultimap<K,V> m, Entry<K,V> e)-> m.put(e.key,e.value);
			this.combiner = (ListMultimap<K,V> m1, ListMultimap<K,V> m2)->{ ListMultimap<K,V> m = MultiMaps2.newListMultimap(m1); m.putAll(m2); return m;};
			this.transformer = Function.identity();
		}
		
		@Override
		public Set<java.util.stream.Collector.Characteristics> characteristics() {
			return Sets.newHashSet(Collector.Characteristics.UNORDERED, Collector.Characteristics.CONCURRENT);
		}



		@Override
		public Supplier<ListMultimap<K,V>> supplier() {
			return supplier;
		}



		@Override
		public BiConsumer<ListMultimap<K,V>, Entry<K,V>> accumulator() {
			return accumulator;
		}



		@Override
		public BinaryOperator<ListMultimap<K,V>> combiner() {
			return combiner;
		}



		@Override
		public Function<ListMultimap<K,V>, ListMultimap<K,V>> finisher() {
			return transformer;
		}
		
	}
	
	private static class ToSetMultimap<K,V> implements Collector<Entry<K,V>,SetMultimap<K,V>,SetMultimap<K,V>>{

		private Supplier<SetMultimap<K,V>> supplier;
		private BiConsumer<SetMultimap<K,V>, Entry<K,V>> accumulator;
		private BinaryOperator<SetMultimap<K,V>> combiner;
		private Function<SetMultimap<K,V>, SetMultimap<K,V>> transformer;	
		
		public ToSetMultimap() {
			super();
			this.supplier = ()-> MultiMaps2.newSetMultimap();
			this.accumulator = (SetMultimap<K,V> m, Entry<K,V> e)-> m.put(e.key,e.value);
			this.combiner = (SetMultimap<K,V> m1, SetMultimap<K,V> m2)->{ SetMultimap<K,V> m = MultiMaps2.newSetMultimap(m1); m.putAll(m2); return m;};
			this.transformer = Function.identity();
		}
		
		@Override
		public Set<java.util.stream.Collector.Characteristics> characteristics() {
			return Sets.newHashSet(Collector.Characteristics.UNORDERED, Collector.Characteristics.CONCURRENT);
		}



		@Override
		public Supplier<SetMultimap<K,V>> supplier() {
			return supplier;
		}



		@Override
		public BiConsumer<SetMultimap<K,V>, Entry<K,V>> accumulator() {
			return accumulator;
		}



		@Override
		public BinaryOperator<SetMultimap<K,V>> combiner() {
			return combiner;
		}



		@Override
		public Function<SetMultimap<K,V>, SetMultimap<K,V>> finisher() {
			return transformer;
		}
		
	}
	
	
}
