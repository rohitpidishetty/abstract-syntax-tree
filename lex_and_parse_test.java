import java.util.Arrays;

import flat.LexicalAnalyzer.Lexer;
import flat.LexicalAnalyzer.AbsSynTree_node;
import flat.SyntaxAnalyzer.Parser;

public class lex_and_parse_test {
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

  }
}
