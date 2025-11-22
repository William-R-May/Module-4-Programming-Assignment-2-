import java.io.*;
import java.util.*;

public class KeywordCounter {
    private static final Set<String> JAVA_KEYWORDS = Set.of(
        "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
        "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
        "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
        "interface", "long", "native", "new", "package", "private", "protected", "public",
        "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
        "throw", "throws", "transient", "try", "void", "volatile", "while"
    );

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java KeywordCounter <JavaFile.java>");
            return;
        }

        File file = new File(args[0]);
        Scanner scanner = new Scanner(file);

        boolean inBlockComment = false;
        int keywordCount = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            // Skip empty lines
            if (line.isEmpty()) continue;

            // Handle block comments
            if (inBlockComment) {
                if (line.contains("*/")) {
                    inBlockComment = false;
                    line = line.substring(line.indexOf("*/") + 2);
                } else {
                    continue;
                }
            }

            // Remove line comments
            if (line.contains("//")) {
                line = line.substring(0, line.indexOf("//"));
            }

            // Handle start of block comment
            if (line.contains("/*")) {
                inBlockComment = true;
                line = line.substring(0, line.indexOf("/*"));
            }

            // Remove string literals
            line = line.replaceAll("\"(\\\\.|[^\"\\\\])*\"", "");

            // Tokenize and count keywords
            String[] tokens = line.split("[\\s\\p{Punct}]+");
            for (String token : tokens) {
                if (JAVA_KEYWORDS.contains(token)) {
                    keywordCount++;
                }
            }
        }

        scanner.close();
        System.out.println("Total Java keywords (excluding comments and strings): " + keywordCount);
    }
}
