给一个字典（substring和相应分数）一个输入字符和指定切分刀数。求该
input能返回的最大分数。切割刀数不满足或者无法全部分割返回-1
eg:
[{"abc",1}, {"bcd",2}, {"f",7}]，“abcbcdf”，3
返回 10
[{"abc",1}, {"bcd",2}, {"f",7}]，“abcbcd”，3
返回-1
[{"abc",1}, {"bcd",2}, {"f",7}, {"abcb",14}, {"cd", 11}], "abcbcdf", 3
返回32



附上DFS solution