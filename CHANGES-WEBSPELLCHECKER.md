# WebSpellChecker changes for LanguageTool

This document records WebSpellChecker-specific changes on top of upstream LanguageTool.

## 2025-09-09

### Security
- **Netty upgrade:** Updated Netty to **4.1.126.Final** to address **CVE-2025-58057**.
  - Scope: components depending on `io.netty` (direct or transitive).