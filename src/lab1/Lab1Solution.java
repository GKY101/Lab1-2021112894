package lab1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.concurrent.ThreadLocalRandom;
@SuppressWarnings({"checkstyle:Indentation", "checkstyle:LineLength",
    "checkstyle:EmptyLineSeparator", "checkstyle:MissingJavadocType"})
public class Lab1Solution {

  @SuppressWarnings("checkstyle:Indentation")
  public static Map<String, Node> nodes = new HashMap<>();
  @SuppressWarnings("checkstyle:Indentation")
  public static List<Edge> edges = new ArrayList<>();

  @SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:Indentation"})
  static String filename = "input.txt";
  @SuppressWarnings("checkstyle:Indentation")
  static Path filePath = Paths.get("input.txt");
  @SuppressWarnings("checkstyle:Indentation")
  static String content;
  @SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:Indentation"})
  static int have_build_map = 0;
  @SuppressWarnings("checkstyle:Indentation")
  static List<String> bridgeWords = new ArrayList<>();
  @SuppressWarnings("checkstyle:Indentation")
  static Map<Node, Integer> distanceMap = new HashMap<>();

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
  @SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:Indentation"})
  Lab1Solution(String filename) {
    this.filename = filename;
  }
  //String content = new String(Files.readAllBytes(filePath));

  @SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:Indentation"})
  static void clear() {
    nodes.clear();
    edges.clear();
    have_build_map = 0;
  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:WhitespaceAround",
      "checkstyle:WhitespaceAfter"})
  static void edges_plus(Edge e) {
    for (Edge existingEdge : edges) {
      //if (existingEdge.equals(edge)) {
      if (existingEdge.eq(e) == 1) {
        existingEdge.weight++;
        break;
      }
    }
  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:WhitespaceAround",
      "checkstyle:FileTabCharacter", "checkstyle:WhitespaceAfter"})
  public static int buildGraph() {
    if (have_build_map == 1) {
      clear();
    }
    filePath = Paths.get(filename);
    int flag = 1;
    try {
      content = new String(Files.readAllBytes(filePath));
    } catch (IOException e) {
      //e.printStackTrace();
      flag = 0;
    }
    if (flag == 0) {
      return 0;
    }
    // 预处理文本：去除标点符号，忽略大小写，将换行视为空格
    String content0 = content.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
    String[] words = content0.split("\\s+");

    // 构建节点和边
    for (int i = 0; i < words.length - 1; i++) {
      String currentWord = words[i];
      String nextWord = words[i + 1];

      // 添加或获取当前单词的节点
      Node currentNode = nodes.computeIfAbsent(currentWord, k -> new Node(k));

      // 添加或获取下一个单词的节点
      Node nextNode = nodes.computeIfAbsent(nextWord, k -> new Node(k));

      // 添加边，同时增加权重
      int f = 1;
      Edge edge = new Edge(currentNode, nextNode, 1);
      for (Edge existingEdge : currentNode.outgoingEdges) {
        if (existingEdge.eq(edge) == 1) {
          existingEdge.weight++;
          f = 0;
          //edges_plus(edge);
          break;
        }

      }
      if (f == 1) {
        currentNode.outgoingEdges.add(edge);
        edges.add(edge);
      }
    }
    have_build_map = 1;
    return 1;
  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:WhitespaceAround"})
  static String showDirectedGraph() {
    String result = "graph";
    String tmp;
    // 打印节点和边信息，代替图形展示
    for (Node node : nodes.values()) {
      tmp = node.toString();
      //System.out.println(node);
      //System.out.println(tmp);
      //result.concat(tmp);
      result = result + "\n" + tmp;
    }
    for (Edge edge : edges) {
      //System.out.println(edge);
      tmp = edge.toString();
      //System.out.println(node);
      //System.out.println(tmp);
      //result.concat(tmp);
      result = result + "\n" + tmp;
    }
    return result;
  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:WhitespaceAfter",
      "checkstyle:WhitespaceAround", "checkstyle:RightCurly"})
  static int draw_graph() {
    File file = new File("tmp.dot");
    try {
      file.createNewFile();
    } catch (Exception e) {
      return 0;
    }
    FileWriter fw;
    try {
      //RandomAccessFile raf=new RandomAccessFile(file,"w");
      fw = new FileWriter("tmp.dot");

      String text = "digraph G {\n";
      fw.write(text);
      for (Edge e : edges) {
        text = e.from.word + " -> " + e.to.word + "[label=" + e.weight + ", weight=" + e.weight
            + "] ;\n";
        fw.write(text);
      }
      text = "\n}";
      fw.write(text);

      fw.close();

      String cmd = "dot -Tpng tmp.dot -o result.png";
      Runtime.getRuntime().exec(cmd);
      cmd = "cmd /c start result.png";
      Runtime.getRuntime().exec(cmd);
    } catch (Exception e) {
      return 2;
    }

    //FileChannel fc=raf.getChannel();

    return 1;

  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:WhitespaceAfter",
      "checkstyle:WhitespaceAround", "checkstyle:LineLength", "checkstyle:RightCurly"})
  public static String queryBridgeWords(String word1, String word2) {
    Node startNode = nodes.get(word1.toLowerCase());
    Node endNode = nodes.get(word2.toLowerCase());

    // 检查两个单词是否都在图中
    if (startNode == null && endNode == null) {
      return "No \"" + word1 + "\" or \"" + word2 + "\" in the graph!\n";
    }
    if (startNode == null) {
      return "No \"" + word1 + "\" in the graph!\n";
    }
    if (endNode == null) {
      return "No \"" + word2 + "\" in the graph!\n";
    }
    bridgeWords.clear();
    // 使用广度优先搜索(BFS)或深度优先搜索(DFS)寻找桥接词
    //List<String> bridgeWords = new ArrayList<>();
    for (Edge edge : startNode.outgoingEdges) {
      //dfsFindBridgeWords(edge.to, endNode, bridgeWords, visited);
      for (Edge edge0 : edge.to.outgoingEdges) {
        if (edge0.to.equals(endNode)) {
          bridgeWords.add("\"" + edge0.from.word + "\"");
        }
      }
    }

    // 处理结果
    if (bridgeWords.isEmpty()) {
      return "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!\n";
    } else {
      if (bridgeWords.size() == 1) {
        return "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" is: " + String.join(
            ", ", bridgeWords) + ".\n";
        //return "The bridge words from \"" + word1 + "\" to \"" + word2 + " \"are: " + String.join(", ","\""+ bridgeWords+"\"") + ".\n";
      } else {
        return "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: " + String.join(
            ", ", bridgeWords) + ".\n";
      }
    }
  }

  @SuppressWarnings("checkstyle:Indentation")
  private static void dfsFindBridgeWords(Node currentNode, Node targetNode,
      List<String> bridgeWords, Set<Node> visited) {
    visited.add(currentNode);

    // 检查当前节点是否为目标节点，直接前驱节点才构成桥接词
    if (currentNode.equals(targetNode)) {
      // 需要上一层递归调用决定是否是桥接词
      return;
    }

    for (Edge edge : currentNode.outgoingEdges) {
      Node nextNode = edge.to;
      if (!visited.contains(nextNode)) {

        if (nextNode.equals(targetNode) && edge.from.equals(currentNode)) {
          bridgeWords.add(currentNode.word);
          // 检查当前节点是否是桥接词，需确保nextNode已经是targetNode且edge的源节点是currentNode
          dfsFindBridgeWords(nextNode, targetNode, bridgeWords, visited);
        }
      }
    }
  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:WhitespaceAfter",
      "checkstyle:WhitespaceAround"})
  static String getBridgeWords(String word1, String word2) {
    Node startNode = nodes.get(word1.toLowerCase());
    Node endNode = nodes.get(word2.toLowerCase());

    // 检查两个单词是否都在图中
    if (startNode == null || endNode == null) {
      return ",";
    }

    bridgeWords.clear();
    // 使用广度优先搜索(BFS)或深度优先搜索(DFS)寻找桥接词
    //List<String> bridgeWords = new ArrayList<>();
    //Set<Node> visited = new HashSet<>();
    for (Edge edge : startNode.outgoingEdges) {
      //dfsFindBridgeWords(edge.to, endNode, bridgeWords, visited);
      for (Edge edge0 : edge.to.outgoingEdges) {
        if (edge0.to.equals(endNode)) {
          bridgeWords.add(edge0.from.word);
        }
      }
    }

    // 处理结果
    if (bridgeWords.isEmpty()) {
      return ",";
    } else {
      //return bridgeWords.get(0);
      return "";
    }
  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:WhitespaceAround"})
  static String generateNewText(String inputText) {
    String[] words = inputText.toLowerCase().split("\\s+");
    StringBuilder output = new StringBuilder();
    bridgeWords.clear();
    int j = 0;
    for (int i = 0; i < words.length - 1; i++) {
      output.append(words[i]);
      // If a bridge word exists, append it with surrounding spaces
      System.out.println(getBridgeWords(words[i], words[i + 1]));
      if ((bridgeWords.size() > 0)) {
        //j = bridgeWords.size();
        String bridge = bridgeWords.get(0);
        System.out.println(bridge);
        output.append(" ").append(bridge);
      }
      output.append(" ");
    }

    // Append the last word
    output.append(words[words.length - 1]);

    // Directly return the modified text; no need to print "bridge" separately
    return output.toString();
  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:LineLength",
      "checkstyle:WhitespaceAround", "checkstyle:WhitespaceAfter", "checkstyle:NeedBraces"})
  static String calcShortestPath(String word1, String word2) {
    if (!(nodes.containsKey(word1) && nodes.containsKey(word2))) {
      return "nodes not in map!";
    }
    // 假设已经有一个方法findShortestPath可以找到最短路径
    List<List<String>> shortestPaths = findAllShortestPaths(nodes.get(word1), nodes.get(word2));
    String tmp = "";
    for (int i = 0; i < shortestPaths.size(); i++) {
      tmp = tmp + "Path " + (i + 1) + ": " + String.join(" -> ", shortestPaths.get(i)) + " -> "
          + word2;
      tmp += "  " + String.valueOf(distanceMap.get(nodes.get(word2))) + "\n";
      //System.out.println("Path " + (i + 1) + ": " + String.join(" -> ", shortestPaths.get(i))+word2);
    }
    return tmp;
    //return shortestPaths.get(0).get(0);
  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:WhitespaceAround",
      "checkstyle:CommentsIndentation", "checkstyle:FileTabCharacter",
      "checkstyle:NoWhitespaceBefore", "checkstyle:WhitespaceAfter", "checkstyle:LineLength",
      "checkstyle:VariableDeclarationUsageDistance"})
  static String calcShortestPath(String word1) {
    if (!nodes.containsKey(word1)) {
      return word1 + " is not in the map!";
    }
    Node startNode = nodes.get(word1);

    Map<Node, Integer> distanceMap = new HashMap<>();
    //Map<Node, List<List<Node>>> pathsMap = new HashMap<>();
    Map<Node, List<List<Node>>> pathsMap = new HashMap<>();
    String result = "";

    // 初始化
    for (Node node : nodes.values()) {
      distanceMap.put(node, Integer.MAX_VALUE);
      pathsMap.put(node, new ArrayList<>()); // 每个节点初始一个空路径列表
    }
    distanceMap.put(startNode, 0);
    pathsMap.get(startNode).add(new ArrayList<>()); // 起点的路径列表添加一个空路径

    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(distanceMap::get));
    queue.offer(startNode);
    List<List<String>> allPaths = new ArrayList<>();
    String s;
    Node currentNode;
    // Dijkstra算法核心
    while (!queue.isEmpty()) {
      currentNode = queue.poll();
      //System.out.println(pathsMap.get(currentNode).size());
      //System.out.println(distanceMap.get(currentNode));
      for (Edge edge : currentNode.outgoingEdges) {
        Node neighbor = edge.to;
        int newDistance = distanceMap.get(currentNode) + edge.weight;

        if (newDistance < distanceMap.get(neighbor)) {
          distanceMap.put(neighbor, newDistance);
          pathsMap.get(neighbor).clear(); // 清除旧路径，因为找到了更短的路径
          pathsMap.get(neighbor)
              .addAll(cloneAndAppendToAll(pathsMap.get(currentNode), currentNode));
          //pathsMap.get(neighbor).
          queue.offer(neighbor);
        } else if (newDistance == distanceMap.get(neighbor)) {
          // 相同距离，收集路径
          pathsMap.get(neighbor)
              .addAll(cloneAndAppendToAll(pathsMap.get(currentNode), currentNode));
        }
        System.out.println(pathsMap.get(neighbor).size());
      }/*
              allPaths.clear();
              for (List<Node> path : pathsMap.getOrDefault(currentNode, Collections.emptyList())) {
                  if (!path.isEmpty()) {
                      allPaths.add(path.stream().map(node -> node.word).collect(Collectors.toList()));
                  }
              }
              */
      Iterator<Node> it;
      //for(List<String> t1:allPaths) {
      //
      //System.out.println(pathsMap.get(currentNode).size());
      //if(allPaths.size()==0)continue;
      //List<String> t1=allPaths.get(0);
      List<Node> t1 = pathsMap.get(currentNode).get(0);
      System.out.println(t1.size());
      for (it = t1.iterator(); it.hasNext(); ) {
        s = it.next().word;
        result += s;

        result += "->";
        //currentNode=nodes.get(it.next());

      }
      result += currentNode.word;//modified
      //result+=String.join(" -> ", t1);
      //currentNode=nodes.get(t1.get(t1.size()-1));
      //it.
      result += "  " + String.valueOf(distanceMap.get(currentNode));
        	  /*
        	  for(String t2:t1) {
        		  result+=t2+"->";
        	  }*/
      result += "\n";
    }
    //result+=allPaths;

    //}
    return result;

  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:LineLength"})
  static List<List<String>> findAllShortestPaths(Node startNode, Node endNode) {
    //Map<Node, Integer> distanceMap = new HashMap<>();
    Map<Node, List<List<Node>>> pathsMap = new HashMap<>();

    // 初始化
    for (Node node : nodes.values()) {
      distanceMap.put(node, Integer.MAX_VALUE);
      pathsMap.put(node, new ArrayList<>()); // 每个节点初始一个空路径列表
    }
    distanceMap.put(startNode, 0);
    pathsMap.get(startNode).add(new ArrayList<>()); // 起点的路径列表添加一个空路径

    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(distanceMap::get));
    queue.offer(startNode);

    // Dijkstra算法核心
    while (!queue.isEmpty()) {
      Node currentNode = queue.poll();

      for (Edge edge : currentNode.outgoingEdges) {
        Node neighbor = edge.to;
        int newDistance = distanceMap.get(currentNode) + edge.weight;

        if (newDistance < distanceMap.get(neighbor)) {
          distanceMap.put(neighbor, newDistance);
          pathsMap.get(neighbor).clear(); // 清除旧路径，因为找到了更短的路径
          pathsMap.get(neighbor)
              .addAll(cloneAndAppendToAll(pathsMap.get(currentNode), currentNode));
          //pathsMap.get(neighbor).
          queue.offer(neighbor);
        } else if (newDistance == distanceMap.get(neighbor)) {
          // 相同距离，收集路径
          pathsMap.get(neighbor)
              .addAll(cloneAndAppendToAll(pathsMap.get(currentNode), currentNode));
        }
      }
    }

    // 构建并返回所有最短路径的字符串表示
    List<List<String>> allPaths = new ArrayList<>();
    for (List<Node> path : pathsMap.getOrDefault(endNode, Collections.emptyList())) {
      if (!path.isEmpty()) {
        allPaths.add(path.stream().map(node -> node.word).collect(Collectors.toList()));

      }
    }
    return allPaths.isEmpty() ? Collections.singletonList(
        Collections.singletonList("No path from " + startNode.word + " to " + endNode.word))
        : allPaths;
  }

  // 辅助方法，复制路径列表并为每个路径添加新节点
  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:LineLength"})
  private static List<List<Node>> cloneAndAppendToAll(List<List<Node>> originalPaths,
      Node newNode) {
    List<List<Node>> result = new ArrayList<>();
    for (List<Node> path : originalPaths) {
      List<Node> newPath = new ArrayList<>(path);
      newPath.add(newNode);
      result.add(newPath);
    }
    return result;
  }
  public static Node selectRandomNode() {
    // 使用ThreadLocalRandom优化单次随机数生成，避免创建不必要的Random实例
    int randomIndex = ThreadLocalRandom.current().nextInt(nodes.size());
    return nodes.values().stream()
        .skip(randomIndex)
        .findFirst()
        .orElse(null);
  }

  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:FileTabCharacter",
      "checkstyle:WhitespaceAround", "checkstyle:LocalVariableName", "checkstyle:WhitespaceAfter",
      "checkstyle:NeedBraces"})
  static String randomWalk() {
    if (nodes.isEmpty()) {
      return "Graph is empty!";
    }
    Node current = selectRandomNode();
    if (current == null) {
      return "Error starting walk";
    }

    TreeMap<String, Integer> nodes_had_gone = new TreeMap<>();
    //nodes_had_gone.put(current.word, 1);
    StringBuilder path = new StringBuilder(current.word);
    while (!current.outgoingEdges.isEmpty() && !nodes_had_gone.containsKey(current.word)) {

      List<Edge> outgoing = new ArrayList<>(current.outgoingEdges);
      Edge nextEdge = outgoing.get(new Random().nextInt(outgoing.size()));
      nodes_had_gone.put(current.word, 1);
      current = nextEdge.to;
      path.append(" -> ").append(current.word);
            /*
            if(!nodes_had_gone.containsKey(current.word)) {
            	path.append(" -> ").append(current.word);
            }
            else {
            	break;
            }*/
    }
    return "Random Walk: " + path.toString();
  }

  // 辅助类定义
  @SuppressWarnings("checkstyle:Indentation")
  public static class Node {

    @SuppressWarnings("checkstyle:Indentation")
    String word;
    @SuppressWarnings("checkstyle:Indentation")
    Set<Edge> outgoingEdges;

    @SuppressWarnings("checkstyle:Indentation")
    Node(String word) {
      this.word = word;
      this.outgoingEdges = new HashSet<>();
    }


    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public String toString() {
      return "Node{" + word + '}';
    }
  }

  @SuppressWarnings("checkstyle:Indentation")
  public static class Edge {

    @SuppressWarnings("checkstyle:Indentation")
    Node from;
    @SuppressWarnings("checkstyle:Indentation")
    Node to;
    @SuppressWarnings("checkstyle:Indentation")
    int weight;

    @SuppressWarnings("checkstyle:Indentation")
    Edge(Node from, Node to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public String toString() {
      return "Edge{" + from.word + " -> " + to.word + ", weight=" + weight + '}';
    }

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:WhitespaceAround",
        "checkstyle:WhitespaceAfter"})
    public int eq(Edge e) {
      if (e.from == this.from && e.to == this.to) {
        return 1;
      }
      return 0;
    }
  }
}
