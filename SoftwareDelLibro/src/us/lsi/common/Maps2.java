package us.lsi.common;

import java.util.*;

public class Maps2 {
	
    public static <K, V> Map<K, V> newHashMap(@SuppressWarnings("unchecked") Entry<? extends K, ? extends V>... entries) {
        Map<K, V> result = new HashMap<>(entries.length);

        for (Entry<? extends K, ? extends V> entry : entries)
            if (entry.value != null)
                result.put(entry.key, entry.value);

        return result;
    }

    
} 
