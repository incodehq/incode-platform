package org.isisaddons.module.stringinterpolator.dom;

import ognl.Ognl;
import ognl.OgnlException;

class StringInterpolatorHelper {

    private final String template;
    private final boolean strict;
    private StringInterpolatorService.Root root;

    StringInterpolatorHelper(final String template, final StringInterpolatorService.Root root, final boolean strict) {
        this.template = template;
        this.root = root;
        this.strict = strict;
    }

    String interpolate() {

        final StringBuilder buffer = new StringBuilder();

        int start;
        int pos = 0;

        while ((start = template.indexOf("${", pos)) != -1) {

            // Append text before possible expression
            buffer.append(template.substring(pos, start));

            // Position is now where we found the "${"
            pos = start;

            // Get start and end of expression
            final int startExpressionPos = start + 2;
            final int endExpressionPos = template.indexOf('}', startExpressionPos);
            if (endExpressionPos == -1) {
                break;
            }

            pos = interpolate(buffer, startExpressionPos, endExpressionPos);
        }

        appendRemainder(buffer, pos);

        return buffer.toString();
    }

    private int interpolate(final StringBuilder buffer, final int startExpressionPos, final int endExpressionPos) {
        final String expression = template.substring(startExpressionPos, endExpressionPos);
        final String str = evaluate(expression);
        buffer.append(str);
        return endExpressionPos + 1;
    }

    private String evaluate(final String expression) {
        final String phraseValue = evaluateOgnl(expression);
        return phraseValue != null ? phraseValue : "${" + expression + "}";
    }

    private String evaluateOgnl(final String expression) {
        try {
            Object value = Ognl.getValue(expression, root);
            return value != null ? value.toString() : "";
        } catch (OgnlException ex) {
            if (strict) {
                throw new RuntimeException("could not parse: " + expression, ex);
            }
            return null;
        }
    }

    private void appendRemainder(final StringBuilder buffer, final int pos) {
        if (pos < template.length()) {
            buffer.append(template.substring(pos));
        }
    }
}

