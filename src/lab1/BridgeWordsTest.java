package lab1;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Rule;
import org.junit.Test;

public class BridgeWordsTest {
  @Test
  public void testBridgeWordsNotExist() {
    Lab1Solution.buildGraph();
    // 单词不在有向图中
    String result = Lab1Solution.calcShortestPath("apple", "banana");
    assertEquals("nodes not in map!", result);
  }

  @Test
  public void testBridgeWordsExistWithNoPath() {
    Lab1Solution.buildGraph();
    // 单词存在但是没有
    String result = Lab1Solution.calcShortestPath("species", "have");
    assertEquals("Path 1: No path from species to have -> have  2147483647\n", result);
  }

  @Test
  public void testBothWordsNotExist() {
    Lab1Solution.buildGraph();
    // 两个单词都不存在
    String result = Lab1Solution.calcShortestPath("used", "as");
    assertEquals("Path 1: used -> regularly -> as  2\n", result);
  }
@Test
  public void testBothWordsExistWithPath() {
    Lab1Solution.buildGraph();
    String result = Lab1Solution.calcShortestPath("used");
    assertEquals("used  0\n"
        + "used->regularly  1\n"
        + "used->regularly->as  2\n"
        + "used->regularly->as->sources  3\n"
        + "used->regularly->as->sources->of  4\n"
        + "used->regularly->as->sources->of->highresolution  5\n"
        + "used->regularly->as->sources->of->sequencing  5\n"
        + "used->regularly->as->sources->of->highresolution->evidence  6\n"
        + "used->regularly->as->sources->of->sequencing->have  6\n"
        + "used->regularly->as->sources->of->sequencing->instruments  6\n"
        + "used->regularly->as->sources->of->sequencing->datasets  6\n"
        + "used->regularly->as->sources->of->sequencing->instruments->are  7\n"
        + "used->regularly->as->sources->of->sequencing->have->both  7\n"
        + "used->regularly->as->sources->of->highresolution->evidence->for  7\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened  8\n"
        + "used->regularly->as->sources->of->highresolution->evidence->for->genotyping  8\n"
        + "used->regularly->as->sources->of->sequencing->instruments->are->now  8\n"
        + "used->regularly->as->sources->of->highresolution->evidence->for->genotyping->methylation  9\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its  9\n"
        + "used->regularly->as->sources->of->highresolution->evidence->for->genotyping->methylation->profiling  10\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility  10\n"
        + "used->regularly->as->sources->of->highresolution->evidence->for->genotyping->methylation->profiling->dnaprotein  11\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and  11\n"
        + "used->regularly->as->sources->of->highresolution->evidence->for->genotyping->methylation->profiling->dnaprotein->interaction  12\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->in  12\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->characterizing  12\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->dramatically  12\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->characterizing->gene  13\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->dramatically->increased  13\n"
        + "used->regularly->as->sources->of->highresolution->evidence->for->genotyping->methylation->profiling->dnaprotein->interaction->mapping  13\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->in->dna  13\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->in->the  13\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->in->other  13\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->in->the->human  14\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->in->other->species  14\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->characterizing->gene->expression  14\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->in->the->size  14\n"
        + "used->regularly->as->sources->of->sequencing->have->both->broadened->its->utility->and->in->the->human->genome  15\n", result);
  }
  @Test
  public void testBothWordsExistWithNoPath() {
    Lab1Solution.buildGraph();
    String result = Lab1Solution.calcShortestPath("hey");
    assertEquals("hey is not in the map!", result);
  }

  // 可以继续添加更多测试用例，如多个桥接词的情况、空字符串输入等
}