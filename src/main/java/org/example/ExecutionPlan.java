package org.example;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@State(Scope.Benchmark)
public class ExecutionPlan {
    @Param({"100", "1000", "10000", "100000"})
    public int iteration;
    public int value = 0;
    public TreeMap<String, Integer> treeMap;
    public HashMap<String, Integer> hashMap;
    public List<String> keyList;

    @Setup(Level.Invocation)
    public void iterationSetup() {
        SecureRandom random = new SecureRandom();
        treeMap = new TreeMap<>();
        hashMap = new HashMap<>();

        List<String> keyVariants = Stream.generate(this::randomString)
            .distinct()
            .limit(10)
            .collect(Collectors.toList());
        keyList = IntStream.range(0, iteration)
            .mapToObj(i -> keyVariants.get(random.nextInt(10)))
            .collect(Collectors.toList());
    }

    private String randomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        SecureRandom random = new SecureRandom();

        return random.ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}
