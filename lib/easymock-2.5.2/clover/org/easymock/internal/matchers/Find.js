var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":41,"id":2130,"methods":[{"el":31,"sc":5,"sl":29},{"el":36,"sc":5,"sl":33},{"el":40,"sc":5,"sl":38}],"name":"Find","sl":23}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_303":{"methods":[{"sl":29},{"sl":33}],"name":"testFind","pass":true,"statements":[{"sl":30},{"sl":34}]},"test_54":{"methods":[{"sl":29},{"sl":38}],"name":"findToString","pass":true,"statements":[{"sl":30},{"sl":39}]},"test_75":{"methods":[{"sl":29},{"sl":33}],"name":"testFind","pass":true,"statements":[{"sl":30},{"sl":34}]},"test_861":{"methods":[{"sl":29},{"sl":38}],"name":"findToString","pass":true,"statements":[{"sl":30},{"sl":39}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [861, 303, 54, 75], [861, 303, 54, 75], [], [], [303, 75], [303, 75], [], [], [], [861, 54], [861, 54], [], []]
