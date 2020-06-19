[maven]: http://maven.apache.org/
[git]: http://git-scm.com/
[grid]: https://www.selenium.dev/documentation/en/grid/setting_up_your_own_grid/
[eyes]: https://eyes.applitools.com/app/test-results/00000251810436322399?accountId=8BdDBLn-ekCxz39XpWszDw~~&display=details&top=00000251810436322399%282%29
## Overview
Traditional approach is implemented using Java, Maven, JUnit5 and Selenium Grid. 
I also used Data Driven approach to keep my tests short and reusable.
As I mostly needed to check elements visibility, I created a wrapper method that would do that check and report the result in specified format.
Not something I would usually do, but hey, we applied to up-skill and have fun.

Please note: my traditional solution fits both V1 and V2, so TraditionalTestsV1 and TraditionalTestsV2 are identical.  
I like my projects backward-compatible :)

## Preconditions
- [Maven][maven] 
- [Git][git]
- [Selenium Grid][grid]

1. Make sure Chrome, Firefox and Edge Chromium browsers and drivers are installed. Edit nodeconfig.json and hubconfig.json if needed.
2. Make sure your Selenium Grid is up and running.
3. Update testdata.properties > grid.url value with your Selenium hub URL.

## Structure overview
- filters (src/filters) - test data that is different between V1 and V2
- testdata.properties (resources/resources-filtered) - the rest of test data for Data Driven approach
- configurations.csv (resources/testdata) - contains configurations we want to test against; easily expandable. 
- drivers dir (resources/drivers) - for Selenium Grid stuff; I intentionally not included most of that, as the exact setup would heavily depends on OS.
- model/tests/pages/utils - pretty much self-explaining

## How to run
To run tests against V1, please execute:

```bash
$ mvn clean test -Pv1
```
To run tests against V2, please execute:
```bash
$ mvn clean test -Pv2
```

## Modern approach
[Eyes batch result][eyes]

## Final word
Hat tip to all the people who worked to make this fantastic hackaton happen. Guys, you rock! 
It was a great fun to participate!
