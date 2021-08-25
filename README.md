To-Do REST Application

This is a To-Do REST application, which specializes in helping user to organize his daily chores by importance and deadlines. This api is meant to be called by front-end framework such as angular.

Application consists of following controllers and endpoints:

Admin Panel Controller


/admin/panel/view - GET all registered users and allows to reach to Edit / Delete endpoints

/admin/panel/edit/{id} - allows admin user to GET/ PUT basic properties of a user, such as first name, last name, age etc. {id} = user id

/admin/panel/view/{id} - allows admin to DELETE user. {id} = user id

User Controller


/login - POST method that accepts users username and password for authentication and authorization

/register - POST method that allows new user to register an account

To Do Controller


/workspace/{id}/todo - GET main user workspace, where he can access all his To-Do notes and perform CRUD operations with them. {id} = logged in users id

/workspace/{id}/today - GET main user workspace, where he can see all the To-Do notes settled for this day (by LocalDate). {id} = logged in users id

/workspace/{id}/{significance} - GET main user workspace, where user can see all his To-Do by one of the three importance levels : Ordinary, Important, Urgent. {id} = logged in users id. {significance} - selected significance from interface

/workspace/{id}/todo/{toDoId} - Perform CRUD (GET/PUT/DELETE) on selected To-Do. {id} = logged in users id, {toDoId} = selected note
