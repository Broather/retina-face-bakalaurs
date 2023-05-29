## TODO:
spring
    mainController with GetMapping("/")
    run a python file and put it's return in a module 
    database, but a persistent one
    image upload

python
    reference image or list<faces>
    image -> faces

## initializing the system:
* upload one or a buch of faces, system tries to group them and prompts the user to name each group (with option to reorganise the groups manually) 

* initially upload one face (ether by ) for each person you'd like to organise by (great for pictures with random people alongside) and uses those vectors to Euclid distance all the other faces the system recieves to grow the groups.