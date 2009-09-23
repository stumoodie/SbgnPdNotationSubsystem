var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":35,"id":2160,"methods":[{"el":24,"sc":5,"sl":22},{"el":29,"sc":5,"sl":26},{"el":34,"sc":5,"sl":31}],"name":"LessThan","sl":18}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_103":{"methods":[{"sl":22},{"sl":26},{"sl":31}],"name":"testLessThan","pass":true,"statements":[{"sl":23},{"sl":28},{"sl":33}]},"test_161":{"methods":[{"sl":22},{"sl":31}],"name":"lessThanOverloaded","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_178":{"methods":[{"sl":22}],"name":"additionalMatchersFailAtReplay","pass":true,"statements":[{"sl":23}]},"test_305":{"methods":[{"sl":22},{"sl":31}],"name":"greaterOrEqual","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_451":{"methods":[{"sl":22},{"sl":31}],"name":"lessThan","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_485":{"methods":[{"sl":22}],"name":"additionalMatchersFailAtReplay","pass":true,"statements":[{"sl":23}]},"test_650":{"methods":[{"sl":22},{"sl":31}],"name":"lessThan","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_654":{"methods":[{"sl":22},{"sl":31}],"name":"greaterOrEqual","pass":true,"statements":[{"sl":23},{"sl":33}]},"test_665":{"methods":[{"sl":22},{"sl":26},{"sl":31}],"name":"testLessThan","pass":true,"statements":[{"sl":23},{"sl":28},{"sl":33}]},"test_756":{"methods":[{"sl":22},{"sl":31}],"name":"lessThanOverloaded","pass":true,"statements":[{"sl":23},{"sl":33}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [650, 665, 756, 485, 161, 178, 654, 451, 305, 103], [650, 665, 756, 485, 161, 178, 654, 451, 305, 103], [], [], [665, 103], [], [665, 103], [], [], [650, 665, 756, 161, 654, 451, 305, 103], [], [650, 665, 756, 161, 654, 451, 305, 103], [], []]
