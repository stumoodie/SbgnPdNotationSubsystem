var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":40,"id":2166,"methods":[{"el":30,"sc":5,"sl":28},{"el":34,"sc":5,"sl":32},{"el":39,"sc":5,"sl":36}],"name":"Matches","sl":22}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_377":{"methods":[{"sl":28},{"sl":32}],"name":"testMatches","pass":true,"statements":[{"sl":29},{"sl":33}]},"test_684":{"methods":[{"sl":28},{"sl":36}],"name":"matchesToString","pass":true,"statements":[{"sl":29},{"sl":37}]},"test_851":{"methods":[{"sl":28},{"sl":32}],"name":"testMatches","pass":true,"statements":[{"sl":29},{"sl":33}]},"test_856":{"methods":[{"sl":28},{"sl":36}],"name":"matchesToString","pass":true,"statements":[{"sl":29},{"sl":37}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [377, 856, 684, 851], [377, 856, 684, 851], [], [], [377, 851], [377, 851], [], [], [856, 684], [856, 684], [], [], []]
