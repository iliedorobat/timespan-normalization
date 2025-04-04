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
JDK 11+ or OpenJDK 11+<br/>
Maven 3.x

## Setup
1. Download and install [JDK 11](https://www.oracle.com/nl/java/technologies/javase/jdk11-archive-downloads.html) or [OpenJDK 11](https://openjdk.org/install/) (or newer versions)
2. Download and install [Maven 3.x](https://maven.apache.org/install.html)
3. Clone the repository:
```bash
git clone https://github.com/iliedorobat/timespan-normalization.git
```
4. Generate the library:
```bash 
./gradlew shadowJar
```

## Test the library:
```bash
java -jar build/libs/timespan-normalization-1.4.jar --expression="1/2 sec. 3 a. chr - sec. 2 p. chr."
```

## Normalize multiple temporal expressions:
```bash
java -jar build/libs/timespan-normalization-1.4.jar
```

## Example
### Usecase
```bash
TimeExpression expression = new TimeExpression("1/2 sec. iii - sec. i a. chr.", null);
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
