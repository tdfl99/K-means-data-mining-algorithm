The code was developed and compiled via Java

To execute, extract the folder k_means into the directory (NOT the one in source code)
and set the following command:

java k_means.clustering <k> <text file>

It should output a text file fittingly called 'output.txt' into the directory

Some notes:

1. The program is, by design, formatted to overrite the output.txt file if it
already exists. If you require multiple individual files, then move the file
out of the directory so that a new one is generated.

2. I was encountering some strange errors while testing later on that was somehow preventing
multiple iterations of centroid determination, causing the initial k-groups to
remain and causing the groups to display incorrectly. I was unable to figure out
why this happened, as it seemed to occur randomly and code changes didn't seem to
do anything. As far as I can tell, I fixed it, but I do apologize if it occurs again.

3. I was having trouble with zeus while testing if it worked
on that server, so I am only able to say I have majority confidence in this program
working properly on that server and not full confidence.
