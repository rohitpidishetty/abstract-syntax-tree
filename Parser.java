package javap.flat.SyntaxAnalyzer;

import javap.flat.LexicalAnalyzer.AbsSynTree_node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
  protected AbsSynTree_node[] nodes;

  public Parser(AbsSynTree_node[] nodes) {
    this.nodes = nodes;
  }

  public Parser normalize() {
    int i = 0, j = 0;
    int size = this.nodes.length;
    while (j < size) {
      boolean contains = false;
      if (this.nodes[j] != null && this.nodes[j].token.equals(")")) {
        contains = true;
        i = j;
        List<AbsSynTree_node> hpop = new ArrayList<>();
        while (this.nodes[i] == null || !this.nodes[i].token.equals("(")) {
          if (this.nodes[i] != null) {
            hpop.addFirst(this.nodes[i]);
            this.nodes[i] = null;
          }
          i--;
        }
        hpop.addFirst(this.nodes[i]);
        hpop = hpop.subList(1, hpop.size() - 1);
        AbsSynTree_node tree = hpop.get(1);
        tree.leftAbsSynTree_node = hpop.get(0);
        tree.rightAbsSynTree_node = hpop.get(2);
        tree.nature = "Sub tree";
        this.nodes[i] = tree;
        i = 0;
        j = 0;
        if (!contains && j == this.nodes.length - 1)
          break;
      }
      j++;
    }
    return this;
  }

  public AbsSynTree_node build_AST() {

    List<AbsSynTree_node> astn = new ArrayList<>();
    for (int i = 0; i < this.nodes.length; i++) {
      if (this.nodes[i] != null)
        astn.add(this.nodes[i]);
    }

    // 1. Normalize (*)
    // 2. Normalize (/)
    // 3. Normalize (%)
    // 4. Normalize (+)
    // 5. Normalize (-)

    int n = astn.size();
    int precedence = 5;
    for (int i = 0; i < astn.size(); i++) {

      if (astn.get(i).nature.equals("Operator")) {

        if (astn.get(i).significance == precedence) {
          AbsSynTree_node tree = astn.get(i);
          tree.leftAbsSynTree_node = astn.get(i - 1);
          tree.rightAbsSynTree_node = astn.get(i + 1);
          tree.nature = "Sub tree";
          astn.set(i, null);
          astn.set(i - 1, null);
          astn.set(i + 1, null);
          astn.set(i + 1, tree);
          List<AbsSynTree_node> temp = new ArrayList<>();
          int j = 0;
          while (j < astn.size()) {
            if (astn.get(j) != null)
              temp.add(astn.get(j));
            j++;
          }
          astn = temp;
          i = 0;
        }
      }
      if (i >= astn.size() - 1) {
        precedence--;
        i = 0;
      }
      if (astn.size() == 1 || precedence < 0)
        break;
    }
    return astn.get(0);
  }
}
