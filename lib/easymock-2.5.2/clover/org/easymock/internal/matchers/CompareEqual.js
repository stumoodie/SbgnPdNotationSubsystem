var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":35,"id":2073,"methods":[{"el":24,"sc":5,"sl":22},{"el":29,"sc":5,"sl":26},{"el":34,"sc":5,"sl":31}],"name":"CompareEqual","sl":18}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_372":{"methods":[{"sl":22},{"sl":26},{"sl":31}],"name":"testCompareEqual","pass":true,"statements":[{"sl":23},{"sl":28},{"sl":33}]},"test_429":{"methods":[{"sl":22},{"sl":31}],"name":"cmpTo","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_65":{"methods":[{"sl":22},{"sl":26},{"sl":31}],"name":"testCompareEqual","pass":true,"statements":[{"sl":23},{"sl":28},{"sl":33}]},"test_790":{"methods":[{"sl":22},{"sl":31}],"name":"cmpTo","pass":true,"statements":[{"sl":23},{"sl":33}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [65, 372, 429, 790], [65, 372, 429, 790], [], [], [65, 372], [], [65, 372], [], [], [65, 372, 429, 790], [], [65, 372, 429, 790], [], []]
