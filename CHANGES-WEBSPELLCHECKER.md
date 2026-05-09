# WebSpellChecker changes for LanguageTool

This document records WebSpellChecker-specific changes on top of upstream LanguageTool.

## 2026-05-09

### Security
- **OpenNLP upgrade:** Updated `opennlp-tools` to **2.5.9** to address **CVE-2026-42027**, **CVE-2026-40682** and **CVE-2026-42440**.
  - Scope: components depending on `opennlp-tools` (direct or transitive) packaging of `langtool/libs`.

## 2026-05-07

### Security
- **Netty upgrade:** Updated `io.netty` to **4.1.133.Final** to address **CVE-2026-42579** and **CVE-2026-42583**.
  - Scope: components depending on `io.netty` (direct or transitive) packaging of `langtool/libs`.

## 2026-03-19

### Security
- **jackson-core upgrade:** Updated `jackson-core` to **2.18.6** to address **GHSA-72hv-8253-57qq**.
  - Scope: components depending on `jackson-core` (direct or transitive) packaging of `langtool/libs`.

## 2026-02-13

### Security
- **lucene-core removal:** Removed `org.apache.lucene:lucene-core` to address **PRISMA-2021-0081**.
  - Scope: components depending on `org.apache.lucene:lucene-core` (direct or transitive) packaging of `langtool/libs`.

## 2026-01-23

### Security
- **ch.qos.logback upgrade:** Updated `ch.qos.logback` to **1.5.25** to address **CVE-2026-1225**.
  - Scope: components depending on `ch.qos.logback` (direct or transitive).

## 2025-09-30

### Security
- **Grpc upgrade:** Updated `grpc-netty-shaded` to **1.75.0** to address **CVE-2025-55163**.
  - Scope: components depending on `grpc-netty-shaded` (direct or transitive).

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
