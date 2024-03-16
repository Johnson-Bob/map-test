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
import java.util.stream.Stream;

@State(Scope.Benchmark)
public class ExecutionPlan {
    @Param({"100000"})
    public int iteration;
    public int value = 0;
    public TreeMap<String, Integer> treeMap;
    public HashMap<String, Integer> hashMap;
    public List<String> keyVariants;
    public SecureRandom random;

    @Setup(Level.Invocation)
    public void iterationSetup() {
        random = new SecureRandom();
        treeMap = new TreeMap<>();
        hashMap = new HashMap<>();

        keyVariants = Stream.generate(this::randomString)
            .distinct()
            .limit(10)
            .collect(Collectors.toList());
    }

    private String randomString() {
        int leftLimit = 'A';
        int rightLimit = 'z';
        int targetStringLength = 8;
        SecureRandom random = new SecureRandom();

        return random.ints(leftLimit, rightLimit + 1)
            .filter(l -> l <= 'Z' || l >='a')
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}
