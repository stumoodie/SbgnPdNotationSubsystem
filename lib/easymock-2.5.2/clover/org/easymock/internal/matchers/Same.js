var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":42,"id":2210,"methods":[{"el":31,"sc":5,"sl":29},{"el":35,"sc":5,"sl":33},{"el":41,"sc":5,"sl":37}],"name":"Same","sl":23}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_204":{"methods":[{"sl":29},{"sl":37}],"name":"sameToStringWithObject","pass":true,"statements":[{"sl":30},{"sl":38},{"sl":39},{"sl":40}]},"test_296":{"methods":[{"sl":29},{"sl":33}],"name":"testSame","pass":true,"statements":[{"sl":30},{"sl":34}]},"test_369":{"methods":[{"sl":29},{"sl":33}],"name":"testSame","pass":true,"statements":[{"sl":30},{"sl":34}]},"test_481":{"methods":[{"sl":29},{"sl":37}],"name":"sameToStringWithObject","pass":true,"statements":[{"sl":30},{"sl":38},{"sl":39},{"sl":40}]},"test_497":{"methods":[{"sl":29},{"sl":37}],"name":"sameToStringWithString","pass":true,"statements":[{"sl":30},{"sl":38},{"sl":39},{"sl":40}]},"test_498":{"methods":[{"sl":29},{"sl":37}],"name":"sameToStringWithChar","pass":true,"statements":[{"sl":30},{"sl":38},{"sl":39},{"sl":40}]},"test_661":{"methods":[{"sl":29},{"sl":37}],"name":"sameToStringWithString","pass":true,"statements":[{"sl":30},{"sl":38},{"sl":39},{"sl":40}]},"test_940":{"methods":[{"sl":29},{"sl":37}],"name":"sameToStringWithChar","pass":true,"statements":[{"sl":30},{"sl":38},{"sl":39},{"sl":40}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [369, 940, 296, 497, 481, 661, 498, 204], [369, 940, 296, 497, 481, 661, 498, 204], [], [], [369, 296], [369, 296], [], [], [940, 497, 481, 661, 498, 204], [940, 497, 481, 661, 498, 204], [940, 497, 481, 661, 498, 204], [940, 497, 481, 661, 498, 204], [], []]
