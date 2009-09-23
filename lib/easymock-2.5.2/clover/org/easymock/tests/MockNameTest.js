var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":35,"id":4166,"methods":[{"el":34,"sc":5,"sl":28}],"name":"MockNameTest","sl":24}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_321":{"methods":[{"sl":28}],"name":"defaultName","pass":true,"statements":[{"sl":30},{"sl":31},{"sl":32},{"sl":33}]},"test_492":{"methods":[{"sl":28}],"name":"defaultName","pass":true,"statements":[{"sl":30},{"sl":31},{"sl":32},{"sl":33}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [321, 492], [], [321, 492], [321, 492], [321, 492], [321, 492], [], []]
