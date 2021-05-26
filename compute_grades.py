import os
import re

# find all outputs:
d='inputs_outputs/'
correct_outputs=[]
pattern = re.compile('output([0-9]+).txt')
for f in os.listdir(d):
    m=pattern.match(f)
    if m is not None:
        with open(os.path.join(d,f),'r') as f:
            content=f.read()
            correct_outputs.append((int(m.group(1)),int(content.strip())))

# Go through submissions:
d='submissions/'
submissions=[o for o in os.listdir(d) if os.path.isdir(os.path.join(d,o))]

column_names=[x[0] for x in correct_outputs]

submission2test_resutls=dict()

for s in submissions:
    submission2test_resutls[s]=dict()
    for o_id,content in correct_outputs:
        with open(os.path.join(os.path.join(d, s),'stdout_'+str(o_id)+'.out'),'r') as f:
            try:
                submission_stdout=int(f.read().strip())
                # print('submission_stdout==content',submission_stdout,content,submission_stdout==content)
                if submission_stdout==content:
                    submission2test_resutls[s][o_id]=True
                else:
                    submission2test_resutls[s][o_id]=False
            except:
                submission2test_resutls[s][o_id]=False

with open('final_scores.csv','w') as f:
    f.write('Submission Dir,')
    for i,c in enumerate(column_names):
        f.write(str(c))
        if i!=len(column_names)-1:
            f.write(',')
    
    f.write('\n')

    for s,test_results in submission2test_resutls.items():
        f.write('%s,'%(s))
        for i,c in enumerate(column_names):
            f.write(str(test_results[c]))
            if i!=len(column_names)-1:
                f.write(',')

        f.write('\n')

