SkipListC = require("SkipListC");

let sl:SkipListC = new SkipListC();

sl.insert(3,40);
sl.insert(4,30);
sl.insert(1,20);
sl.insert(10,20);
sl.insert(90,20);

sl.insert(-1,20);


console.log(sl.toString());
