# organisationChartTraversal
This project is to find a way between two employees in an organisation. 

For example- Below is the input

  | Employee ID | Name            | Manager ID |
  | 1           | Dangermouse     |            |
  | 2           | Gonzo the Great | 1          |
  | 3           | Invisible Woman | 1          |
  | 6           | Black Widow     | 2          |
  | 12          | Hit Girl        | 3          |
  | 15          | Super Ted       | 3          |
  | 16          | Batman          | 6          |
  | 17          | Catwoman        | 6          |
  
Find the path between - "Batman" and "Super Ted"

It should give below output:

Batman (16) -> Black Widow (6) -> Gonzo the Great (2) -> Dangermouse (1) <- Invisible Woman (3) <- Super Ted (15)

Note: There is no guarantee that names are unique – your program should print at least one path between each pair of
 people with the same name, but you don't have to show, for example, the path from “Minion (1)” to “Minion (2)” 
 and the path from “Minion (2)” to “Minion (1)”. Where your program prints more than one path, 
 each path should be printed on a new line.
 
 When comparing names, the case of letters is not significant, and neither are leading or trailing spaces or runs of multiple spaces. 
 So: "Gonzo the Great"
    "gonzo the GREAT" 
	" Gonzo the Great "  
are all considered to be equivalent, but 
  "Gon Zot Heg Reat" is NOT.