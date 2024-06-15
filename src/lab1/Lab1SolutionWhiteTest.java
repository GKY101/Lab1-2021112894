package lab1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Lab1SolutionTest {

  @Test
  void testQueryBridgeWords1() {
    Lab1Solution.buildGraph();
    String result = Lab1Solution.queryBridgeWords("apple", "banana");
    assertEquals("No \"apple\" or \"banana\" in the graph!\n", result);
  }

  @Test
  void testQueryBridgeWords2() {
    Lab1Solution.buildGraph();
    String result = Lab1Solution.queryBridgeWords("apple", "in");
    assertEquals("No \"apple\" in the graph!\n", result);
  }

  @Test
  void testQueryBridgeWords3() {
    Lab1Solution.buildGraph();
    String result = Lab1Solution.queryBridgeWords("in", "banana");
    assertEquals("No \"banana\" in the graph!\n", result);
  }

  @Test
  void testQueryBridgeWords4() {
    Lab1Solution.buildGraph();
    String result = Lab1Solution.queryBridgeWords("a", "t");
    assertEquals("No bridge words from \"a\" to \"t\"!\n", result);
  }

  @Test
  void testQueryBridgeWords5() {
    Lab1Solution.buildGraph();
    String result = Lab1Solution.queryBridgeWords("t", "in");
    assertEquals("No bridge words from \"t\" to \"in\"!\n", result);
  }

  @Test
  void testQueryBridgeWords7() {
    Lab1Solution.buildGraph();
    String result = Lab1Solution.queryBridgeWords("of", "evidence");
    assertEquals("The bridge words from \"of\" to \"evidence\" is: \"highresolution\".\n", result);
  }

  @Test
  void testQueryBridgeWords8() {
    Lab1Solution.buildGraph();
    String result = Lab1Solution.queryBridgeWords("in", "species");
    assertEquals("The bridge words from \"in\" to \"species\" are: \"the\", \"other\".\n", result);
  }
}