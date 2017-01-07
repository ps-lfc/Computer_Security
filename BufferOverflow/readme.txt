CS558 Introduction to Computer Security
Readme File
Project 3
Name:Plash Sachdeva
Email:psachde3@binghamton.edu
Files included
1)attack-string.c
2)vuln_program.c
3)Readme

Steps to execute:
1) gcc vuln_program.c -fno-stack-protector -z execstack -static -o vuln_program
2) sudo sysctl -w kernel.randomize_va_space=0
3) gcc attack-string.c -o attack-string
4) ./attack-string $'/* address of target function in little endian */' > attack.input
5) ./vuln_program <./attack.input
