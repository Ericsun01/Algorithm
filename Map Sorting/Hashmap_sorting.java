import java.util.*;

public class Hashmap_sorting {
    // replace the map with a sorted list<Key, Value>
    public List<Map.Entry<String, Long>> SortByValue(Map<String, Long> map) {
        List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            // 降序排列
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
                //return o2.getKey().compareTo(o1.getKey());
            }
        });
        return list;
    }

    public static void main(String[] args) {
        Hashmap_sorting h = new Hashmap_sorting();

        // TreeMap默认对key升序的， 若要修改顺序传入新的comparator
        Map<String, String> treemap = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                });
        treemap.put("c", "ccccc");
        treemap.put("a", "aaaaa");
        treemap.put("b", "bbbbb");
        treemap.put("d", "ddddd");
        for (Map.Entry<String, String> entry : treemap.entrySet()) {
            System.out.print("treemap's pair: ");
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.print("\n");

        // hashmap默认无序的
        Map<String, Long> map1 = new HashMap<String, Long>();
        map1.put("c", 33333L);
        map1.put("a", 11111L);
        map1.put("d", 44444L);
        map1.put("e", 55555L);
        map1.put("b", 22222L);
        List<Map.Entry<String, Long>> list1 = h.SortByValue(map1);

        // 对TreeMap的值排序
        Map<String, Long> map2 = new TreeMap<String, Long>();
        map2.put("h", 33333L);
        map2.put("f", 11111L);
        map2.put("i", 44444L);
        map2.put("j", 55555L);
        map2.put("g", 22222L);
        List<Map.Entry<String, Long>> list2 = h.SortByValue(map2);

        for (Map.Entry<String, Long> mapping : list1) {
            System.out.print("map1's pair: ");
            System.out.println(mapping.getKey() + ":" + mapping.getValue());
        }
        System.out.print("\n");

        for (Map.Entry<String, Long> mapping : list2) {
            System.out.print("map2's pair: ");
            System.out.println(mapping.getKey() + ":" + mapping.getValue());
        }
        System.out.print("\n");
    }
}
