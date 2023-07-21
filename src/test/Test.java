package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

//    static final Logger logger = LogManager.getLogger();

    public static long hash(String s) {
        if (s == null) {
            return 587058L;
        }
        if (s.isEmpty()) {
            return 0L;
        }

        long res = s.charAt(0) << 7;
        for (int i = 0; i < s.length(); ++i) {
            res = (1000003 * res) ^ s.charAt(i);
        }
        res ^= s.length();
        if (res == -1L) {
            res = -2L;
        }
        return res;
    }

    public static int mod(long a, int b) {
        int res = (int) (a % b);
        res = res < 0 ? res + b : res;
        return res;
    }

    public int getShardByUid(String uid) {
        int redisShardNum = 0;
        int ceofOldShardNumber = 0;
        int seed = HashUtils.hashMod(uid, redisShardNum);
        int fixCeof = HashUtils.imohashMod(uid, ceofOldShardNumber);
        return (seed / ceofOldShardNumber) * ceofOldShardNumber + fixCeof;
    }

    private static final String BGID = "bgid";
    public static void main (String[] args) {
        System.out.println(HashUtils.hashMod("1135373949589958", 512));
        System.out.println(HashUtils.imohashMod("1100061474233456", 3));
        System.out.println(HashUtils.imohashMod("ds.202153691160991540", 128));
        System.out.println(HashUtils.imohashMod("ds.297809645340364602", 128));
        System.out.println(HashUtils.imohashMod("ds.279088789807103968", 128));
        System.out.println(HashUtils.imohashMod("ds.282063211614768411", 128));
        Map<String, Object> map2k = new HashMap<>();
        System.out.println(map2k.get("live_status"));
        map2k.put("live_status", null);
        System.out.println(map2k.get("live_status"));
        String text = "{date} Stats: channel {name} was recommended to {num} users, which brought {num} users to this channel.";
        System.out.println(Arrays.toString(text.split("\\{([^\\{\\}]+)\\}")));
        List<String> buids = Arrays.asList("uid1", "uid2");
        Map<String, Object> mapp = new HashMap<>();
        mapp.put("uid1", 4.2);
        List<Map<String, Object>> mappp = mapp.entrySet().stream()
                .filter(e -> (Double) e.getValue() >= 4.2 && buids.contains(e.getKey()))
                .sorted()
                .limit(200)
                .map(e -> {
                    Map<String , Object> map = new HashMap<>();
                    map.put("buid", e.getKey());
                    map.put("intimacy", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
        System.out.println("map"+ mappp);
        System.out.println(GatewayResponse.uniformResponseWithNetworkResult(NetworkResult.success()));
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        for (String item : list) {
            if ("1".equals(item)) {
                list.remove(item);
            }
        }
        Long l = (2592000000L / 30);
        System.out.println(l.intValue());
        String str = "abc/def";
        String[] strs = str.split("/");
        System.out.println(Arrays.toString(strs));
        List<Map<String, Object>> list1 = new ArrayList<>();
        List<Map<String, Object>> list2 = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map11 = new HashMap<>();
        Map<String, Object> map111 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map22 = new HashMap<>();
        map1.put("tag", "xx");
        map1.put("id", "aa");
        map11.put("tag", "yy");
        map11.put("id", "bb");
        map111.put("tag", "ky");
        map111.put("id", "bq");
        map2.put("tag", "yy");
        map2.put("id", "bb");
        map22.put("tag", "xx");
        map22.put("id", "aa");
        list1.add(map1);
        list1.add(map11);
        list1.add(map111);
        list2.add(map2);
        list2.add(map22);
        List<Map<String, Object>> res = list1.stream().filter(l1 -> !list2.contains(l1)).collect(Collectors.toList());
        System.out.println(res);

        VarHolder<Map<String, Object>> mapHolder = new VarHolder<>();
        Map<String, Object> map = new HashMap<>();
        map.put("a", "a");
        mapHolder.set(map);
        System.out.println(mapHolder.get());
        mapHolder.get().put("b", "b");
        System.out.println(mapHolder.get());
        String udid = null;
        System.out.println(Collections.singletonList(udid));
        System.out.println("----------redis shard---------");

        System.out.println(mod(hash("1009521145237258"), 512));
        System.out.println(mod(hash("1127917363384289"), 1024));
        System.out.println(mod(hash("1134676643916382"), 1024));
        System.out.println(mod(hash("1104383785269646"), 512));
        System.out.println(mod(hash("1104172235566101"), 512));
        System.out.println(mod(hash("1100073969672790"), 4));
        System.out.println(mod(hash("1100071828571006"), 4));
        System.out.println(mod(hash("1100024412130530"), 4));
        System.out.println(mod(hash("1100064605990380"), 4));
        System.out.println(mod(hash("1100018737464043"), 4));
        System.out.println(mod(hash("1100051245101862"), 4));

        System.out.println("----sharding----");
        boolean flag = false;
        System.out.println(!(flag));
        System.out.println(HashUtils.imohashMod("1100073969672790:start", 1) / 1);
        //
        System.out.println("-------线上");
        System.out.println("self");
        System.out.println(mod(hash("1127917363384289"), 512));
        System.out.println(HashUtils.hashMod("1110565782737082:start", 256));
        System.out.println(HashUtils.hashMod("1110565782737082:end", 256));
        System.out.println(HashUtils.hashMod("1110565782737082:start", 256) / 32);
        System.out.println(HashUtils.hashMod("1110565782737082:end", 256) / 32);
        System.out.println(HashUtils.imohashMod("1110565782737082", 8));
        System.out.println("chai");
        System.out.println(511 / 2);
        System.out.println(mod(hash("3027176563747150"), 1024));
        System.out.println(mod(hash("1129150543130107"), 1024));
        System.out.println(mod(hash("1117518517957754"), 512));
        System.out.println(HashUtils.hashMod("1117518517957754:start", 256));
        System.out.println(HashUtils.hashMod("1117518517957754:end", 256));
        System.out.println(HashUtils.hashMod("1117518517957754:start", 256) / 32);
        System.out.println(HashUtils.hashMod("1117518517957754:end", 256) / 32);
        System.out.println("yulong");
        System.out.println(mod(hash("1118092575405027"), 1024));
        System.out.println(HashUtils.hashMod("1118092575405027:start", 256) / 32);
        System.out.println(HashUtils.hashMod("1118092575405027:end", 256) / 32);
        System.out.println(mod(hash("1110565782737082"), 8));
        System.out.println(mod(hash("1110565782737082"), 512));
        System.out.println(mod(hash("bg.0lfisdefp98o3r0ac"), 3));
        System.out.println(mod(hash("1129341085280643"), 512));
        System.out.println(mod(hash("1000882271743497"), 512));
        System.out.println(mod(hash("3017277657355470"), 512));
        System.out.println(mod(hash("1103481927003530"), 512));
        System.out.println(mod(hash("3007444154391811"), 8));
        Long pinShard = null;
//        System.out.println(pinShard.intValue());
//        test("123", "321");
//        String key = "bgid";
//        System.out.println(BGID == key);
//        Stream.of("d2", "a2", "b1", "b3", "c")
//                .map(s -> {
//                    System.out.println("map: " + s);
//                    return s.toUpperCase(); // 转大写
//                })
//                .filter(s -> {
//                    System.out.println("filter: " + s);
//                    return s.startsWith("A"); // 过滤出以 A 为前缀的元素
//                })
//                .forEach(s -> System.out.println("forEach: " + s)); // for 循环输出
//
//// map:     d2
//// filter:  D2
//// map:     a2
//// filter:  A2
//// forEach: A2
//// map:     b1
//// filter:  B1
//// map:     b3
//// filter:  B3
//// map:     c
//// filter:  C
//        Stream.of("d2", "a2", "b1", "b3", "c")
//                .filter(s -> {
//                    System.out.println("filter: " + s);
//                    return s.startsWith("a"); // 过滤出以 a 为前缀的元素
//                })
//                .map(s -> {
//                    System.out.println("map: " + s);
//                    return s.toUpperCase(); // 转大写
//                })
//                .forEach(s -> System.out.println("forEach: " + s)); // for 循环输出
//
//// filter:  d2
//// filter:  a2
//// map:     a2
//// forEach: A2
//// filter:  b1
//// filter:  b3
//// filter:  c
//        System.out.println();
//        Stream.of("d2", "a2", "b1", "b3", "c")
//                .sorted((s1, s2) -> {
//                    System.out.printf("sort: %s; %s\n", s1, s2);
//                    return s1.compareTo(s2); // 排序
//                })
//                .filter(s -> {
//                    System.out.println("filter: " + s);
//                    return s.startsWith("a"); // 过滤出以 a 为前缀的元素
//                })
//                .map(s -> {
//                    System.out.println("map: " + s);
//                    return s.toUpperCase(); // 转大写
//                })
//                .forEach(s -> System.out.println("forEach: " + s)); // for 循环输出
////        sort:    a2; d2
////        sort:    b1; a2
////        sort:    b1; d2
////        sort:    b1; a2
////        sort:    b3; b1
////        sort:    b3; d2
////        sort:    c; b3
////        sort:    c; d2
////        filter:  a2
////        map:     a2
////        forEach: A2
////        filter:  b1
////        filter:  b3
////        filter:  c
////        filter:  d2
//        System.out.println();
//        Stream.of("d2", "a2", "b1", "b3", "c")
//                .filter(s -> {
//                    System.out.println("filter: " + s);
//                    return s.startsWith("a");
//                })
//                .sorted((s1, s2) -> {
//                    System.out.printf("sort: %s; %s\n", s1, s2);
//                    return s1.compareTo(s2);
//                })
//                .map(s -> {
//                    System.out.println("map: " + s);
//                    return s.toUpperCase();
//                })
//                .forEach(s -> System.out.println("forEach: " + s));
//
//// filter:  d2
//// filter:  a2
//// filter:  b1
//// filter:  b3
//// filter:  c
//// map:     a2
//// forEach: A2
//        Map<String, Object> map = new HashMap<>();
//        System.out.println(map == null);
//        System.out.println(map.get("key"));
    }
}
