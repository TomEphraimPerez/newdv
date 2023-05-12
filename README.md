# Group 3 Documentation
Members
* Randy Herrera
* Thomas Perez
* Jessica Kim
* Al Azmine
------------------------------------------------------------------------
## 1. Installation, Building, & Runtime
### INSTALLATION
* Any IDE can be used.
* The command prompt was not tried, but we used (at least) IntelliJ IDEA and NetBeans. 
* **Requirements/prerequisites:**
    *  At least, a JDK, JVM, and JRE are required to run the application. 
* **GOAL:**
    *  The goal is exactly what is explained in the ASMT2 prompt. Ie., at most four servers will be exchanging packet information.
    *  However for demo purposes, we're allowed to simulate this by displaying 4 terminals/consoles on the laptop/desktop display, such that each terminal/console will represent one-of-four servers which will contain its own discrete port number so that they can exchange/update information
    *  In this assignment you will implement a simplified version of the Distance Vector Routing Protocol.
    *  The protocol will be run on top of four servers/laptops (behaving as routers) using TCP or UDP.
    *  Each server runs on a machine at a pre-defined port number.
    *  The servers should be able to output their forwarding tables along with the cost and should be robust to link changes.
    *  A server should send out routing packets only in the following two conditions: 
          *  **a) periodic update**
          *  **b) the user uses command asking for one**.

*  *This is a little different from the original algorithm which immediately sends
out update routing information when routing table changes.*

### BUILD
* The initial, (and only) build of the various components, etc, are done via the IDE's Build tool. 

### RUNTIME EXECUTION 

* One can use `javac dv_routing.java`, however it's easier, faster, and more convenient using the IDE's Run shortcut.  
* Each server will have its own node routing-topology table.  
* Since costs of neighboring nodes will be updatable using the Bellman-Ford algorithm, initial entries will be `"inf"`.  
  
&emsp;A note of importance is in reference to the command string:  
&emsp; &emsp; &emsp; &emsp; &emsp;`server -t <topology-file-name> -i <routing-update-interval>`
      
      
&emsp;which of course contains 5 arguments. The " -t " is not used in our application.  
&emsp;So the initial setup command will be of the form:  
&emsp; &emsp; &emsp; &emsp;&emsp;`server <topology-file-name> -i <routing-update-interval> , ie., 4 args.`  
    
  
* The available user-input commands or queries are exactly the same as what the ASMT2 prompt requires. 
* Another note of importance are the results of the displayed menu of available commands. Here's a representation of this command:
  
 ```Help
Commands:
1. server <topology> -i <time interval> | 4 args.
2. update <server-ID1> <server-ID2> <cost>
3. step
4. display
5. disable <server-ID>
6. crash
Message received from Server 1 (192.168.0.112)
>> display()

Current Routing Table:-
Destination Server ID           Next Hop     Server ID Cost 
             1                     1                   3    
             2                     2                   4    
Message sent to 192.168.0.112

>>step
Step SUCCESS.									//

**Explained: If this table was generated by server-2, the table is read - "the cost traversing from server-2 to server-1 is equal to 3".**

If the table was generated by server-1, the table is of course read - "the cost going from 
server-1 to server-2 is equal to 4",

         1                     1                  3    
        
         2                     2                  4
```

------------------------------------------------------------------------
# Contributions
## **Randy**
* Participated in group meetings to discuss structure and logic of distance vector program
* Consistently check in with group members to identify and discuss checkpoints, progress, and troubleshooting
* Worked with Thomas to build existing `Server.java` and `Client.java` from previous project to fit current requirements of Project 2
* Contributed on `server -t <topology-file-name> -i <routing-update-interval>` command:
   * Set up display of command menu upon runtime execution
   * Developed the file parser function, `readTopology()`,to create file path to specified `topology.txt` files required for each server running the program which would grab the text file values and assign ID and link cost values for each `node --> neighbor node`
   * **getNodeById(int id)**
      * Simply grabs the server ID number of the node being requested
      * Called by many functions, especially needed for `update()` 
* Contributed on Node class
* Contributed on Crash command
* Set up demo environment, which consists of four virtual machines
   * The virtual machines were connected via a virtual subnet address 10.0.2.x.
   * Additionally, they were configured to generate a random MAC address, otherwise it would use the same MAC as the host machine.
   * Individual topology files were created for each virtual machine to assume that each were its own server that had neighbors with specified link costs to each direct neighbor
   * Debugging of code via virtual machines. Several different types of topology setups were considered. The example in the project prompt was used as a base case.
* For every virtual machine a topology file was created, our code was cloned into an Eclipse project, and tested forinterconnectivity. 
* Created a video recording explaining the Server command.
   * Created a video recording to demonstrate various commands of our project using the virtual machine environment. 
* Took responsibility for merging video recordings together for presentation purposes
   * Compiled all group members' video recordings of each explaining the parts of the code they contributed to during this project

##
## **Thomas**
* Participated in group meetings to discuss structure and logic of distance vector program 
* Consistently check in with group members to identify and discuss checkpoints, progress, and troubleshooting
* Worked with Randy to build existing `Server.java` and `Client.jav`a from previous project to fit current requirements of Project 2
* Contributed on `server -t <topology-file-name> -i <routing-update-interval>` command:
   * Responsible for parsing arguments inputted by user in terminal
   * Added throw/catch exceptions in command menu
   * Contributed to logical structure of `readTopology()` and helped troubleshoot errors during program writing of the function
##
## **Jessica**
* Creation and editing of README.md file to comipile group's contribution as well as individual 
* Participated in group meetings to discuss structure and logic of distance vector program
* Consistently check in with group members to identify and discuss checkpoints, progress, and troubleshooting 
* Assigned to focus on developing functions for commands `display()` and `update()` in conjunction with other basic functions that fetched neighbor node IDs needed to create new strings that contains new link costs to neighbors
* **display()**
   * Created initialized topology table with infinity values to indicate no cost to nodes that were not direct neighbors.
   * Developed a getNodeId function to properly identify the current’s server’s node identification and it’s neighbor’s id with its corresponding IP address and port (given by the topology file).
   * This will display a table containing the server id of the current server node the user will be on and the link cost information of its neighbors.
   * This will be rendered differently depending on which server node the user will be on.
   * Considering that the link costs between server and neighbor nodes is bidirectional, the tables will also display these link costs according to this behavior where `link cost of A -> B` is 8 and vice versa, where the link cost of the server node itself is 0.
```                        
                           For example:
                                       A -> A = 0    |   A->B = 8
                                       B->A = 8      |   B->B = 0
```
  
   * To properly display the table, I had to look up how to format the labels and numbers with correct spacing for friendly user-interface on the terminal output.
   * To make updating the table efficient and simple as possible, I made one-liner using ternary operator to check for `“infinity”` as a string and replace with the new link cost and the same applies to nodes that aren’t neighbors that carry the value `“N.A”` (aka INFINITY).
* **update()**
   * To be able to update the routing table with the correct link cost values and display this new information, I made it so that the function first checks if the servers passed as arguments `“update 1 2 3”` are valid.
   * It first checks if the currents server node the user is on matches the variable `myID` which holds the information of itself and if so, then the variable `toNeighbor` is set to the neighbor node’s id.
   * However, in the case that `toNeighbor` is set but it’s not a neighbor or if the neighbor to the current server node is **NOT** a neighbor, an `IllegalArgumentException` is thrown to let the  user know that either the one of the servers passed as argument needs to be the current one they are on **OR** that the given server ID is not a neighbor of ther current server.
   * If all is valid, then the `routingTable` is called to add a new link cost entry for the given nodes and an update message is sent to let the server and the neighbor node that the update is successful.
   * To make sure display and update work properly, these functions also required other functions to retrieve node ID for any given node or to create a string of values containing the nodes and link cost.
* **isNeighbor(Node serverNode)**
   * A static boolean function that simply returns the neighbor node's server ID when called by `update()` to perform a boolean check on the nodes passed in as arguments 
   * A invalid neighbor node or non-existing neighbor node will throw an `IllegalArgumentException` which means that the arguments passed into the function are not valid
* **List<String> makeMessage()**
   * Needed to create a new string that appends new link cost values of `source node -> destination node`
   * This function is called by `update()` whenever uses specifies new link cost between two neighbor nodes (direct neighbors)
   * String should be read in as is when being entered as a new entry for `routingTable`
   
##
## **Al**
*  Participated in group meetings to discuss structure and logic of distance vector program
*  Consistently check in with group members to identify and discuss checkpoints, progress, and troubleshooting
*   
##



## 2. Getting Started
[A distance vector routing protocol](https://en.wikipedia.org/wiki/Distance-vector_routing_protocol) uses the [Bellman-Ford Algorithm](https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm) or [Ford-Fulkerson Algorithm](https://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm) or [Diffusing update Algorithm](https://en.wikipedia.org/wiki/Diffusing_update_algorithm). _We will be using Bellman-Ford Algorithm to calculate the cost of the paths._
A distance vector routing protocol only works on the basis of sending the routing table to its neighbors periodically or if there are any updates in the table. Initially, each server/node is going to have no information about the topology except its neighbors. Each server gets information about its whole topology, when each server starts to send information about its neighbors.
_Examples of distance vector routing protocols are [RIPv1, RIPv2](https://en.wikipedia.org/wiki/Routing_Information_Protocol), [IGRP](https://en.wikipedia.org/wiki/Interior_Gateway_Routing_Protocol) and [EIGRP](https://en.wikipedia.org/wiki/Enhanced_Interior_Gateway_Routing_Protocol)_.

## 3. Protocol Specification
### 3.1 Topology Establishment
I used 4 servers/computers/laptops to implement the simulation. **The four servers are required to form a network topology as shown in Figure 1**. Each server is supplied with a topology file at startup that it uses to build its initial routing table. The topology file is local and contains the link cost to the neighbors. For all other servers in the network, the initial cost would be infinity. Each server can only read the topology file for itself. The entries of a topology file are listed below:
* num-servers
* num-neighbors
* server-ID server-IP server-port
* server-ID1 server-ID2 cost
**num-servers:** total number of servers in the network.
**num-neighbors:** the number of directly linked neighbors of the server.
**server-ID, server-ID1, server-ID2:** a unique identifier for a server, which is assigned by you.
**cost:** cost of a given link between a pair of servers. Assume that cost is an integer value.
Here is an example, consider the topology in Figure 1. We give a topology file for server 1 as shown
in the table below.
![Figure 1. The network topology](/images/network_topology1.png)

Line Number| Line Entry | Comments
---------- | ---------- | --------
1|4|number of servers
2|3|number of edges or neighbors
3|1 192.168.0.112 2000|server-id1 and corresponding IP and port
4|2 192.168.0.100 2001|server-id2 and corresponding IP and port
5|3 192.168.0.118 2002|server-id3 and corresponding IP and port
6|4 192.168.0.117 2003|server-id4 and corresponding IP and port
7|1 2 7| server-id and neighbor-id and cost
8|1 4 2| server-id and neighbor-id and cost

### IMPORTANT: 
In this environment, costs are bi-directional i.e. the cost of a link from A-B is the same for B-A. Whenever a new server is added to the network, it will read its topology file to determine who its neighbors are. Routing updates are exchanged periodically between neighboring servers. When this newly added server sends routing messages to its neighbors, they will add an entry in their routing tables corresponding to it. Servers can also be removed from a network. When a server has been removed from a network, it will no longer send distance vector updates to its neighbors. When a server no longer receives distance vector updates from its neighbor for three consecutive update intervals, it assumes that the neighbor no longer exists in the network and makes the appropriate changes to its routing table (link cost to this neighbor will now be set to infinity but not remove it from the table). This information is propagated to other servers in the network with the exchange of routing updates. Please note that although a server might be specified as a neighbor with a valid link cost in the topology file, the absence of three consecutive routing updates from this server will imply that it is no longer present in the network.

### 3.2 Routing Update
Routing updates are exchanged periodically between neighboring servers based on a time interval specified at the startup. In addition to exchanging distance vector updates, servers must also be able torespond to user-specified events. There are 4 possible events in this system. They can be grouped into three classes: topology changes, queries and exchange commands: (1) Topology changes refer to an updating of link status (update). (2) Queries include the ability to ask a server for its current routing table (display), and to ask a server for the number of distance vectors it has received (packets). In the case of the packets command, the value is reset to zero by a server after it satisfies the query. (3) Exchange commands can cause a server to send distance vectors to its neighbors immediately.

## 4. Server Commands/ Input format
The server supports the following commands:-
* server -t topology-file-name -i time-interval-for-step
* update server-id1 server-id2 updated-cost
* step
* packets
* display
* disable server-id
* crash

Below is the description of each of the above commands-
1. _server -t topology-file-name -i time-interval-for-step_:- 
This command starts the server after reading the topology file and gets the time interval to trigger the step process repeatedly. No other command can be executed unless this command is executed.
**_topology-file-name_** :- topology file name in which the topology is mentioned.
**_time-interval-for-step_** :- time interval to perform the step process in a repetitive manner.

2. _update server-id1 server-id2 updated-cost_:- 
This command updates the routing table of both of the servers i.e., server-id1 and server-id2 with the updated cost. Note
that this command will be issued to both server-ID1 and server-ID2 and involve them to update the cost and no other server.
For example:-
**update 1 2 3** - Assume this update command is sent from server 1 to server 2. The cost of the link is changed 3 in both of the routing tables.
Assume server 1 to have the following intial routing table before execution of any step and update command:- (The Routing table is based on the topology specified in Figure 1)

Destination ID | Next Hop ID | Cost
-------------- | ----------- | ----
1|1|0
2|2|7
3|N. A|inf
4|4|2

After the update command is sent from server 1 to server 2, the routing table of server 1 looks like below

Destination ID | Next Hop ID | Cost
-------------- | ----------- | ----
1|1|0
2|2|3
3|N. A|inf
4|4|2

And routing table of server 2 looks like below

Destination ID | Next Hop ID | Cost
-------------- | ----------- | ----
1|1|3
2|2|0
3|3|8
4|4|3

And now the network topology looks like the following figure 2.

![Figure 2. Updated network topology](/images/updated_network_topology.png)

3. _step_:- 
Send routing update to neighbors right away. Note that except this, routing updates only happen periodically.
Let me explain how the neighbors would update their routing table based on the information sent to it by its neighbors.
Assume server 2's routing table is the above updated one. Say, server 2 performs the step command. So it will send its routing table to server 1, server 3 and server 4. See below figure,

![Figure 3. Server 2 performing step](/images/step_topology_2_to_all.png)

let's assume Server 1 is having the routing table mentioned above after the update command. When server 1 receives the routing table from server 2, it then applies the [Bellman-Ford Algorithm](https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm) to update all its cost if any. It compares all of its cost to all nodes with the routing table it received. For example,
> Cost from 1 to 3 is infinity and cost from 2 to 3 is 8.
> When server 1 receives the routing table from server 2, it updates its cost to server 3 only if the cost from server 1 to server 2 plus cost from server 2 to server 3 is less than the present cost to server 3 in the routing table.
As cost from 1 to 2 is 3 and cost from 2 to 3 is 8 which is equal to (3+8) = 11. 11 is ofcourse less than infinity, thus it updates it routing table to cost to 3 as 11 and updates next hop as 2.

> NOTE : This same thing would happen when server 2 sends its routing table to server 3. Server 3 would also do the same operations.
**Thus, each server when receives routing table from its neighbors performs the Bellman-Ford Algorithm and updates its routing table if required**

4. _packets_:- Display the number of distance vector packets this server has received since the last invocation of this information.

5. _display_:- Display the current routing table. And the table should be displayed in a sorted order from small ID to big. The display should be formatted as a sequence of lines, with each line indicating: destination-server-ID next-hop-server-ID cost-of-path

6. _disable server-id_:- Disable the link to a given server. Doing this “closes” the connection to a given server with server-ID. Here need to check is if the given server is its neighbor.

7. _crash_:- “Close” all connections. This is to simulate server crashes. Close all connections on all links. The neighboring servers must handle this close correctly and set the link cost to infinity.
