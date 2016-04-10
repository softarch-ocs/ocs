import sys
import os
import re

steps = [1, 2, 3]
hosts= '192.168.0.9'
test = sys.argv[1]

for step in steps:
    a = "jmeter -n -R"+hosts+" -Gusers="+str(step)+" -t "+ test + \
		" -l res"+ str(step)+test+".csv > res"+ str(step) +test+".txt"
    os.system(a)
    print a

table = 'users average error\n'

for step in steps:
	content = open("res"+ str(step) +test+".txt").readlines()
	tokens = [y for y in content[-3].split(' ') if y != ''] 
	avg = tokens[tokens.index('Avg:')+1]
	error = re.findall("[-+]?\d+[\,]?\d*", tokens[tokens.index('Err:')+2])
	table += (str(step) +' '+ str(avg) +' '+ error[0] + '\n')
	print content[-3]

with open('table_'+test+'.csv', 'w+') as o:
	o.write(table)