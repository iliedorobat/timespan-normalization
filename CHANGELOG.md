# Release notes
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).
---

## 1.8
- Added support for a greater range of intervals, such as but not limited to the following:
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

## 1.7
- Added support for preserving diacritics of transformed values

## 1.6
- Bump commons-echo to 1.6
- Added py4j gateway server
- Added historicalOnly flag to allow users to standardize both historical and future temporal expressions
- Added serialization mechanism for TimeExpression instances
- Removed the normalization of temporal expressions from the sanitization method

## 1.5
- Bump commons-echo to 1.5
- Bump LIDO-Parser to 1.2
- Bump Dublin-Core-Parser to 1.2
- Updated edgesValues field of TimeExpression to directly retrieve DBpedia URIs from timespanModel

## 1.4
- Added gradle support

## 1.3
- Added maven support
- Added a comprehensive sample

## 1.2
- Several updates and fixes

## 1.1
- Several updates and fixes

## 1.0
- Initial implementation