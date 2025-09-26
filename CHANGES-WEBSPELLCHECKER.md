# WebSpellChecker changes for LanguageTool

This document records WebSpellChecker-specific changes on top of upstream LanguageTool.

## 2025-09-26

### Bugfix
- **Hunspell:** Windows path handling — replace `Path.of(url.getPath())` with `Paths.get(url.toURI())` to correctly resolve file paths on Windows.
  - **Scope:** `languagetool-core/src/main/java/org/languagetool/rules/spelling/hunspell/Hunspell.java`.

## 2025-09-23

### Rules

- [es] Enabled the **AGREEMENT_SER_ADJ_PL** rule by default.
- [it] Enabled the **GR_02_001** rule by default.

## 2025-09-19

### Security
- **MyBatis removal:** Removed `org.mybatis:mybatis` to eliminate embedded `ognl:ognl` exposure and address **CVE-2025-53192**.
  - Scope: components depending on `org.mybatis:mybatis` (direct or transitive) packaging of `langtool/libs`.

## 2025-09-09

### Security
- **Netty upgrade:** Updated Netty to **4.1.126.Final** to address **CVE-2025-58057**.
  - Scope: components depending on `io.netty` (direct or transitive).
