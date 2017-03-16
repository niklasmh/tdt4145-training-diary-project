# TDT4145 - Training Diary

This is a CLI based application for keeping track of your workouts.

The main objects in the applications are ExerciseGroup, Exercise, Workout and Session. Let's explain how it works.

The core of the application is a workout. A workout represents a collection of exercises. It has a name to make it easy to recognize and a description. 

The exercises in the workout is, you guessed it, a single exercise performed as part of a workout. All exercises have to be part of an exercise group. 

##### Let's use an example:

Lets say you go to the gym and do bench press and push-ups. To register this in the diary you might  create the ExerciseGroup _"Chest exercises"_. Then you would add the two exercises _"Bench press"_ and _"Push ups"_ and make them part of the group _"Chest exercises"_.

Now you are ready to create a workout. Maybe it is your regular chest routine, it witch case we would make a workout with the name _"Chest routine"_. You would add the _"Push ups"_ and _"Bench press"_ exercises to this workout. 

All we've done up until this point is creating the workout, but we haven't saved the fact that we've actually done it. Let's do that by creating a Session. We give the session a start time and an end time. We'll also let you save a note about how it went, and let you git it a _performance_ grade on a scale from 0 to 10. We'll use the performance grade to decide what was your best session. 

##### Other features:

We also support fetching statistics about your best session of a workout, and about the aggregated data from your sessions in the last 30 days. 


## Usage

### Scenarios

Follow the instructions in the terminal.

Currently we support the following scenarios:
1. Add an ExerciseGroup -> Exercies -> Workout -> Session
2. Update workouts and exercises
3. Statistics for the last 30 days

## Development

Would recommend using the IntelliJ import functionality to get the database up and working when running the program. We cannot ensure that there exists another way to run the program.

### Structure:

The project consists of two packages:

#### View-package

The UI is command-line based and uses classes called Handlers. Each handler represent a menu. For instance there is one ExerciseHandler, and one ExerciseGroupHandler, as they represent different menus. You can "drill-down" into a menu, and the return back to the main menu. 

#### Database-package
The _handler_-classes connect to _services_. It is the services that talk to the database. The ExerciseService has methods for fetching a list of all Exercises, saving a new to the database, etc. There is one Service per Entity. In the code the Entities are represented by simple classes i n the database.models package. 


