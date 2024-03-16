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
    @Param({"100", "1000", "10000", "100000"})
    public int iteration;
    public int value = 0;
    public TreeMap<String, Integer> treeMap;
    public HashMap<String, Integer> hashMap;
    public List<String> keyList;
    private SecureRandom random;

    @Setup(Level.Iteration)
    public void iterationSetup() {
        random = new SecureRandom();
    }

    @Setup(Level.Invocation)
    public void invocationSetup() {
        treeMap = new TreeMap<>();
        hashMap = new HashMap<>();
        value = random.nextInt();

        keyList = Stream.generate(this::randomString)
            .limit(iteration)
            .collect(Collectors.toList());
    }

    private String randomString() {
        int leftLimit = 'A';
        int rightLimit = 'z';
        int targetStringLength = random.nextInt(15) + 1;

        return random.ints(leftLimit, rightLimit + 1)
            .filter(c -> c <= 'Z' || c >= 'a')
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}
