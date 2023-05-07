package com.assignment;

import java.util.*;

public class SignalProblem {

	public static void main(String[] args) {
		
		
		int[][] times = { { 2, 1, 1 }, { 2, 3, 1 }, { 3, 4, 1 } };
		int n = 4;
		int k = 2;

		int result = networkDelayTime(times, n, k);
		System.out.println(result); // Output: 2

	}

	public static int networkDelayTime(int[][] times, int n, int k) {

		// Create a distance array and initialize it to infinity, except for the source
		// node k
		int[] dist = new int[n];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[k - 1] = 0;

		// Create a set of unvisited nodes and add all the nodes to it
		Set<Integer> unvisitedNodes = new HashSet<>();
		for (int i = 1; i <= n; i++) 
		{
			unvisitedNodes.add(i);
		}

		// Create a map to store the neighbors and their distances
		Map<Integer, List<int[]>> neighbors = new HashMap<>();
		
		for (int[] time : times) 
		{
			int u = time[0], v = time[1], w = time[2];
			neighbors.computeIfAbsent(u, k1 -> new ArrayList<>()).add(new int[] { v, w });
		}

		// Use priority queue to optimize the search for the node with the smallest
		// distance
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		heap.offer(new int[] { k, 0 });

		while (!unvisitedNodes.isEmpty()) 
		{
			// Find the node with the smallest distance and remove it from unvisitedNodes
			int[] node = heap.poll();
			int u = node[0], d = node[1];
			
			if (!unvisitedNodes.contains(u)) 
			{
				continue;
			}
			unvisitedNodes.remove(u);

			// Update the distances to the neighbors of u
			if (neighbors.containsKey(u)) 
			{
				for (int[] edge : neighbors.get(u)) 
				{
					int v = edge[0], w = edge[1];
					if (unvisitedNodes.contains(v) && d + w < dist[v - 1]) 
					{
						dist[v - 1] = d + w;
						heap.offer(new int[] { v, dist[v - 1] });
					}
				}
			}
		}

		// Return the maximum value in dist[], or -1 if any value is still infinity
		int maxDist = Arrays.stream(dist).max().getAsInt();
		return (maxDist == Integer.MAX_VALUE) ? -1 : maxDist;
	}

}