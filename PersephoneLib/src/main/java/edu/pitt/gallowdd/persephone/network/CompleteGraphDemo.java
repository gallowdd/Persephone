package edu.pitt.gallowdd.persephone.network;

import java.util.Iterator;
import java.util.function.Supplier;

import org.jgrapht.Graph;
import org.jgrapht.generate.DirectedScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.util.SupplierUtil;

/**
 * 
 */
public final class CompleteGraphDemo {
  // number of vertices
  private static final int TARGET_EDGES = -1;
  private static final int TARGET_NODES = 30;

  /**
   * Main demo entry point.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args)
  {
    // Create the VertexFactory so the generator can create vertices
    Supplier<Integer> vSupplier = new Supplier<Integer>() {
      private int id = 0;

      @Override
      public Integer get()
      {
        return id++;
      }
    };

    // Create the graph object
    Graph<Integer, DefaultEdge> completeGraph = new SimpleGraph<>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(),
        false);

    // Create the CompleteGraphGenerator object
    DirectedScaleFreeGraphGenerator<Integer, DefaultEdge> completeGenerator = new DirectedScaleFreeGraphGenerator<>(0.5f, 0.5f, 0.9f, 0.9f, TARGET_EDGES, TARGET_NODES, 1L, false, false);

    // Use the CompleteGraphGenerator object to make completeGraph a
    // complete graph with [size] number of vertices
    completeGenerator.generateGraph(completeGraph);

    // Print out the graph to be sure it's really complete
    Iterator<Integer> iter = new DepthFirstIterator<>(completeGraph);
    while(iter.hasNext())
    {
      Integer vertex = iter.next();
      System.out.println("Vertex " + vertex + " is connected to: " + completeGraph.edgesOf(vertex).toString());
    }
  }
}
