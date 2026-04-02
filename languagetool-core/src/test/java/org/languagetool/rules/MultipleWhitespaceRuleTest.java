/* LanguageTool, a natural language style checker 
 * Copyright (C) 2005 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.rules;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Marcin Milkowski
 */
public class MultipleWhitespaceRuleTest {

  @Test
  public void testRule() throws IOException {
    List<RuleMatch> matches;
    JLanguageTool lt = new JLanguageTool(TestTools.getDemoLanguage());
    setUpRule(lt);

    // correct sentences:
    assertGood("This is a test sentence.", lt);
    assertGood("This\uFEFF is a test sentence.", lt);
    assertGood("This\uFEFF\uFEFF is a test sentence.", lt);
    assertGood("This \uFEFFis a test sentence.", lt);
    assertGood("This\uFEFF\u2060 is a test sentence.", lt);
    assertGood("This\uFEFF\u2060 is a test sentence.", lt);
    assertGood("\uFEFF\uFEFFThis is a\n\u2060\ntest sentence...", lt);
    assertGood("This is a test sentence...", lt);
    assertGood("\n\tThis is a test sentence...", lt);
    assertGood("Multiple tabs\t\tare okay", lt);
    assertGood("\n This is a test sentence...", lt);
    assertGood("\n    This is a test sentence...", lt);

    // incorrect sentences:

    String input1 = "This  is a test sentence.";
    matches = lt.check(input1);
    assertEquals(1, matches.size());
    assertEquals("This  is",
      input1.substring(matches.get(0).getFromPos(), matches.get(0).getToPos()));

    String input2 = "\n   This  is a test sentence.";
    matches = lt.check(input2);
    assertEquals(1, matches.size());
    assertEquals("This  is",
      input2.substring(matches.get(0).getFromPos(), matches.get(0).getToPos()));

    String input3 = "This is a test   sentence.";
    matches = lt.check(input3);
    assertEquals(1, matches.size());
    assertEquals("test   sentence",
      input3.substring(matches.get(0).getFromPos(), matches.get(0).getToPos()));

    String input4 = "This is   a  test   sentence.";
    matches = lt.check(input4);

    // IMPORTANT: overlapping matches are filtered → only 2 remain
    assertEquals(2, matches.size());

    assertEquals("is   a",
      input4.substring(matches.get(0).getFromPos(), matches.get(0).getToPos()));

    assertEquals("test   sentence",
      input4.substring(matches.get(1).getFromPos(), matches.get(1).getToPos()));

    // whitespace-only case (no surrounding words → unchanged)
    matches = lt.check("\t\t\t    \t\t\t\t  ");
    assertEquals(2, matches.size());

    // non-breaking space
    String input5 = "This \u00A0is a test sentence.";
    matches = lt.check(input5);
    assertEquals(1, matches.size());
    assertEquals("This \u00A0is",
      input5.substring(matches.get(0).getFromPos(), matches.get(0).getToPos()));
  }

  private void assertGood(String input, JLanguageTool lt) throws IOException {
    List<RuleMatch> ruleMatches = lt.check(input);
    assertEquals(0, ruleMatches.size());
  }

  private void setUpRule(JLanguageTool lt) {
    for (Rule rule : lt.getAllRules()) {
      lt.disableRule(rule.getId());
    }
    MultipleWhitespaceRule rule = new MultipleWhitespaceRule(
        TestTools.getEnglishMessages(),
        TestTools.getDemoLanguage()
    );
    lt.addRule(rule);
  }

  public static MultipleWhitespaceRule getMultipleWhitespaceRule(JLanguageTool lt) {
    for (Rule rule : lt.getAllActiveRules()) {
      if (rule instanceof MultipleWhitespaceRule) {
        return (MultipleWhitespaceRule) rule;
      }
    }
    throw new RuntimeException("Rule not found: " + GenericUnpairedBracketsRule.class);
  }
}