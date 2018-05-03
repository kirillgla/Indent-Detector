package indentDetector;

public enum OpenBraceType {
    Case, // For the purposes of simplicity, I view case label as opening brace.
          // Therefore, break operator that terminates case label is treated as closing brace
    Other // part of if operator, method body, etc
}
