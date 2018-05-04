package indentDetector;

public enum OpenBraceType {
    Case, // For the purposes of simplicity, I view `case` label as opening brace.
          // Therefore, `break` operator that terminates case label is treated as closing brace
          // `default` is treated same as `case`
    Other // part of if operator, method body, etc
}
