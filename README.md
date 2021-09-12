# Taskmaster

## Documentation

## Lab-26

- Homepage should have a heading at the top of the page, an image to mock the “my tasks” view, and buttons at the bottom of the page to allow going to the “add tasks” and “all tasks” page.
  - <img src="/screenshots/home.jpg" alt="Homepage" width="150"/>

- Add a Task allow users to type in details about a new task, specifically a title and a body. When users click the “submit” button, show a “submitted!” label on the page.
  - <img src="/screenshots/addTask.jpg" alt="addTaskFeature" width="150"/>
- All Tasks
  - <img src="/screenshots/allTask.jpg" alt="allTasksFeature" width="150"/>


## Lab-27

- Task Detail Page -> Create a Task Detail page. It should have a title at the top of the page, and a Lorem Ipsum description.
  - <img src="/screenshots/taskdetails.jpg" alt="Task Detail Page" width="150"/>
- Settings Page -> Create a Settings page. It should allow users to enter their username and hit save.
  - <img src="/screenshots/settings.jpg" alt="Settings Page" width="150"/>
- Homepage
  - The main page should be modified to contain three different buttons with hardcoded task titles. When a user taps one of the titles, it should go to the Task Detail page, and the title at the top of the page should match the task title that was tapped on the previous page.

  - The homepage should also contain a button to visit the Settings page, and once the user has entered their username, it should display “{username}’s tasks” above the three task buttons.
  - <img src="/screenshots/home2.jpg" alt="Home Page" width="150"/>

## Lab-28

- Task Model -> Create a Task class. A Task should have a title, a body, and a state. The state should be one of “new”, “assigned”, “in progress”, or “complete”.

- Refactor your homepage to use a RecyclerView for displaying Task data. This should have hardcoded Task data for now.
  - <img src="/screenshots/lab-28.jpg" alt="drawing" width="150"/>
  
  
## Lab-29

**Task Model and Room -> Following the directions provided in the Android documentation, set up Room in your application, and modify your Task class to be an Entity.**

- Add Task Form -> Modify your Add Task form to save the data entered in as a Task in your local database.
  - <img src="/screenshots/addNewTask.png" alt="drawing" width="150"/>
- Homepage -> Refactor your homepage’s RecyclerView to display all Task and add task button.
  - <img src="/screenshots/home.png" alt="drawing" width="150"/>
- Detail Page -> Ensure that the description and status of a tapped task are also displayed on the detail page, in addition to the title. (Note that you can accomplish this by passing along the entire Task entity, or by passing along only its ID in the intent.)
  - <img src="/screenshots/oneTask.png" alt="drawing" width="150"/>
- All Tasks
  - <img src="/screenshots/alltasks.png" alt="drawing" width="150"/>

## Lab-31

- Add Espresso to your application, and use it to test basic functionality of the main components of your application. For example:

  - assert that important UI elements are displayed on the page
  - tap on a task, and assert that the resulting activity displays the name of that task
  - edit the user’s username, and assert that it says the correct thing on the homepage
  
- [Tests](app/src/androidTest/java/com/example/taskmaster)

## Lab-32

- Tasks Are Cloudy
- Add Task Form -> Modify your Add Task form to save the data entered in as a Task to DynamoDB.
- All Tasks Page -> Refactor RecyclerView to display all Task entities in DynamoDB.

## Lab-33

- Tasks Are Owned By Teams -> Created a second entity for a team, which has a name and a list of tasks. Task updated to be owned by a team.
  ```graphql
        type Team @model {
        id:ID!
        name:String!
        teamTasks: [Todo] @connection(keyName: "byTeam" , fields:["id"])
        }
        
        type Todo @model @key(name: "byTeam", fields: ["teamID"]){
        id: ID!
        title: String!
        bode: String
        state: String
        teamID: ID!
        team: Team @connection(fields: ["teamID"])
        }
  ```
  - <img src="/screenshots/tasks.png" alt="task" width="250"/>
  - <img src="/screenshots/teams.png" alt="team" width="250"/>

