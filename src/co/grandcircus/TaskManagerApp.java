package co.grandcircus;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TaskManagerApp {

	// Create scanner to use throughout the program
	public static Scanner scnr = new Scanner(System.in);

	public static void main(String[] args) {

		// Declare variables
		String cont = "yes";
		List<Task> taskList = new LinkedList<>();

		// Greet user
		System.out.println("Welcome to the Task Manager!");

		// Loop while user says they want to continue
		do {
			printMenu(); // Method prints menu options
			// Prompt user to pick a menu choice and save choice to an int
			int menuChoice = Validator.getInt(scnr, "Please select an option (enter the number):\n", 1, 8);
			if (menuChoice == 8) { // Option 8 is quit
				// Verify user wants to quit and restart loop with new input if they do not
				System.out.println("Are you sure you want to quit?");
				String sure = Validator.getCont(scnr);
				if (sure.equalsIgnoreCase("yes")) {
					cont = "no";
				} else {
					System.out.println("OK, let's try again.\n");
				}
			} else { // If user does not select quit option
				// Call method to perform the task they selected
				performMenuChoice(menuChoice, taskList);
				// Once action is complete, ask if user wants to take another action
				System.out.println("\nDo you want to take another action? (yes/no)");
				String contMaybe = Validator.getCont(scnr);
				if (contMaybe.equalsIgnoreCase("no")) { // If user states they want to quit, verify they do
					System.out.println("Are you sure you want to quit? (yes/no)");
					String sure = Validator.getCont(scnr);
					if (sure.equalsIgnoreCase("yes")) {
						cont = "no";
					} else {
						System.out.println("OK, let's try again.\n");
					}
				}
				System.out.println();
			}
		} while (cont.equalsIgnoreCase("yes"));

		// Say bye and closer scanner
		System.out.println("\nBye!");
		scnr.close();
	}

	// Method prints a formatted menu of tasks the program can perform
	public static void printMenu() {
		System.out.printf("  %s\n  %s\n  %s\n  %s\n  %s\n  %s\n  %s\n  %s\n", "1. List Tasks",
				"2. List Tasks by Team Member", "3. List Tasks By Due Date", "4. Add Task", "5. Delete Task",
				"6. Mark Task Complete", "7. Edit Task", "8. Quit");
	}

	// Method performs the task the user selected from the menu
	public static void performMenuChoice(int menuChoice, List<Task> taskList) {
		switch (menuChoice) {
		case 1: // List tasks
			if (taskList.size() == 0) { // If there are no tasks on the list, advise user there are no tasks
				System.out.println("There are no tasks to list.");
			} else { // Else call the listTasks method to add tasks to the list
				System.out.println();
				System.out.println(listTasks(taskList));
			}
			break;
		case 2: // List tasks by team member
			if (taskList.size() == 0) { // If there are no tasks on the list, advise user there are no tasks ...
				System.out.println("There are no tasks to list.");
			} else {
				System.out.println();
				String seeAnother = "yes";
				do { // Otherwise, call the method below to list tasks by team member and ask if user
						// wants to continue after task is complete.
					listTasksByTeamMember(scnr, taskList);
					System.out.println("Would you like to see the tasks for another team member?");
					seeAnother = Validator.getCont(scnr);
				} while (seeAnother.equalsIgnoreCase("yes"));
			}
			break;
		case 3: // List tasks by due date
			if (taskList.size() == 0) { // If there are no tasks on the list, advise user there are no tasks
				System.out.println("There are no tasks to list.");
			} else {
				System.out.println();
				String seeAnother = "yes";
				do {// Otherwise, call method to list tasks by due date and continue until user
					// states to stop
					listTasksByDate(scnr, taskList);
					System.out.println("Would you like to see the tasks due before a different date? (yes/no)");
					seeAnother = Validator.getCont(scnr);
				} while (seeAnother.equalsIgnoreCase("yes"));
			}
			break;
		case 4: // Creat task
			String addAnother = "yes";
			do {
				taskList.add(createTask(scnr)); // Call method to create task
				System.out.println("Would you like to add another task? (yes/no)"); // Continue until user stops
				addAnother = Validator.getCont(scnr);
			} while (addAnother.equalsIgnoreCase("yes"));
			break;
		case 5: // Delete task
			if (taskList.size() == 0) { // Advise user if there is no task to delete
				System.out.println("Sorry, there are no tasks to delete.");
			} else {
				String deleteAnother = "yes";
				do { // Other call deleteTask method and continue calling until user states to stop
					deleteTask(scnr, taskList);
					System.out.println("Would you like to delete another task? (yes/no)");
					deleteAnother = Validator.getCont(scnr);
				} while (deleteAnother.equalsIgnoreCase("yes"));
			}
			break;
		case 6: // Mark task complete
			if (taskList.size() == 0) { // Advise user if there is no task to mark complete
				System.out.println("\nSorry, there are no tasks to mark complete.\n");
			} else {
				String completeAnother = "yes";
				do { // Otherwise call markTaskComplete method to mark tasks complete until user
						// states they don't want to
					markTaskComplete(scnr, taskList);
					System.out.println("\nWould you like to mark another task complete? (yes/no)");
					completeAnother = Validator.getCont(scnr);
					if (completeAnother.equalsIgnoreCase("yes")) { // If user wants to mark another task complete ...
						for (Task t : taskList) { // ...verify whether any tasks haven't been marked complete
							if (t.getWhetherComplete() == "x") {
								continue; // If not, start the loop over
							}
						} // Otherwise, tell the user all tasks are complete and return them to main menu
						System.out.println("\nAll tasks are complete.");
						completeAnother = "no";

					}
				} while (completeAnother.equalsIgnoreCase("yes"));
			}
			break;
		case 7: // Edit a task
			String editAnother = "yes";
			if (taskList.size() == 0) { // If there are no tasks to edit, let user know
				System.out.println("\nSorry, there are no tasks to edit.\n");
			} else { // Otherwise, call editTask method to edit task until user is done editing
				do {
					editTask(scnr, taskList);
					System.out.println("Would you like to edit another task?");
					editAnother = Validator.getCont(scnr);
				} while (editAnother.equalsIgnoreCase("yes"));
			}
			break;
		}

		// return taskList;
	}

	// This method creates a task using user input
	public static Task createTask(Scanner scnr) {
		System.out.println();
		System.out.println("You selected Create Task.\n");
		System.out.println("Which team member will be assigned to this task?");
		String teamMemberName = scnr.nextLine();
		System.out.println("\nCan you provide a brief description of the task?");
		String taskDescription = scnr.nextLine();
		String dueDate = Validator.getDate(scnr, "\nWhat date is this task due? (mm/dd/yyyy)\n");
		System.out.println("\nHere is your new task:\n");
		Task task = new Task(teamMemberName, taskDescription, dueDate); // Construct new Task using the Task class and
																		// the Strings gathered from the user
		System.out.println(task); // Print the new task created
		return task;
	}

	// This method lists all of the tasks the user has added
	public static String listTasks(List<Task> taskList) {
		// Declare variables
		int i = 1;
		int descriptionLength = 0;
		int nameLength = 0;
		String taskListString = "Task List\n";

		// This for loop gathers the largest length of descriptions and team members
		// names from the task list to user in formatting.
		for (Task t : taskList) {
			if (t.getTaskDescription().length() > descriptionLength) {
				descriptionLength = t.getTaskDescription().length();
			}
			if (t.getTeamMemberName().length() > nameLength) {
				nameLength = t.getTeamMemberName().length();
			}
		}

		// For loop prints a line of * characters at the total length of the longest
		// task string. 57 is added to account for additional characters including
		// dividers, labels, due date, and whether the task is complete.
		for (int j = 0; j < (nameLength + descriptionLength + 57); j++) {
			taskListString += "*";
		}
		taskListString += "\n";

		// For loop adds each task to the string and formats using the highest name and
		// task descriptions lengths gathered earlier in the method.
		for (Task t : taskList) {
			taskListString += String.format(
					"%d. %-" + descriptionLength + "s | Assigned To: %-" + nameLength + "s | %s | %s", i,
					t.getTaskDescription(), t.getTeamMemberName(), "Due Date: " + t.getDueDate(),
					"Complete: " + t.getWhetherComplete() + "\n");
			i++;
		}
		return taskListString;
	}

	// This method list tasks assigned to an individual team member chosen by the
	// user
	public static void listTasksByTeamMember(Scanner scnr, List<Task> taskList) {
		// Create variables
		String teamMembersAssigned = "";
		String nameSelection;
		int h = 1;
		int trueCount = 0;
		int i = 1;
		int descriptionLength = 0;
		int nameLength = 0;

		System.out.println("Here are the team members who currently have tasks assigned to them:");

		// For loop iterates through each task in the list ...
		for (Task t : taskList) {
			// ... if the team member name for that a task is not already included in the
			// teamMembersAssigned string ...
			if (!teamMembersAssigned.contains(t.getTeamMemberName())) {
				// ... the name is added along with a rising counter.
				teamMembersAssigned += h + ". " + t.getTeamMemberName() + "\n";
				h++;
			}
		}
		String recheck = "";
		do {
			System.out.println(teamMembersAssigned); // Print the list of all team members

			// Get user input for which team member to list
			System.out.println("Whose tasks would you like to see? (enter their name)\n");
			nameSelection = scnr.nextLine();

			//
			do {
				for (Task t : taskList) { // For loop iterates through each task in the list ...
					// ...Finds if the team member name the user entered is equal to the team member
					// name saved for the task ...
					if (t.getTeamMemberName().equals(nameSelection)) {
						trueCount++; // ...and increments the trueCount counter if so.
					}
				}
				if (trueCount == 0) { // If the name was not on the list, trueCount will be 0. Advise user to try a
										// new name.
					System.out.println(
							"Whoops, that name wasn't on the list. Make sure you enter it with exactly the same spelling.");
					nameSelection = scnr.nextLine();
				}
			} while (trueCount == 0);

			// If name was found on the list, have user confirm selection.
			System.out.println("\nYou've selected " + nameSelection + ". Is that right? (yes/no)");
			recheck = Validator.getCont(scnr);
		} while (!recheck.equalsIgnoreCase("yes"));

		String taskListString = "\nOK, here's " + nameSelection + "'s task list: \n";

		// For loop finds the length of the longest task description among tasks
		// assigned to the user. This number will be used for formatting.
		for (Task t : taskList) {
			if (t.getTaskDescription().length() > descriptionLength) {
				descriptionLength = t.getTaskDescription().length();
			}
		}
		nameLength = nameSelection.length(); // Save length of team member name for formatting

		// For loop creates a line of * characters as long as the length of the team
		// member name, their longest task description, and an additional 57 characters
		// to account for other data listed with the task.
		for (int j = 0; j < (nameLength + descriptionLength + 57); j++) {
			taskListString += "*";
		}
		taskListString += "\n";

		// For loop iterates through each task on the task list ...
		for (Task t : taskList) {
			if (t.getTeamMemberName().equals(nameSelection)) { // If the task is assigned to the team member the user
																// selected ...
				// ...the task will be added to the print output along with an incrementing
				// number counter
				taskListString += String.format("%d. %-" + descriptionLength + "s | %s | %s", i, t.getTaskDescription(),
						"Due Date: " + t.getDueDate(), "Complete: " + t.getWhetherComplete() + "\n");
				i++;
			}
		}
		System.out.println(taskListString);
	}

	// This method deletes tasks from the task list
	public static void deleteTask(Scanner scnr, List<Task> taskList) {
		// Get user input for a task to delete
		System.out.println("Which task would you like to delete?\n");
		System.out.println(listTasks(taskList)); // List all of the tasks on the taskList
		int deleteChoice = Validator.getInt(scnr, "", 1, taskList.size());

		// Ask user to confirm selection and print their chosen task
		System.out.println("This is the task you've chosen to delete. Are you sure you want to delete it? (yes/no)\n");
		System.out.println(String.format("Task Description: %s | Assigned To: %s | %s | %s",
				taskList.get(deleteChoice - 1).getTaskDescription(), taskList.get(deleteChoice - 1).getTeamMemberName(),
				"Due Date: " + taskList.get(deleteChoice - 1).getDueDate(),
				"Complete: " + taskList.get(deleteChoice - 1).getWhetherComplete() + "\n"));
		String yesDelete = Validator.getCont(scnr);

		if (yesDelete.equalsIgnoreCase("yes")) { // Once user confirms, delete the task from the list
			taskList.remove(deleteChoice - 1);
			System.out.println("Task deleted.\n");
		} else { // Otherwise, user recursion to repeate process with a new input
			System.out.println("Alright, let's try again.\n");
			deleteTask(scnr, taskList);
		}
	}

	// This method marks tasks complete on the task list
	public static void markTaskComplete(Scanner scnr, List<Task> taskList) {
		// Get user input for task to mark complete
		System.out.println("Which task have you completed?");
		System.out.println();
		System.out.println(listTasks(taskList)); // Print lists of tasks
		int completeChoice = Validator.getInt(scnr, "", 1, taskList.size());

		// Print user's choice and ask them to confirm selection
		System.out.println(
				"This is the task you've chosen to mark complete. Are you sure you want to mark it complete? (yes/no)\n");
		System.out.println(String.format("Task Description: %s | Assigned To: %s | %s | %s",
				taskList.get(completeChoice - 1).getTaskDescription(),
				taskList.get(completeChoice - 1).getTeamMemberName(),
				"Due Date: " + taskList.get(completeChoice - 1).getDueDate(),
				"Complete: " + taskList.get(completeChoice - 1).getWhetherComplete() + "\n"));
		String yesDelete = Validator.getCont(scnr);

		if (yesDelete.equalsIgnoreCase("yes")) { // Once user confirms, change task to complete (i.e., check mark)
			taskList.get(completeChoice - 1).setWhetherComplete("✓");
			System.out.println("Task completed.\n");
		} else {// Otherwise use recursion to perform the method again using new input
			System.out.println("Alright, let's try again.\n");
			markTaskComplete(scnr, taskList);
		}

	}

	// This method edits tasks on the task list
	public static void editTask(Scanner scnr, List<Task> taskList) {
		// Declare variables
		String yesEdit = "no";
		String cont = "yes";
		do { // Get user input for task to edit
			System.out.println("Which task would you like to edit?\n");
			System.out.println(listTasks(taskList)); // List all tasks available
			int editChoice = Validator.getInt(scnr, "", 1, taskList.size());
			System.out.println();

			// Print out the user's selection and ask them to confirm
			System.out.println("This is the task you've chosen to edit. Are you sure you want to edit it? (yes/no)\n");
			System.out.println(String.format("Task Description: %s | Assigned To: %s | %s | %s",
					taskList.get(editChoice - 1).getTaskDescription(), taskList.get(editChoice - 1).getTeamMemberName(),
					"Due Date: " + taskList.get(editChoice - 1).getDueDate(),
					"Complete: " + taskList.get(editChoice - 1).getWhetherComplete() + "\n"));
			yesEdit = Validator.getCont(scnr);

			if (yesEdit.equalsIgnoreCase("no")) { // If user does not confirm, start loop over with new input
				System.out.println("OK, let's try again.\n");
			} else {
				// Otherwise, ask user to select the information they want to edit
				System.out.println(
						"What information would you like to edit?\n  1. Task Description\n  2. Team Member Assigned\n  3. Due Date\n  4. Completeness");
				int infoToEdit = Validator.getInt(scnr, "\n", 1, 4);
				// Switch statement based on user's input choice
				switch (infoToEdit) { // Get user input for edit and call appropriate method from Task class
				case 1:
					System.out.println("What would you like to change the description to?");
					taskList.get(editChoice - 1).setTaskDescription(scnr.nextLine());
					System.out.println("The task description has been changed to: "
							+ taskList.get(editChoice - 1).getTaskDescription() + ".\n");
					break;
				case 2:
					System.out.println("\nWhich team member do you want to reassign this task to?");
					taskList.get(editChoice - 1).setTeamMemberName(scnr.nextLine());
					System.out.println("\nThis task is now assigned to "
							+ taskList.get(editChoice - 1).getTeamMemberName() + ".\n");
					break;
				case 3:
					taskList.get(editChoice - 1).setDueDate(
							Validator.getDate(scnr, "\nWhat would you like to change the date to? (mm/dd/yyyy)\n"));
					System.out.println("\nThis task is now due " + taskList.get(editChoice - 1).getDueDate() + ".\n");
					break;
				case 4:
					System.out.println("Is this task complete? (yes/no)");
					String complete = Validator.getCont(scnr);
					if (complete.equalsIgnoreCase("yes")) {
						taskList.get(editChoice - 1).setWhetherComplete("✓");
						System.out.println("This task is now marked complete.");
					} else if (complete.equalsIgnoreCase("no")) {
						taskList.get(editChoice - 1).setWhetherComplete("x");
						System.out.println("This task is now marked incomplete.");
					}
					break;
				}
				// Ask user if they want to make additional changes to the same task and
				// continue if so
				System.out.println("Do you want to edit anything else about this task?");
				cont = Validator.getCont(scnr);
			}
		} while (cont.equalsIgnoreCase("yes"));
	}

	// This method lists tasks before a due date entered by the user
	public static void listTasksByDate(Scanner scnr, List<Task> taskList) {
		// Declare variables
		int i = 1;
		int descriptionLength = 0;
		int nameLength = 0;

		// Get user input for date to use for sorting
		String seeByDate = Validator.getDate(scnr,
				"Enter a date to see all tasks due before that date (mm/dd/yyyy):\n");
		String taskListString = "\nOK, here are the tasks due before " + seeByDate + ":\n";

		// For loop finds the longest task description and team member names in the task
		// list to use in formatting
		for (Task t : taskList) {
			if (t.getTaskDescription().length() > descriptionLength) {
				descriptionLength = t.getTaskDescription().length();
			}
			if (t.getTeamMemberName().length() > nameLength) {
				nameLength = t.getTeamMemberName().length();
			}
		}

		// For loop creates a line of * chars equal to the length of the longest task
		// string to be ouput
		for (int j = 0; j < (nameLength + descriptionLength + 57); j++) {
			taskListString += "*";
		}
		taskListString += "\n";

		// Split the date entered by the user into a String[] in order to compare with
		// the due dates saved in the taskList
		String[] seeByDateArr = seeByDate.split("/");
		for (Task t : taskList) {// For each task in the list ...
			String[] compareArr = t.getDueDate().split("/"); // Split the due date into a String[]
			// First compare years between the user entered date and the task date by
			// converting the date strings to ints. Increment i each team a task is added to
			// the printout to use as a counter of tasks due before the user's entered date.

			// If the task due date is in the same year as the user's entered date ...
			if (Integer.parseInt(compareArr[2]) == Integer.parseInt(seeByDateArr[2])) {
				// ... then compare months. If the task date is in the same month ...
				if (Integer.parseInt(compareArr[0]) == Integer.parseInt(seeByDateArr[0])) {
					// ... then compare days. If the task is due on an earlier day ...
					if (Integer.parseInt(compareArr[1]) < Integer.parseInt(seeByDateArr[1])) {
						// ... then add it to the string to be printed out.
						taskListString += String.format("%d. %-" + descriptionLength + "s | %s | %s", i,
								t.getTaskDescription(), "Due Date: " + t.getDueDate(),
								"Complete: " + t.getWhetherComplete() + "\n");
						i++;
					}
				}
				// If the task due date is in the same year as the entered due date, but in an
				// earlier month, then add it to the string to be output.
				else if (Integer.parseInt(compareArr[0]) < Integer.parseInt(seeByDateArr[0])) {
					taskListString += String.format("%d. %-" + descriptionLength + "s | %s | %s", i,
							t.getTaskDescription(), "Due Date: " + t.getDueDate(),
							"Complete: " + t.getWhetherComplete() + "\n");
					i++;
				}
			}
			// If the task due date is an earlier year than the entered date, then add iit
			// to the string to be output.
			else if (Integer.parseInt(compareArr[2]) < Integer.parseInt(seeByDateArr[2])) {
				taskListString += String.format("%d. %-" + descriptionLength + "s | %s | %s", i, t.getTaskDescription(),
						"Due Date: " + t.getDueDate(), "Complete: " + t.getWhetherComplete() + "\n");
				i++;
			}
		}
		System.out.println(taskListString);
	}

}
