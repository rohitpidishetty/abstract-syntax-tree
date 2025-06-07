import java.util.Arrays;

import javap.flat.LexicalAnalyzer.Lexer;
import javap.flat.LexicalAnalyzer.AbsSynTree_node;
import javap.flat.SyntaxAnalyzer.Parser;

public class test {
  public static void main(String[] args) {

    AbsSynTree_node tree = new Parser(
        new Lexer(
            new String("12*((12+2)+4)*2/50"))
            .tokenize()
            .lexify())
        .normalize()
        .build_AST();

    tree.print_AST();
    System.out.println();
    System.out.println(tree.json());
    // To save the file locally use this command.
    try {
      System.out.println(tree.json("./AST.json"));
    } catch (Exception e) {
      System.out.println("Error generating file");
    }

  }
}
