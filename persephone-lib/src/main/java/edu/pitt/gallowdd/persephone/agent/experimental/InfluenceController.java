package edu.pitt.gallowdd.persephone.agent.experimental;

import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
//import org.jgrapht.nio.ComponentNameProvider;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.ExportException;
import edu.pitt.gallowdd.persephone.agent.attribute.Race;
import edu.pitt.gallowdd.persephone.agent.attribute.Sex;
import edu.pitt.gallowdd.persephone.controller.Controller;
import edu.pitt.gallowdd.persephone.influence.InfluenceAgent;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * 
 * @author ddg5
 *
 */
public class InfluenceController extends Controller {
  
  /**
   * @param args 
   * 
   */
  public static void main(String[] args)
  {
    // Messing around with positions
//    Position p1 = new Position(0.0);
////    Position p2 = new Position(0.7);
////    Position p3 = Position.add(p1, p2);
//    p1.addX(0.1);
//    System.out.println("p1:\n" + p1.toString());
//    p1.addX(0.2);
//    System.out.println("p1:\n" + p1.toString());
//    p1.addX(0.4);
//    System.out.println("p1:\n" + p1.toString());
//    p1.addX(0.9);
//    System.out.println("p1:\n" + p1.toString());
//    p1.addX(1.9);
//    System.out.println("p1:\n" + p1.toString());
//    p1.addX(10.9);
//    System.out.println("p1:\n" + p1.toString());
//    p1.addX(100.9);
//    System.out.println("p1:\n" + p1.toString());
//    
//    Position p2 = new Position(0.7, -0.1, 0.3);
//    Velocity v1 = new Velocity(0.019, 0.039, 0.079);
//    Acceleration a1 = new Acceleration(0.001, 0.0139, 0.0179);
//    a1.updatePosition(p2, v1, 12);
//    
//    System.out.println("p2.toString():\n" + p2.toString());
//    System.out.println("-----------------------------------\n");
//    Position p3 = new Position(0.09, -0.0019, 0.19);
////    p2.multiply(100);
//    p3.multiply(-100);
//    System.out.println("p2.toString():\n" + p2.toString());
//    System.out.println("p3.toString():\n" + p3.toString());
//    System.out.println("-----------------------------------\n");
//    
//    DeltaVector deltaVec = Position.getDelta(p2, p3);
//    System.out.println("deltaVec = " + deltaVec.toString());
//    p2.add(deltaVec);
//    System.out.println("p2 update = " + p2.toString());
    
    Graph<InfluenceAgent, DefaultWeightedEdge> completeGraph =
        new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    
    InfluenceAgent me;
    InfluenceAgent earl;
    InfluenceAgent dyingGirl;
    InfluenceAgent bobbyMcGee;
    try
    {
      me = new InfluenceAgent(50, Sex.MALE, Race.WHITE);
      earl = new InfluenceAgent(17, Sex.MALE, Race.BLACK);
      dyingGirl = new InfluenceAgent(17, Sex.FEMALE, Race.WHITE);
      bobbyMcGee = new InfluenceAgent(52, Sex.MALE, Race.WHITE);
      completeGraph.addVertex(me);
      completeGraph.addVertex(bobbyMcGee);
      completeGraph.addVertex(earl);
      completeGraph.addVertex(dyingGirl);
      
      
      
      DefaultWeightedEdge e1 = completeGraph.addEdge(earl, dyingGirl);
      completeGraph.setEdgeWeight(e1, .95);
      
      e1 = completeGraph.addEdge(earl, me);
      completeGraph.setEdgeWeight(e1, .05);
      
      e1 = completeGraph.addEdge(me, dyingGirl);
      completeGraph.setEdgeWeight(e1, .8);
      
      e1 = completeGraph.addEdge(me, earl);
      completeGraph.setEdgeWeight(e1, .95);
      
      e1 = completeGraph.addEdge(me, bobbyMcGee);
      completeGraph.setEdgeWeight(e1, .6);
      
      e1 = completeGraph.addEdge(bobbyMcGee, me);
      completeGraph.setEdgeWeight(e1, .2);
      
      
//      ComponentNameProvider<InfluenceAgent> vertexIdProvider = new ComponentNameProvider<InfluenceAgent>()
//      {
//        public String getName(InfluenceAgent agent)
//        {
//          return agent.getId().getIdString().replaceAll("-", "_");
//        }
//      };
      
      
      
      /*
       * 
      
      ComponentNameProvider<V> vertexIDProvider,
                     ComponentNameProvider<V> vertexLabelProvider,
                     ComponentNameProvider<E> edgeLabelProvider,
                     ComponentAttributeProvider<V> vertexAttributeProvider,
                     ComponentAttributeProvider<E> edgeAttributeProvider,
                     ComponentNameProvider<Graph<V,E>> graphIDProvider
      
       */
//      GraphExporter<InfluenceAgent, DefaultWeightedEdge> exporter =
//          new DOTExporter<>(v -> v.g.getHost().replace('.', '_'));
//        exporter.setVertexAttributeProvider((v) -> {
//            Map<String, Attribute> map = new LinkedHashMap<>();
//            map.put("label", DefaultAttribute.createAttribute(v.toString()));
//            return map;
//        });
        
        DOTExporter<InfluenceAgent, DefaultWeightedEdge> exporter = new DOTExporter<>(v -> v.getId().toString());
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.toString()));
            return map;
        });
      Writer writer = new StringWriter();
      try
      {
        exporter.exportGraph(completeGraph, writer);
      }
      catch(ExportException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      System.out.println(writer.toString());
    
    }
    catch(IdException | IllegalArgumentException ex)
    {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }
  }
  
  /**
   * 
   * @param id
   * @throws IdException 
   */
  public InfluenceController(String id) throws IdException
  {
    super(id, null, null, null);
    // TODO Auto-generated constructor stub
  }
  
//  /* (non-Javadoc)
//   * @see java.util.concurrent.Callable#call()
//   */
//  @Override
//  public ControllerOutput call() throws Exception
//  {
//    // TODO Auto-generated method stub
//    return null;
//  }
  
  
  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Controller o)
  {
    // TODO Auto-generated method stub
    return 0;
  }
}
