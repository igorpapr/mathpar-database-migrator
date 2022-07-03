This project is meant to help migrate the existing data from the original H2 mathpar database into a new MySQL version of
this database.

Its main purpose is to operate with tables "tasks" and newly created table "sections".
The original tasks table contained a field called "task" with some JSON data which consisted 
of the list of sections for each "TASK" 
(which is actually either a book with lectures or a book with problems for students to solve).

Each section represents a "page" from that book. The goal is to parse the json data from tasks.task 
and to spread it over the sections records in the corresponding table.