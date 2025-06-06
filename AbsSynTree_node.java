package javap.flat.LexicalAnalyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public class AbsSynTree_node {
  public String type;
  public String token;
  public String nature;
  public int significance = -1;
  public AbsSynTree_node leftAbsSynTree_node = null;
  public AbsSynTree_node rightAbsSynTree_node = null;

  public AbsSynTree_node(String token) {
    this.token = token;
    switch (token) {
      case "+":
        this.type = "Addition";
        this.nature = "Operator";
        this.significance = 2;
        break;
      case "-":
        this.type = "Subtraction";
        this.nature = "Operator";
        this.significance = 1;
        break;
      case "/":
        this.type = "Division";
        this.nature = "Operator";
        this.significance = 4;
        break;
      case "*":
        this.type = "Multiplication";
        this.nature = "Operator";
        this.significance = 5;
        break;
      case "%":
        this.type = "Modulo";
        this.nature = "Operator";
        this.significance = 3;
        break;
      case "(":
        this.type = "Opening bracket";
        break;
      case ")":
        this.type = "Closing bracket";
        break;
      default:
        this.type = "32 bit Integer";
        this.nature = "Operand";
        this.significance = 0;
    }
  }

  private class LevelOrder {
    protected AbsSynTree_node node;
    protected int level;

    LevelOrder(AbsSynTree_node node, int level) {
      this.node = node;
      this.level = level;
    }
  }

  private void levelOrderTraversal(int level, AbsSynTree_node node) {
    Queue<AbsSynTree_node.LevelOrder> queue = new ArrayDeque<>();
    queue.offer(new LevelOrder(node, level));
    while (!queue.isEmpty()) {
      LevelOrder currentNode = queue.poll();
      if (currentNode.level > level) {
        level = currentNode.level;
        System.out.println();
      }
      if (currentNode.node != null && level == currentNode.level)
        System.out.print(currentNode.node.token + " ");
      if (currentNode.node.leftAbsSynTree_node != null)
        queue.offer(new LevelOrder(currentNode.node.leftAbsSynTree_node, currentNode.level + 1));
      if (currentNode.node.rightAbsSynTree_node != null)
        queue.offer(new LevelOrder(currentNode.node.rightAbsSynTree_node, currentNode.level + 1));
    }
  }

  public void print_AST() {
    levelOrderTraversal(0, this);
  }

  public String json() {
    StringBuilder sb = new StringBuilder();
    toJson(this, sb, 0);
    return sb.toString();
  }

  public String json(String filename) throws Exception {
    StringBuilder sb = new StringBuilder();
    toJson(this, sb, 0);
    File file = new File(filename);
    FileWriter write = new FileWriter(file);
    BufferedWriter writer = new BufferedWriter(write);
    writer.write(sb.toString());
    writer.close();
    write.close();
    return "File " + filename + " has been generated";
  }

  private void toJson(AbsSynTree_node node, StringBuilder sb, int padding) {
    String pad = " ".repeat(padding);
    String innerIndent = " ".repeat(padding + 2);
    if (node == null) {
      sb.append("null");
      return;
    }
    sb.append("{\n");
    sb.append(innerIndent).append("\"token\": \"").append(node.token).append("\", \n");
    sb.append(innerIndent).append("\"type\": \"").append(node.type).append("\", \n");
    sb.append(innerIndent).append("\"nature\": \"").append(node.nature).append("\", \n");
    sb.append(innerIndent).append("\"significance\": ").append(node.significance).append(", \n");
    sb.append(innerIndent).append("\"left\": ");
    toJson(node.leftAbsSynTree_node, sb, padding + 2);
    sb.append(", \n");
    sb.append(innerIndent).append("\"right\": ");
    toJson(node.rightAbsSynTree_node, sb, padding + 2);
    sb.append("\n");
    sb.append(pad).append("}");
  }

}
