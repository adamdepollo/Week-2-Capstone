package co.grandcircus;

import java.util.LinkedList;
import java.util.List;

public class Task {

	private String dueDate;
	private String teamMemberName;
	private String whetherComplete;
	private String taskDescription;

	public Task(String teamMemberName, String taskDescription, String dueDate) {
		setDueDate(dueDate);
		setTeamMemberName(teamMemberName);
		whetherComplete = "x";
		setTaskDescription(taskDescription);
	}

	public Task() {

	}

	public String setDueDate(String dueDate) {
		return this.dueDate = dueDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String setTeamMemberName(String teamMemberName) {
		return this.teamMemberName = teamMemberName;
	}

	public String getTeamMemberName() {
		return teamMemberName;
	}

	public String setWhetherComplete(String whetherComplete) {
		return this.whetherComplete = whetherComplete;
	}

	public String getWhetherComplete() {
		return this.whetherComplete;
	}

	public String setTaskDescription(String taskDescription) {
		return this.taskDescription = taskDescription;
	}

	public String getTaskDescription() {
		return this.taskDescription;
	}

	public List<String> setTask() {
		List<String> task = new LinkedList<>();
		task.add(this.teamMemberName);
		task.add(this.taskDescription);
		task.add(dueDate);
		task.add(whetherComplete);
		return task;

	}

	@Override
	public String toString() {
		String formatting = "%-" + teamMemberName.length() + "s | %-10s | %-3s\n%s\n%-" + teamMemberName.length()
				+ "s | %-10s | %-5s\n%s\nTask: %s\n";
		String divider = "";
		for (int i = 0; i < formatting.length(); i++) {
			divider += "*";
		}
		return String.format(formatting, "Team Member", "Due Date", "Complete?", divider, teamMemberName, dueDate,
				whetherComplete, divider, taskDescription);
	}

}
