var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":40,"id":2218,"methods":[{"el":30,"sc":5,"sl":28},{"el":35,"sc":5,"sl":32},{"el":39,"sc":5,"sl":37}],"name":"StartsWith","sl":22}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_266":{"methods":[{"sl":28},{"sl":32}],"name":"testStartsWith","pass":true,"statements":[{"sl":29},{"sl":33}]},"test_396":{"methods":[{"sl":28},{"sl":37}],"name":"startsWithToString","pass":true,"statements":[{"sl":29},{"sl":38}]},"test_558":{"methods":[{"sl":28},{"sl":37}],"name":"startsWithToString","pass":true,"statements":[{"sl":29},{"sl":38}]},"test_657":{"methods":[{"sl":28},{"sl":32}],"name":"testStartsWith","pass":true,"statements":[{"sl":29},{"sl":33}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [558, 266, 396, 657], [558, 266, 396, 657], [], [], [266, 657], [266, 657], [], [], [], [558, 396], [558, 396], [], []]
