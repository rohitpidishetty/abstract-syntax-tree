package javap.flat.LexicalAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Lexer {
  private String stream;
  private String SYMBOLS = "[*|-|+|/|%|)]";
  private char NULL = '\0';
  private char OP_BRACKER = '(';
  private List<String> tokens = new ArrayList<>();

  public Lexer(String stream) {
    this.stream = new String(stream);
  }

  public Lexer tokenize() {
    char[] charTokens = this.stream.toCharArray();
    int i = 0, j = 0;
    while (j < charTokens.length) {
      while (!Pattern.matches(SYMBOLS, String.valueOf(charTokens[j])) && j != charTokens.length - 1)
        j++;
      String number = this.stream.substring(i, j);
      if (j == charTokens.length - 1) {
        tokens.add(this.stream.substring(i, j + 1));
        break;
      }
      if (number.length() > 0)
        tokens.add(number);
      if (this.stream.charAt(j) != NULL)
        tokens.add(String.valueOf(this.stream.charAt(j)));
      j++;
      i = j;
      while (charTokens[i] == OP_BRACKER) {
        tokens.add(String.valueOf(this.stream.charAt(i++)));
        j++;
      }
    }
    return this;
  }

  public List<String> getTokens() {
    return this.tokens;
  }

  public AbsSynTree_node[] lexify() {
    int i = 0;
    int size = this.tokens.size();
    AbsSynTree_node[] lexTokens = new AbsSynTree_node[size];
    while (i < size)
      lexTokens[i] = new AbsSynTree_node(this.tokens.get(i++));
    return lexTokens;
  }
}