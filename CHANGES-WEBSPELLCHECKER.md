# WebSpellChecker changes for LanguageTool

This document records WebSpellChecker-specific changes on top of upstream LanguageTool.

## 2026-08-26

### Security
- **micrometer upgrade:** Updated `io.micrometer` to **1.16.7** to address two issues reported against `micrometer-core` 1.15.12: injection (low) and missing release of memory after effective lifetime (high).
  - Scope: components depending on `micrometer-core` (direct or transitive) packaging of `langtool/libs`.
- **Netty upgrade:** Updated `io.netty` to **4.1.137.Final** to address two issues reported against `netty-handler` 4.1.136.Final: improper validation of certificate with host mismatch (high) and improper check for unusual or exceptional conditions (critical).
  - Scope: components depending on `io.netty` (direct or transitive) packaging of `langtool/libs`.
- **reactor-core pin:** Pinned `io.projectreactor:reactor-core` to **3.8.7** to address allocation of resources without limits or throttling (high, SNYK-JAVA-IOPROJECTREACTOR-19267101) and a race condition (high, SNYK-JAVA-IOPROJECTREACTOR-19267102) in 3.6.6.
  - Scope: components depending on `reactor-core` (transitive through `lettuce-core` in `languagetool-server`) packaging of `langtool/libs`.

## 2026-08-10

### Security
- **micrometer upgrade:** Updated `io.micrometer` to **1.15.12** to address **CVE-2026-40984**. Switched the registry dependency from `micrometer-registry-prometheus` to `micrometer-registry-prometheus-simpleclient`, which keeps the legacy `io.micrometer.prometheus` API (used by `CircuitBreakers`) on the 1.15.x line without migrating to the new Prometheus client.
  - Scope: components depending on `micrometer-core` (direct or transitive) packaging of `langtool/libs`.

## 2026-07-24

### Security
- **Netty upgrade:** Updated `io.netty` to **4.1.136.Final** to address **CVE-2026-59901**.
  - Scope: components depending on `io.netty` (direct or transitive) packaging of `langtool/libs`.

## 2026-07-22

### Security
- **ch.qos.logback upgrade:** Updated `ch.qos.logback` to **1.5.38** to address **CVE-2026-13006**.
  - Scope: components depending on `ch.qos.logback` (direct or transitive) packaging of `langtool/libs`.

## 2026-07-14

### Security
- **jackson-databind upgrade:** Updated `jackson-databind` to **2.18.9** to address **CVE-2026-54515**.
  - Scope: components depending on `jackson-databind` (direct or transitive) packaging of `langtool/libs`.
- **ch.qos.logback upgrade:** Updated `ch.qos.logback` to **1.5.35** to address **CVE-2026-9828** and **CVE-2026-10532**.
  - Scope: components depending on `ch.qos.logback` (direct or transitive) packaging of `langtool/libs`.

## 2026-06-24

### Security
- **jackson-databind upgrade:** Updated `jackson-databind` to **2.18.8** to address **CVE-2026-54513**.
  - Scope: components depending on `jackson-databind` (direct or transitive) packaging of `langtool/libs`.

## 2026-06-12

### Security
- **jackson-core upgrade:** Updated `jackson-core` to **2.18.7** to address **CWE-770**.
  - Scope: components depending on `jackson-core` (direct or transitive) packaging of `langtool/libs`.

## 2026-06-09

### Security
- **Netty upgrade:** Updated `io.netty` to **4.1.135.Final** to address **CVE-2026-44249**, **CVE-2026-45416**, **CVE-2026-45674** and **CVE-2026-47691**.
  - Scope: components depending on `io.netty` (direct or transitive) packaging of `langtool/libs`.

## 2026-05-31

### Security
- **opennlp-tools fork:** Replaced `org.apache.opennlp:opennlp-tools:1.9.4` with the in-house fork `com.webspellchecker:opennlp-tools:1.9.4-webspellchecker-1` to address **CVE-2026-40682** (XXE in DictionaryEntryPersistor), **CVE-2026-42027** (unsafe reflection in ExtensionLoader), and **CVE-2026-42440** (DoS in AbstractModelReader). The fork backports the upstream fixes onto the 1.9.4 baseline. Upgrading to opennlp-tools 2.x is not viable because the chunker feature emission format changed (OPENNLP-1332) and breaks the legacy 1.5-format models used by LanguageTool.
  - Fork repository: https://github.com/WebSpellChecker/opennlp.
  - Scope: components depending on `opennlp-tools` (direct or transitive) packaging of `langtool/libs`.

## 2026-05-19

### Security
- **Netty upgrade:** Updated `opentelemetry` to **1.62.0** to address **CVE-2026-45292**.
  - Scope: components depending on `opentelemetry` (direct or transitive) packaging of `langtool/libs`.

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
