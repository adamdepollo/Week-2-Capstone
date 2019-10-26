package co.grandcircus;

import java.util.LinkedList;
import java.util.List;

public class TaskList {

	private List<Task> taskList = new LinkedList<>();

	public TaskList(Task task) {
		//taskList.add(task);
	}
	
	public TaskList() {
		//List<Task> taskList = new LinkedList<>();
	}
	
	public void setTaskList(Task task) {
		taskList.add(task);
		//return taskList;
	}
	public List<Task> getTaskList() {
		return taskList;
	}
	@Override
	public String toString() {
		int i = 1;
		String taskListString = "Tasks\n*******************************\n";
		for (Task t: taskList) {
			taskListString.concat(i + ". " + t.getTaskDescription());
			i++;
		}
		return taskListString;
		
	}

}
