/*
 * Copyright (C) 2017-2019 Dremio Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dremio.exec.store.store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dremio.exec.physical.EndpointAffinity;
import com.dremio.exec.proto.CoordinationProtos.NodeEndpoint;
import com.dremio.exec.store.schedule.AssignmentCreator;
import com.dremio.exec.store.schedule.CompleteWork;
import com.dremio.exec.store.schedule.SimpleCompleteWork;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class TestAssignment {

  private static final long FILE_SIZE = 1000;
  private static List<NodeEndpoint> endpoints;

  @BeforeClass
  public static void setup() {
    endpoints = Lists.newArrayList();
    final String pattern = "node%d";
    for (int i = 2; i < 32; i++) {
      String host = String.format(pattern, i);
      endpoints.add(NodeEndpoint.newBuilder().setAddress(host).build());
    }
  }

  @Test
  public void manyFiles() throws Exception {
    List<CompleteWork> chunks = generateChunks(1000);

    Iterator<NodeEndpoint> incomingEndpointsIterator = Iterators.cycle(endpoints);

    List<NodeEndpoint> incomingEndpoints = Lists.newArrayList();

    final int width = 28 * 30;
    for (int i = 0; i < width; i++) {
      incomingEndpoints.add(incomingEndpointsIterator.next());
    }

    ListMultimap<Integer, CompleteWork> mappings = AssignmentCreator.getMappings(incomingEndpoints, chunks);
    System.out.println(mappings.keySet().size());
    for (int i = 0; i < width; i++) {
      Assert.assertTrue("no mapping for entry " + i, mappings.containsKey(i));
      Assert.assertFalse("mapping for entry " + i + " was empty", mappings.get(i).isEmpty());
    }
  }

  private List<CompleteWork> generateChunks(int chunks) {
    List<CompleteWork> chunkList = Lists.newArrayList();
    for (int i = 0; i < chunks; i++) {
      SimpleCompleteWork chunk = new SimpleCompleteWork(FILE_SIZE, getAffinity());
      chunkList.add(chunk);
    }
    return chunkList;
  }

  private List<EndpointAffinity> getAffinity() {
    List<EndpointAffinity> affinity = new ArrayList<>();
    Set<NodeEndpoint> usedEndpoints = Sets.newHashSet();
    while (usedEndpoints.size() < 3) {
      usedEndpoints.add(getRandom(endpoints));
    }
    for (NodeEndpoint ep : usedEndpoints) {
      affinity.add(new EndpointAffinity(ep, FILE_SIZE));
    }
    return affinity;
  }

  private <T> T getRandom(List<T> list) {
    int index = ThreadLocalRandom.current().nextInt(list.size());
    return list.get(index);
  }
}
