var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":53,"id":2190,"methods":[{"el":32,"sc":5,"sl":30},{"el":41,"sc":5,"sl":34},{"el":52,"sc":5,"sl":43}],"name":"Or","sl":24}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1022":{"methods":[{"sl":30},{"sl":34}],"name":"testOr","pass":true,"statements":[{"sl":31},{"sl":35},{"sl":36},{"sl":37},{"sl":40}]},"test_398":{"methods":[{"sl":30},{"sl":43}],"name":"orToString","pass":true,"statements":[{"sl":31},{"sl":44},{"sl":45},{"sl":46},{"sl":47},{"sl":48},{"sl":51}]},"test_448":{"methods":[{"sl":30},{"sl":34}],"name":"orOverloaded","pass":true,"statements":[{"sl":31},{"sl":35},{"sl":36},{"sl":37}]},"test_621":{"methods":[{"sl":30},{"sl":34}],"name":"orOverloaded","pass":true,"statements":[{"sl":31},{"sl":35},{"sl":36},{"sl":37}]},"test_869":{"methods":[{"sl":30},{"sl":43}],"name":"orToString","pass":true,"statements":[{"sl":31},{"sl":44},{"sl":45},{"sl":46},{"sl":47},{"sl":48},{"sl":51}]},"test_934":{"methods":[{"sl":30},{"sl":34}],"name":"testOr","pass":true,"statements":[{"sl":31},{"sl":35},{"sl":36},{"sl":37},{"sl":40}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [1022, 621, 448, 869, 934, 398], [1022, 621, 448, 869, 934, 398], [], [], [1022, 621, 448, 934], [1022, 621, 448, 934], [1022, 621, 448, 934], [1022, 621, 448, 934], [], [], [1022, 934], [], [], [869, 398], [869, 398], [869, 398], [869, 398], [869, 398], [869, 398], [], [], [869, 398], [], []]
