package com.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;

public class TheVector {

  public static void main(String[] args) throws InterruptedException {

    int size = 1000000;
    List<Integer> arrayList = new ArrayList<>();
    List<Integer> vector = new Vector<>();

    long start = System.currentTimeMillis();
    IntStream.range(0, size).forEach(arrayList::add);
    long end = System.currentTimeMillis();
    System.out.printf("Added %d elements to arrayList in %dms\n", size, end - start);
    System.out.println("Size: " + arrayList.size());

    start = System.currentTimeMillis();
    IntStream.range(0, size).forEach(vector::add);
    end = System.currentTimeMillis();
    System.out.printf("Added %d elements to vector in %dms\n", size, end - start);
    System.out.println("Size: " + vector.size());

    System.out.println();

    //MULTI THREAD
    //ArrayList:
    //With synchronized collection we can sure that the list is correct size in multi-thread!!
    //Without synchronized, the list is Async and the result in multi-thread is wrong!! example -> multiThreadList.size() => 1125659 and not 2000000
    List<Integer> multiThreadList = Collections.synchronizedList(new ArrayList<>());
    start = System.currentTimeMillis();
    Thread t1 = new Thread(() -> {
      IntStream.range(0, size).forEach(multiThreadList::add);
    });
    Thread t2 = new Thread(() -> {
      IntStream.range(0, size).forEach(multiThreadList::add);
    });
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    end = System.currentTimeMillis();
    System.out.printf("Added %d elements to multiThreadList in %dms\n", size * 2, end - start);
    System.out.println("Size: " + multiThreadList.size());

    //Vector:
    //With vector (sync) the result always is correct! but is hard for processing
    List<Integer> multiThreadVector = new Vector<>();
    start = System.currentTimeMillis();
    t1 = new Thread(() -> {
      IntStream.range(0, size).forEach(multiThreadVector::add);
    });
    t2 = new Thread(() -> {
      IntStream.range(0, size).forEach(multiThreadVector::add);
    });
    t1.start();
    t2.start();

    t1.join();
    t2.join();
    end = System.currentTimeMillis();
    System.out.printf("Added %d elements to multiThreadVector in %dms\n", size * 2, end - start);
    System.out.println("Size: " + multiThreadVector.size());
  }
}
