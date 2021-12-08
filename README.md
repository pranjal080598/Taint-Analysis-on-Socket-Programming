# Taint-Analysis-on-Socket-Programming

## Student Details
|Student name                |    CCID      |
|----------------------------|--------------|
| Pranjal Dilip Naringrekar  |  naringre    |

## Structure of the code for this project
- `basic socket program demo step 1`: Used for Client Server Demo purpose 
- `socket programming taint analysis step 2`: Used to identify the taints in the Client and Server code
- `callgraphs`: Used RTA and VTA to create call graph using Soot
- `report`: Report for the final Project

## Execution

### Step 1: Client Server Demo

a. Open two command prompt terminals and navigate to the directory 'Taint-Analysis-on-Socket-Programming/basic socket program demo step 1/src'

b. In the first Command prompt, execute the command:
- javac Server.java
- java Server

c. In the second command prompt, execute the command:
- javac Client.java
- java Client

d. When prompted for port number enter '5000'

e. Send any message from Client command prompt and it can be seen on Server command prompt.

f. Send 'Over' from client command prompt to terminate the connection.

### Step 2: Taint Analysis in Client Server Code

a. Open the command prompt and navigate to the directory 'Taint-Analysis-on-Socket-Programming/socket programming taint analysis step 2'

b. Sanitization results:
- Add the pattern.matches regex to check if the port number contains only integer value in Client.java code.
- On the command prompt, execute the command: 'javac IdentifyTaint.java' followed by 'java IdentifyTaint'
- The result should depcit that no taint exists in the code as the input is sanitized.

![image](https://user-images.githubusercontent.com/46446655/145279953-152e8706-d0d6-4bfe-a5f4-eb066e449d5d.png)



c. Non-Sanitization results:
- Remove the pattern.matches regex to check if the port number contains only integer value in Client.java code.
- On the command prompt, execute the command: 'javac IdentifyTaint.java' followed by 'java IdentifyTaint'
- The result should depict that taint exists in the code and is propagated to Server code as the input it is not sanitized.

### Step 3: Call Graph Creation for Analyzing Affected Libraries

a. Import the folder callgraphs present at 'Taint-Analysis-on-Socket-Programming/callgraphs/' to a suitable IDE.

b. Download soot.jar package.

c. Import the package to the project structure.

d. Add all other libraries present in the repository to the libraries section of the IDE.

e. Specify appropriate Java Version in the project structure.

f. Comment either useRTA(); or useVTA(); in 'Main.java'to see results using RTA or VTA respectively.

g. RTA takes more time than VTA to execute.

h. The output will be the number of Edges, Affected Libraries and Solutions.

## Resources

https://users.ece.cmu.edu/~aavgerin/papers/Oakland10.pdf

http://www.cs.columbia.edu/~azavou/taint_exchange_iwsec11.pdf

https://dl.acm.org/doi/10.1145/3341105.3373924

http://www.airccse.org/journal/nsa/0113nsa01.pdf

https://www.sciencedirect.com/science/article/pii/S089812211100664X?via%3Dihub

https://www.usenix.org/legacy/event/webapps10/tech/full_papers/Davis.pdf
