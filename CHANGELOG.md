# Release notes
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).
---

### 2.1.0
#### Changed
- Bump commons-echo to 1.6.1
- Pre-sanitize datasets only if the user explicitly requests it via the `--sanitize` flag

### 2.0.0
#### Added
- `--py4j` parameter which allows users to specify whether they need to start GatewayServer
- Support for a greater range of intervals, such as but not limited to the following:
  - "Între x și y"
    - "Între 2004 și 2008"
    - "Între sec. al 2-lea a. chr. și al 21-lea"
    - "Între mijl. sec. al xix-lea și xxi"
  - "Între x - y"
    - "Între 2004 - 2008"
    - "Între sec. al 2-lea a. chr. - al 21-lea"
  - "În intervalul x și y"
    - "În intervalul 2004 și 2008"
    - "În intervalul mijl. mil. i a. chr. și al ii-lea"
  - Sfârșitul/Începutul/Mijlocul...:
    - "Sf. anului 1990"
    - "Înc. anului 1990"
    - "Sfârșit de an 1990"
    - "Început de an 1990"
    - "Sfârșitul anului 1990"
    - "Începutul anului 1990"
    - "Înc de an 1990 - sfârșitul anului 1995"
    - "Mijlocul anilor 1960-1970"
  - DMY-like intervals:
    - "19 -26 noiembrie 2010"
    - "19 03 -26 noiembrie 2010"
    - "19 martie -26 noiembrie 2010"
    - "19.02  -26 11 2010"
    - "19.02-  26.11.2010"
  - MY-like intervals:
    - "noiembrie 1784 - aprilie 1785"
    - "noiembrie 1784 - 1785"
    - "1784 - aprilie 1785"
- Support to detect century/millennium intervals containing both Roman and Arabic numerals, such as but not limited to the following:
  - "sec. 4 - 1/2 sec. 2 p. chr"
  - "sec. iv - 1/2 sec. ii p. chr"
  - "între mil. iii şi 1/2 mil. ii p. chr"
- Safety guard to the TimeExpression constructor

#### Changed
- Prevented matching the following patterns:
  - day-month expressions like "23 decembrie", "30 august", etc.
  - hour-minute expressions like "18.05"
  - term-value like "luna 10", "minutul 30", etc.

#### Breaking
- ⚠️ API change: TimeExpression

### 1.7.0
#### Added
- Support for preserving diacritics of transformed values

### 1.6.0
#### Changed
- Bump commons-echo to 1.6

#### Added
- py4j gateway server
- `historicalOnly` flag to allow users to standardize both historical and future temporal expressions
- Serialization mechanism for TimeExpression instances

#### Removed
- The normalization of temporal expressions from the sanitization method

### 1.5.0
#### Changed
- Bump commons-echo to 1.5
- Bump LIDO-Parser to 1.2
- Bump Dublin-Core-Parser to 1.2
- Updated edgesValues field of TimeExpression to directly retrieve DBpedia URIs from timespanModel

### 1.4
#### Added
- Gradle support

### 1.3
#### Added
- Maven support
- Comprehensive sample

### 1.2
#### Changed
- Several updates and fixes

### 1.1
#### Changed
- Several updates and fixes

### 1.0
#### Added
- Initial implementation