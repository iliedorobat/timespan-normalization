# Temporal Expression Normalization Framework
Normalize calendar data expressed in different shapes, years, and phrases by which the centuries and millennia can be identified

Supported Language: Romanian

<b>Table I.</b> Types of temporal expressions which can be handled
<table>
    <tr>
        <th>Type of Time Period</th>
        <th>Examples of Time Periods*</th>
    </tr>
    <tr>
        <td>date</td>
        <td>
            YMD: 1881-08-31; 1857 mai 10; etc.<br/>
            DMY: 09.11.1518; 1 noiembrie 1624; etc.<br/>
            MY: ianuarie 632; etc.
        </td>
    </tr>
    <tr>
        <td>timespans</td>
        <td>
            centuries: s:; sc; se.; sec; sec.; secol; secolele; secolul; sex.<br/>
            millenniums: mil; mil.; mileniul; mileniului; mileniile
        </td>
    </tr>
    <tr>
        <td>years</td>
        <td>77; 78; 1652; [1873]; aproximativ 1834; cca. 1420; etc.</td>
    </tr>
</table>
* The values are mentioned in the reference language – Romanian language

## Requirements
JDK 11+ or OpenJDK 11+

## Setup
1. Download and install [JDK 11](https://www.oracle.com/nl/java/technologies/javase/jdk11-archive-downloads.html) or [OpenJDK 11](https://openjdk.org/install/) (or newer versions)
2. Clone the repository:
```bash
  git clone https://github.com/iliedorobat/temporal-normalization.git
```
3. Generate the library:
```bash
  ./gradlew clean shadowJar
```

## Usage
### Input Parameters
- <b>expression="SOME_TEXT"</b> takes the text to be parsed.
- <b>historicalOnly=true</b> specifies whether the Framework will only handle historical dates
(future dates will be ignored).
- <b>sanitize=true</b> specifies if the custom method TimeSanitizeUtils.sanitizeValue will
be used to sanitize values. Use "true" only if you  use this framework on LIDO datasets.

### Test the library:
```bash
  # Parse only historical dates
  java -jar build/libs/temporal-normalization-1.7.jar --historicalOnly=true --expression="1/2 sec. 3 a. chr - sec. 2 p. chr."
  # Include future dates
  java -jar build/libs/temporal-normalization-1.7.jar --expression="1/2 sec. 3 a. chr - sec. 2 p. chr."
```

### Normalize multiple temporal expressions:
```bash
  # Parse only historical dates
  java -jar build/libs/temporal-normalization-1.7.jar --historicalOnly=true --analysis
  # Include future dates
  java -jar build/libs/temporal-normalization-1.7.jar --analysis
```

## Example
### Use case
```bash
    TimeExpression expression = new TimeExpression("1/2 sec. iii - sec. i a. chr.");
    System.out.print(expression);

    TimeExpression timeExpression = new TimeExpression("1/2 mil. 5 - sec. i al mil. 4 a.chr.");
    System.out.print(expression);

    TimeExpression timeExpression = new TimeExpression("4/4 sec.xix. sfârșitul sec.al xix-lea și începutul sec.al xx-lea.");
    System.out.print(expression);

    TimeExpression timeExpression = new TimeExpression("402-403, 405-406 a. chr.");
    System.out.print(expression);

    TimeExpression timeExpression = new TimeExpression("1/2 sec. 3 - sec. 1 a. chr.");
    System.out.print(expression);

    TimeExpression timeExpression = new TimeExpression("epoca modernă");
    System.out.print(expression);
```

### Result
```bash
    input value = 1/2 sec. iii - sec. ii a. chr.
    sanitized value = 1/2 sec. iii - sec. i __BC__
    normalized values = [
            http://dbpedia.org/page/1st_century_BC,
            http://dbpedia.org/page/2nd_century_BC,
            http://dbpedia.org/page/3rd_century_BC
    ]
```

## Publications
ECAI 2021: [The Power of Regular Expressions in Recognizing Dates and Epochs (2021)](https://ieeexplore.ieee.org/document/9515139)
```
@inproceedings{9515139,
  author={Dorobăț, Ilie Cristian and Posea, Vlad},
  booktitle={2021 13th International Conference on Electronics, Computers and Artificial Intelligence (ECAI)}, 
  title={The Power of Regular Expressions in Recognizing Dates and Epochs}, 
  year={2021},
  pages={1-3},
  doi={10.1109/ECAI52376.2021.9515139}}
```
