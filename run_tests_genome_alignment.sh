#!/bin/bash
cd inputs_outputs/
allInputAs=(`ls input*a.txt`)
allInputIDs=()
for input in ${allInputAs[*]}
do 
    len=${#input}
    inputID=${input:5:$((len-10))}
    allInputIDs=("${allInputIDs[@]}" ${inputID})
done
cd ..
for repo in submissions/*/
do
    dir=${repo%*/} # remove trailing "/"
    echo $dir
    cd $dir
    # pwd
    javac GA.java
    for i in ${allInputIDs[@]}
    do
        ../../../timeout -t 25 -s 5000000 taskset -c 0 java GA ../../inputs_outputs/input${i}a.txt ../../inputs_outputs/input${i}b.txt > stdout_${i}.out 2> stderr_${i}.out
    done
    cd ../..
done

python compute_grades.py
