var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":38,"id":2185,"methods":[{"el":29,"sc":5,"sl":28},{"el":33,"sc":5,"sl":31},{"el":37,"sc":5,"sl":35}],"name":"Null","sl":22}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_233":{"methods":[{"sl":28},{"sl":31}],"name":"allKinds","pass":true,"statements":[{"sl":32}]},"test_263":{"methods":[{"sl":31}],"name":"testNull","pass":true,"statements":[{"sl":32}]},"test_352":{"methods":[{"sl":31}],"name":"testNull","pass":true,"statements":[{"sl":32}]},"test_365":{"methods":[{"sl":35}],"name":"nullToString","pass":true,"statements":[{"sl":36}]},"test_383":{"methods":[{"sl":28},{"sl":31}],"name":"allKinds","pass":true,"statements":[{"sl":32}]},"test_930":{"methods":[{"sl":35}],"name":"nullToString","pass":true,"statements":[{"sl":36}]},"test_94":{"methods":[{"sl":31}],"name":"differentConstraintsOnSameCall","pass":true,"statements":[{"sl":32}]},"test_985":{"methods":[{"sl":31}],"name":"differentConstraintsOnSameCall","pass":true,"statements":[{"sl":32}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [383, 233], [], [], [383, 233, 94, 263, 985, 352], [383, 233, 94, 263, 985, 352], [], [], [930, 365], [930, 365], [], []]
