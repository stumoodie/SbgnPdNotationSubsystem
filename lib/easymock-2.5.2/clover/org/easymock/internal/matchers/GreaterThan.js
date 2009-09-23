var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":35,"id":2142,"methods":[{"el":24,"sc":5,"sl":22},{"el":29,"sc":5,"sl":26},{"el":34,"sc":5,"sl":31}],"name":"GreaterThan","sl":18}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_105":{"methods":[{"sl":22},{"sl":31}],"name":"lessOrEqual","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_13":{"methods":[{"sl":22},{"sl":31}],"name":"greaterThanOverloaded","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_240":{"methods":[{"sl":22},{"sl":31}],"name":"greaterThanOverloaded","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_345":{"methods":[{"sl":22},{"sl":31}],"name":"greaterThan","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_614":{"methods":[{"sl":22},{"sl":26},{"sl":31}],"name":"testGreateThan","pass":true,"statements":[{"sl":23},{"sl":28},{"sl":33}]},"test_774":{"methods":[{"sl":22},{"sl":31}],"name":"lessOrEqual","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_950":{"methods":[{"sl":22},{"sl":26},{"sl":31}],"name":"testGreateThan","pass":true,"statements":[{"sl":23},{"sl":28},{"sl":33}]},"test_952":{"methods":[{"sl":22},{"sl":31}],"name":"greaterThan","pass":true,"statements":[{"sl":23},{"sl":33}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [952, 13, 240, 614, 345, 950, 774, 105], [952, 13, 240, 614, 345, 950, 774, 105], [], [], [614, 950], [], [614, 950], [], [], [952, 13, 240, 614, 345, 950, 774, 105], [], [952, 13, 240, 614, 345, 950, 774, 105], [], []]
