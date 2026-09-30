# GitLab CI/CD Portability

**Status:** REFERENCE_MAPPING

GitLab is cited by the public Estreem job posting. The executable repository CI remains GitHub Actions because the source repository is hosted on GitHub.

Pipeline equivalence:

| Capability | GitHub Actions | GitLab CI equivalent |
|---|---|---|
| build/test | Java Domain CI | test stage |
| container build | Platform CI | build image stage |
| manifest validation | Platform CI | validate/deploy stage |
| multi-cluster test | Kind workflow | integration/resilience stage |
| evidence | Actions logs | job artifacts/logs |

Installing a local GitLab instance would add operational weight without proving an additional payment capability.
