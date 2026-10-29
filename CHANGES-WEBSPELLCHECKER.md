# WebSpellChecker changes for LanguageTool

This document records WebSpellChecker-specific changes on top of upstream LanguageTool.

## 2026-10-29

### Security
- **jackson-databind upgrade:** Updated `jackson-databind` to **2.18.12** to address **CVE-2026-110879** (SNYK-JAVA-COMFASTERXMLJACKSONCORE-21761837).
  - Scope: components depending on `jackson-databind` (direct or transitive) packaging of `langtool/libs`.

## 2026-10-23

### Security
- **SBOM matches the package:** Moved the removal of `io.lettuce:lettuce-core` (with `reactor-core`, `redis-authx-core` and `io.netty`), `at.favre.lib:bcrypt`, `org.mybatis:mybatis`, `io.grpc:grpc-netty-shaded` and `org.apache.lucene:lucene-core` from `zip.xml` to Maven exclusions in `languagetool-standalone/pom.xml`. The SBOM (`cyclonedx-maven-plugin`) reads the Maven dependency graph, so it listed these artifacts although they were not in `libs`. `lucene-core` is also declared `provided` in `languagetool-standalone`, because the `HomophoneOccurrenceDumper` dev tool compiles against it.
  - The exclusions also drop the dependencies that came only with these artifacts and were still packaged: `io.grpc:grpc-core`, `grpc-context`, `grpc-util`, `com.google.code.gson:gson` and `io.perfmark:perfmark-api` (through `grpc-netty-shaded`), `org.reactivestreams:reactive-streams` (through `reactor-core`). The checks AppServer runs load none of their classes; `io.grpc:grpc-api`, which `languagetool-core` loads during every check, stays.
  - Scope: `languagetool_sbom.json` and packaging of `langtool/libs`.
- **JAXB 4 runtime removal:** Removed `org.glassfish.jaxb:jaxb-runtime` 4.0.5 and its dependencies (`jaxb-core` 4.0.5, `txw2`, `istack-commons-runtime` 4.1.2, `angus-activation`, `jakarta.activation-api` 2.1.3). Nothing in the package uses `jakarta.xml.bind`; the `javax.xml.bind` implementation (`com.sun.xml.bind:jaxb-impl` 2.3.3) stays. Its `jaxb-core.jar` was overwritten in `libs` by `com.sun.xml.bind:jaxb-core` 2.3.0.1, so the SBOM listed a jar that was not shipped and Snyk reported it (XXE, high, CVE-2026-92877, SNYK-JAVA-ORGGLASSFISHJAXB-20883142).
  - Scope: components depending on `org.glassfish.jaxb:jaxb-runtime` (direct or transitive) packaging of `langtool/libs`.
- **Android annotations removal:** Removed `com.google.android:annotations`, which comes with `grpc-core`. Its `annotations.jar` was overwritten in `libs` by `com.intellij:annotations`, so the SBOM listed a jar that was not shipped.
  - Scope: `languagetool_sbom.json`.

## 2026-10-19

### Security
- **Netty and Redis client removal:** Removed `io.lettuce:lettuce-core`, `io.projectreactor:reactor-core`, `redis.clients.authentication:redis-authx-core` and all `io.netty` artifacts to address the hardcoded private keys (SQ34109, Spectra Assure SAFE level L3) found in Netty's `SslUtils` and `JdkSslServerContext`. Updating Netty does not help: the keys are still in the 4.2 line. These artifacts come only from `lettuce-core`, which `languagetool-server` declares but no shipped code uses.
  - Scope: components depending on `io.lettuce:lettuce-core` and `io.netty` (direct or transitive) packaging of `langtool/libs`.
- **grpc-netty-shaded removal:** Removed `io.grpc:grpc-netty-shaded` to address the same hardcoded private key (SQ34109) in its shaded copy of Netty's `OpenSsl`. It is only the gRPC transport for remote rules (`GRPCRule`, `BERTSuggestionRanking`), which are enabled only through `remoteRulesFile` or `activateRemoteRules`, and WebSpellChecker uses neither. The other `io.grpc` artifacts stay, because `languagetool-core` loads classes from `grpc-api` during every check.
  - Scope: components depending on `io.grpc:grpc-netty-shaded` (direct or transitive) packaging of `langtool/libs`.
- **bcrypt removal:** Removed `at.favre.lib:bcrypt` and its dependency `at.favre.lib:bytes` to address a code signing certificate without the code signing extended key usage (SQ20122) on `bytes.jar`. `languagetool-server` declares bcrypt but no shipped code uses it.
  - Scope: components depending on `at.favre.lib:bcrypt` (direct or transitive) packaging of `langtool/libs`.
- **ch.qos.logback upgrade:** Updated `ch.qos.logback` to **1.6.5** to address directory traversal (medium, SNYK-JAVA-CHQOSLOGBACK-20824362) in 1.6.3. logback 1.6.5 is built against slf4j 2.0.19, the version we already pin.
  - Scope: components depending on `ch.qos.logback` (direct or transitive) packaging of `langtool/libs`.
- **guava upgrade:** Updated `com.google.guava:guava` to **33.7.2-jre** to address allocation of resources without limits or throttling (high, SNYK-JAVA-COMGOOGLEGUAVA-20825054) in 33.5.0-jre.
  - Scope: components depending on `com.google.guava:guava` (direct or transitive) packaging of `langtool/libs`.

## 2026-09-21

### Security
- **ch.qos.logback upgrade:** Updated `ch.qos.logback` to **1.6.3** to address **CVE-2026-19880** (directory traversal, medium, SNYK-JAVA-CHQOSLOGBACK-19882330) in 1.5.38.
  - Scope: components depending on `ch.qos.logback` (direct or transitive) packaging of `langtool/libs`.
- **slf4j upgrade:** Updated `org.slf4j` to **2.0.19** alongside logback. logback 1.6 is built against slf4j-api 2.0.18, and our `dependencyManagement` excludes `slf4j-api` from `logback-classic`, so the project pin (previously 2.0.16) wins at runtime and would have risked `NoSuchMethodError`.
  - Scope: components depending on `org.slf4j` (direct or transitive) packaging of `langtool/libs`.
- **jackson-databind upgrade:** Updated `jackson-databind` to **2.18.11** to address three issues reported against 2.18.9: unsafe reflection (medium, SNYK-JAVA-COMFASTERXMLJACKSONCORE-19477042), deserialization of untrusted data (medium, SNYK-JAVA-COMFASTERXMLJACKSONCORE-19496766) and allocation of resources without limits or throttling (high, SNYK-JAVA-COMFASTERXMLJACKSONCORE-19778370).
  - Scope: components depending on `jackson-databind` (direct or transitive) packaging of `langtool/libs`.

## 2026-09-11

### Security
- **netty pin moved to the 4.2 line:** Replaced the `io.netty:netty-bom` 4.1.137.Final import with **4.2.17.Final**, the lowest version on the line `lettuce-core` requires that clears all nine issues reported against the 4.2.13.Final it brings in, including **CVE-2026-75595** (critical), **CVE-2026-75596**, **CVE-2026-73508**, **CVE-2026-44249**, **CVE-2026-45416**, **CVE-2026-50010**, **CVE-2026-45674**, **CVE-2026-47691** and **CVE-2026-45673**.
  - Scope: components depending on `io.netty` (direct or transitive) packaging of `langtool/libs`.
- **mariadb-java-client upgrade:** Updated `org.mariadb.jdbc:mariadb-java-client` to **3.4.3** (upstream is still on 3.4.1) to address **CVE-2026-55856**, **CVE-2026-55857** and **CVE-2026-55858**.
  - Scope: components depending on `mariadb-java-client` (direct or transitive, through `languagetool-server`) packaging of `langtool/libs`.
- **micrometer registry back on the upstream artifact:** Reverted `micrometer-registry-prometheus-simpleclient` to `micrometer-registry-prometheus` (1.17.1, the version upstream now uses).
  - Scope: components depending on `micrometer-core` (direct or transitive) packaging of `langtool/libs`.

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
- **opentelemetry upgrade:** Updated `opentelemetry` to **1.62.0** to address **CVE-2026-45292**.
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
