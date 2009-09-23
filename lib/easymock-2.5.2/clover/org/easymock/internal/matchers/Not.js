var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":41,"id":2172,"methods":[{"el":30,"sc":5,"sl":28},{"el":34,"sc":5,"sl":32},{"el":40,"sc":5,"sl":36}],"name":"Not","sl":22}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_18":{"methods":[{"sl":28}],"name":"equalsMissing","pass":true,"statements":[{"sl":29}]},"test_224":{"methods":[{"sl":28},{"sl":32}],"name":"notOverloaded","pass":true,"statements":[{"sl":29},{"sl":33}]},"test_263":{"methods":[{"sl":28},{"sl":32}],"name":"testNull","pass":true,"statements":[{"sl":29},{"sl":33}]},"test_285":{"methods":[{"sl":28},{"sl":32}],"name":"notOverloaded","pass":true,"statements":[{"sl":29},{"sl":33}]},"test_30":{"methods":[{"sl":28},{"sl":32}],"name":"testNotNull","pass":true,"statements":[{"sl":29},{"sl":33}]},"test_326":{"methods":[{"sl":28}],"name":"equalsMissing","pass":true,"statements":[{"sl":29}]},"test_352":{"methods":[{"sl":28},{"sl":32}],"name":"testNull","pass":true,"statements":[{"sl":29},{"sl":33}]},"test_468":{"methods":[{"sl":28},{"sl":36}],"name":"notToString","pass":true,"statements":[{"sl":29},{"sl":37},{"sl":38},{"sl":39}]},"test_522":{"methods":[{"sl":28},{"sl":32}],"name":"testNotNull","pass":true,"statements":[{"sl":29},{"sl":33}]},"test_593":{"methods":[{"sl":28},{"sl":36}],"name":"notToString","pass":true,"statements":[{"sl":29},{"sl":37},{"sl":38},{"sl":39}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [285, 468, 522, 30, 224, 263, 352, 593, 326, 18], [285, 468, 522, 30, 224, 263, 352, 593, 326, 18], [], [], [285, 522, 30, 224, 263, 352], [285, 522, 30, 224, 263, 352], [], [], [468, 593], [468, 593], [468, 593], [468, 593], [], []]
