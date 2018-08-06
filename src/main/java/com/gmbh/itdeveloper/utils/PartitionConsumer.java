package com.gmbh.itdeveloper.utils;

@FunctionalInterface
public interface PartitionConsumer<INDEX,OFFSET> {
    void accept(INDEX index,OFFSET offset);
}
